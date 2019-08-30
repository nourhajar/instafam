package com.codingdojo.instafam.services;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.codingdojo.instafam.models.User;
import com.codingdojo.instafam.repositories.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
	
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    //find all
    
    public List<User> getAll(){
    	return userRepository.findAll();
    }
    // register user and hash their password
    public User registerUser(User user) {
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);
        return userRepository.save(user);
    }
    
    // find user by email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    // find user by id
    public User findUserById(Long id) {
    	Optional<User> u = userRepository.findById(id);
    	
    	if(u.isPresent()) {
            return u.get();
    	} else {
    	    return null;
    	}
    }
    
    //create user
    public User createUser(User u) {
    	return userRepository.save(u);
    }
    
    // authenticate user
    public boolean authenticateUser(String email, String password) {
        // first find the user by email
        User user = userRepository.findByEmail(email);
        // if we can't find it by email, return false
        if(user == null) {
            return false;
        } else {
            // if the passwords match, return true, else, return false
            if(BCrypt.checkpw(password, user.getPassword())) {
                return true;
            } else {
                return false;
            }
        }
    }
    public User saveUser(User user) {
    	return userRepository.save(user);
    }
    // deletes a user
    public void deleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
        	User u = optionalUser.get();
        	userRepository.delete(u);
        } else {
            System.out.println("No user to delete.");
        }
        
    }
}
