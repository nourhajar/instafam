package com.codingdojo.instafam.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codingdojo.instafam.models.Family;
import com.codingdojo.instafam.repositories.FamilyRepository;

@Service
public class FamilyService {
	private final FamilyRepository familyRepository;
	
	public FamilyService (FamilyRepository familyRepository) {
		this.familyRepository = familyRepository;
	}
	public List<Family> findAll(){
    	return familyRepository.findAll();
    }
    public Family findById(Long id) {
    	Optional<Family> u = familyRepository.findById(id);
    	
    	if(u.isPresent()) {
            return u.get();
    	} else {
    	    return null;
    	}
    }
   
    public Family createFamily(Family family) {
    	return familyRepository.save(family);
    }
    
    public Family updateFamily(Family family) {
    	return familyRepository.save(family);
    }
}
