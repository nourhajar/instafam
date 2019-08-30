package com.codingdojo.instafam.controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.codingdojo.instafam.models.Comment;
import com.codingdojo.instafam.models.Family;
import com.codingdojo.instafam.models.Photo;
import com.codingdojo.instafam.models.User;
import com.codingdojo.instafam.services.CommentService;
import com.codingdojo.instafam.services.FamilyService;
import com.codingdojo.instafam.services.PhotoService;
import com.codingdojo.instafam.services.UserService;
import com.codingdojo.instafam.validator.UserValidator;

@Controller
public class InstafamController {
	private final UserService userService;
    private final UserValidator userValidator;
    private final FamilyService familyService;
    private final CommentService commentService;
    private final PhotoService photoService;
    
    public InstafamController(UserService userService, UserValidator userValidator, FamilyService familyService, CommentService commentService, PhotoService photoService) {
    	this.userService = userService;
    	this.userValidator = userValidator;
    	this.commentService = commentService;
    	this.familyService = familyService;
    	this.photoService = photoService;
    }
    
    @RequestMapping("/")
    public String loginForm(@ModelAttribute("user") User user) {
        return "login.jsp";
    }
    
    @RequestMapping("/register")
    public String registerForm(@ModelAttribute("user") User user) {
        return "register.jsp";
    }
    
