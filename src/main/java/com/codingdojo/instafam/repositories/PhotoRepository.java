package com.codingdojo.instafam.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.instafam.models.Family;
import com.codingdojo.instafam.models.Photo;
import com.codingdojo.instafam.models.User;

@Repository
public interface PhotoRepository extends CrudRepository<Photo, Long> {
	public List<Photo> findAll();
	public List<Photo> findByFamily(Family family);
	public List<Photo> findByFamilyOrderByCreatedAtDesc(Family family);
	public List<Photo> findByUploader(User user);
	public List<Photo> findByUploaderOrderByCreatedAtDesc(User user);
}
