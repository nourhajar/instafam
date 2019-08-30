package com.codingdojo.instafam.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codingdojo.instafam.models.Family;
import com.codingdojo.instafam.models.Photo;
import com.codingdojo.instafam.models.User;
import com.codingdojo.instafam.repositories.PhotoRepository;

@Service
public class PhotoService {
	private final PhotoRepository photoRepository;
	
	public PhotoService (PhotoRepository photoRepository) {
		this.photoRepository = photoRepository;
	}
	public List<Photo> findAll(){
    	return photoRepository.findAll();
    }
	public List<Photo> findByFamily(Family family){
		return photoRepository.findByFamily(family);
	}
	public List<Photo> findByFamilyOrderByCreatedAtDesc(Family family){
		return photoRepository.findByFamilyOrderByCreatedAtDesc(family);
	}
	public List<Photo> findByUploader(User user){
		return photoRepository.findByUploader(user);
	}
	public List<Photo> findByUploaderOrderByCreatedAtDesc(User user){
		return photoRepository.findByUploaderOrderByCreatedAtDesc(user);
	}
	
    public Photo findById(Long id) {
    	Optional<Photo> u = photoRepository.findById(id);
    	
    	if(u.isPresent()) {
            return u.get();
    	} else {
    	    return null;
    	}
    }
    
    public void deletePhoto(Long id) {
//        Optional<Photo> optionalPhoto = photoRepository.findById(id);
        photoRepository.deleteById(id);
//        if(optionalPhoto.isPresent()) {
//        	Photo p= optionalPhoto.get();
//        	photoRepository.delete(p);
//        } else {
//            System.out.println("No photo to delete.");
//        }
    }
   
    public Photo createPhoto(Photo photo) {
    	return photoRepository.save(photo);
    }
    
    public Photo updatePhoto(Photo photo) {
    	return photoRepository.save(photo);
    }
}