    @RequestMapping(value="/registration", method=RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
    	userValidator.validate(user, result);
    	// if result has errors, return the registration page (don't worry about validations just now)
    	if(result.hasErrors()) {
    		return "login.jsp";
    	}
        // else, save the user in the database, save the user id in session, and redirect them to the /home route
    		User userReg = userService.createUser(user);
    		userService.registerUser(userReg);
    		Long userId = userReg.getId();
    		session.setAttribute("userId", userId);
    		return "redirect:/family";
    }
    
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession session, @ModelAttribute("user") User user) {
        // if the user is authenticated, save their user id in session
    	if(userService.authenticateUser(email, password)) {
    		User jeff = userService.findByEmail(email);
    		Long userId = jeff.getId();
    		session.setAttribute("userId", userId);
    		if(jeff.getFamily() == null) {
    			return "redirect:/family";
    		}else {
    			return "redirect:/feed";
    		}
    	}
        // else, add error messages and return the login page
    		model.addAttribute("error", "Login failed");
    		return "login.jsp";
    }
    
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        // invalidate session
    	session.removeAttribute("userId");
        // redirect to login page
    	return "redirect:/";
    }
    
    @RequestMapping("/family")
    public String family(HttpSession session, Model model, @ModelAttribute("family") Family family) {
    	Long userID = (Long) session.getAttribute("userId");
    	User user = userService.findUserById(userID);
    	List<Family> families = familyService.findAll();
    	model.addAttribute("families", families);
    	return "pickfamily.jsp";
    }
    
    @RequestMapping(value="/families/new", method=RequestMethod.POST)
    public String createFam(@Valid @ModelAttribute("family") Family family, BindingResult result, HttpSession session) {
		if(result.hasErrors()) {
			System.out.println("I'm here");
			return "pickfamily.jsp";
		} else {
			System.out.println("I went here");
			Family f = familyService.createFamily(family);
			Long userID = (Long) session.getAttribute("userId");
	    	User user = userService.findUserById(userID);
	    	user.setFamily(f);
	    	userService.saveUser(user);
	    	familyService.updateFamily(f);
			return "redirect:/feed";
		}
    }
    
    @RequestMapping(value="/families/add", method=RequestMethod.POST)
    public String addUserToFam(@RequestParam("family") Long id, Model model, HttpSession session, @ModelAttribute("family") Family family) {
    	Long userID = (Long) session.getAttribute("userId");
    	User user = userService.findUserById(userID);
    	Family fam = familyService.findById(id);
    	user.setFamily(fam);
    	userService.saveUser(user);
    	familyService.updateFamily(fam);
    	if(fam.getUsers().contains(user)) {
    		return "redirect:/feed";
    	}else {
    		model.addAttribute("error", "Fam jam failed");
    		return "pickfamily.jsp";
    	}
    }
    
    @RequestMapping("/feed")
    public String home(HttpSession session, Model model) {
        // get user from session, save them in the model and return the home page
    	Long userID = (Long) session.getAttribute("userId");
    	User user = userService.findUserById(userID);
    	Family fam = user.getFamily();
    	List<User> members = fam.getUsers();
    	//List<Idea> ideas = ideaService.allIdeas();
    	List<Photo> photos = photoService.findByFamilyOrderByCreatedAtDesc(fam);
    	model.addAttribute("user", user);
    	//model.addAttribute("ideas", ideas);
    	model.addAttribute("photos", photos);
    	model.addAttribute("members", members);
    	return "/feed.jsp";
    }
    
    @RequestMapping("/likes/{id}/remove")
    public String unlike(@PathVariable("id") Long id, Model model, HttpSession session) {
    	Long userID = (Long) session.getAttribute("userId");
    	User user = userService.findUserById(userID);
    	Photo photo = photoService.findById(id);
    	photo.getLikedUsers().remove(user);
    	photoService.updatePhoto(photo);
    	return "redirect:/feed";
    }
    
    @RequestMapping("/likes/{id}/add")
    public String join(@PathVariable("id") Long id, HttpSession session) {
    	Long userID = (Long) session.getAttribute("userId");
    	User user = userService.findUserById(userID);
    	Photo photo = photoService.findById(id);
    	photo.setLikedUser(user);
    	photoService.updatePhoto(photo);
    	return "redirect:/feed";
    }
    
    @RequestMapping("/profiles/likes/{id}/remove")
    public String punlike(@PathVariable("id") Long id, Model model, HttpSession session) {
    	Long userID = (Long) session.getAttribute("userId");
    	User user = userService.findUserById(userID);
    	Photo photo = photoService.findById(id);
    	User uploader = photo.getUploader();
    	Long uploaderID = uploader.getId();
    	photo.getLikedUsers().remove(user);
    	photoService.updatePhoto(photo);
    	return "redirect:/profiles/" + uploaderID;
    }
    
    @RequestMapping("/profiles/likes/{id}/add")
    public String pjoin(@PathVariable("id") Long id, HttpSession session) {
    	Long userID = (Long) session.getAttribute("userId");
    	User user = userService.findUserById(userID);
    	Photo photo = photoService.findById(id);
    	User uploader = photo.getUploader();
    	Long uploaderID = uploader.getId();
    	photo.setLikedUser(user);
    	photoService.updatePhoto(photo);
    	return "redirect:/profiles/" + uploaderID;
    }
    
    @RequestMapping("/photos/new")
    public String photos(HttpSession session, Model model, @ModelAttribute("photo") Photo photo) {
    	Long userID = (Long) session.getAttribute("userId");
    	User user = userService.findUserById(userID);
    	model.addAttribute("user", user);
    	return "newphoto.jsp";
    }
    
    @RequestMapping("/photos/new/profilepic")
    public String profilePic(HttpSession session, Model model, @ModelAttribute("photo") Photo photo) {
    	Long userID = (Long) session.getAttribute("userId");
    	User user = userService.findUserById(userID);
    	model.addAttribute("user", user);
    	return "newprofilephoto.jsp";
    }
    
    @RequestMapping("/photos/update/profilepic")
    public String upProfilePic(HttpSession session, Model model, @ModelAttribute("photo") Photo photo) {
    	Long userID = (Long) session.getAttribute("userId");
    	User user = userService.findUserById(userID);
    	model.addAttribute("user", user);
    	return "updateprofilephoto.jsp";
    }
    
    @RequestMapping(value="/photos/new", method=RequestMethod.POST)
    public String createPhoto(HttpServletRequest servletRequest, @Valid @ModelAttribute("photo") Photo photo, BindingResult result, HttpSession session) {
		if(result.hasErrors()) {
			System.out.println("I'm here");
			return "newphoto.jsp";
		} else {
			MultipartFile image = photo.getImage();
			String fileName = image.getOriginalFilename();

			File imageFile = new File(servletRequest.getServletContext().getRealPath("/image"), fileName);
			try {
				image.transferTo(imageFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Long userID = (Long) session.getAttribute("userId");
	    	User user = userService.findUserById(userID);
	    	photo.setUploader(user);
	    	user.setPhoto(photo);
			photo.setImageFileName(fileName);
			user = userService.saveUser(user);
			photo = photoService.createPhoto(photo);
			return "redirect:/feed";
		}
    }
    
    @RequestMapping(value="/photos/update/profilepic", method=RequestMethod.POST)
    public String updateProfilePhoto(HttpServletRequest servletRequest, @Valid @ModelAttribute("photo") Photo photo, BindingResult result, HttpSession session) {
		if(result.hasErrors()) {
			System.out.println("I'm here");
			return "newphoto.jsp";
		} else {
			MultipartFile image = photo.getImage();
			String fileName = image.getOriginalFilename();

			File imageFile = new File(servletRequest.getServletContext().getRealPath("/image"), fileName);
			try {
				image.transferTo(imageFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Long userID = (Long) session.getAttribute("userId");
	    	User user = userService.findUserById(userID);
	    	Photo noto = user.getProfilePic();
	    	System.out.println(noto.getImageFileName());
	    	System.out.println(noto.getId());
	    	user.setProfilePic(null);
	    	user = userService.saveUser(user);
			photoService.deletePhoto(noto.getId());
			user = userService.saveUser(user);
			photo.setImageFileName(fileName);
			photo.setUser(user);
			photo = photoService.createPhoto(photo);
			user.setProfilePic(photo);
			user = userService.saveUser(user);
			return "redirect:/profiles/" + userID;
		}
    }
    
    @RequestMapping(value="/photos/new/profilepic", method=RequestMethod.POST)
    public String createProfilePhoto(HttpServletRequest servletRequest, @Valid @ModelAttribute("photo") Photo photo, BindingResult result, HttpSession session) {
		if(result.hasErrors()) {
			System.out.println("I'm here");
			return "newphoto.jsp";
		} else {
			MultipartFile image = photo.getImage();
			String fileName = image.getOriginalFilename();

			File imageFile = new File(servletRequest.getServletContext().getRealPath("/image"), fileName);
			try {
				image.transferTo(imageFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Long userID = (Long) session.getAttribute("userId");
	    	User user = userService.findUserById(userID);
			photo.setImageFileName(fileName);
			photo.setUser(user);
			photo = photoService.createPhoto(photo);
			user.setProfilePic(photo);
			user = userService.saveUser(user);
			return "redirect:/profiles/" + userID;
		}
    }
    
    @PostMapping("/comments")
    public String createComment(HttpSession session, @RequestParam("comment") String comment, @RequestParam("photo") Long pId) {
		Comment com = new Comment();
		Long userID = (Long) session.getAttribute("userId");
    	User user = userService.findUserById(userID);
		Photo photo = photoService.findById(pId);
		com.setUser(user);
		com.setPhoto(photo);
		com.setComment(comment);
		commentService.saveComment(com);
		return "redirect:/feed";
    }
    
    @RequestMapping("/profiles/{id}")
    public String showEvent(@PathVariable("id") Long id, Model model, HttpSession session) {
    	User profileUser = userService.findUserById(id);
    	Long userID = (Long) session.getAttribute("userId");
    	User user = userService.findUserById(userID);
    	List<Photo> photos = photoService.findByUploaderOrderByCreatedAtDesc(profileUser);
    	model.addAttribute("profileUser", profileUser);
    	model.addAttribute("user", user);
    	model.addAttribute("photos", photos);
    	return "profile.jsp";
    }
    
    @RequestMapping("/profiles/{id}/update")
    public String fixUser(@ModelAttribute("user") User user, Model model, HttpSession session) {
    	Long userID = (Long) session.getAttribute("userId");
    	User profileUser = userService.findUserById(userID);
    	model.addAttribute("profileUser", profileUser);
    	return "editprofile.jsp";
    }
    
    @PostMapping("/profiles/{id}/update")
    public String editUser(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
    		
    		System.out.println("******" + user.getPassword());
    		if(result.hasErrors()) {
    			System.out.println("I'm here");
    			return "editprofile.jsp";
    		} else {
    			Long userID = (Long) session.getAttribute("userId");
    			System.out.println("I went here");
    	    	User profileUser = userService.findUserById(userID);
    	    	Family family = profileUser.getFamily();
    			user.setFamily(family);
    			User use = userService.saveUser(user);
    			return "redirect:/profiles/" + userID;
    		}
        }
    
    @RequestMapping(value="/comments/{id}/delete", method=RequestMethod.DELETE)
    public String destroyComment(@PathVariable("id") Long id) {
    	commentService.deleteComment(id);
        return "redirect:/feed";
    }
    
    @RequestMapping(value="/photos/{id}/delete", method=RequestMethod.DELETE)
    public String destroyPhoto(@PathVariable("id") Long id) {
    	photoService.deletePhoto(id);
        return "redirect:/feed";
    }
    }
    

