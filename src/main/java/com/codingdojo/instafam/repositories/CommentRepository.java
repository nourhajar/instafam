package com.codingdojo.instafam.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.codingdojo.instafam.models.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {
	List<Comment> findAll();
}
