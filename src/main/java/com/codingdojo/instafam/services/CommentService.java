package com.codingdojo.instafam.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codingdojo.instafam.models.Comment;
import com.codingdojo.instafam.repositories.CommentRepository;

@Service
public class CommentService {
	private final CommentRepository commentRepository;
	
	public CommentService(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}
	
	public Comment saveComment(Comment m) {
		return commentRepository.save(m);
	}
	
	public List<Comment> allComments(){
		return commentRepository.findAll();
	}
	
    public Comment findById (Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if(optionalComment.isPresent()) {
            return optionalComment.get();
        } else {
            return null;
        }
    }
    
    public void deleteComment(Long id) {
//      Optional<Photo> optionalPhoto = photoRepository.findById(id);
      commentRepository.deleteById(id);
//      if(optionalPhoto.isPresent()) {
//      	Photo p= optionalPhoto.get();
//      	photoRepository.delete(p);
//      } else {
//          System.out.println("No photo to delete.");
//      }
  }
    public Comment updateComment(Comment c) {
    	return commentRepository.save(c);
    }
}
