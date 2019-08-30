package com.codingdojo.instafam.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.instafam.models.Family;

@Repository
public interface FamilyRepository extends CrudRepository<Family, Long> {
	List<Family> findAll();
}
