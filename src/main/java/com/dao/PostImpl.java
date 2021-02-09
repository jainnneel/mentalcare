package com.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.exceptionHandle.ExceptionOccured;
import com.model.Post;
import com.model.Users;
import com.repository.postrepo;

@Service
public class PostImpl {

	@Autowired
	postrepo p;
	
	public Optional<Post> getbyid(int id) {
		try {
			return p.findById(id);
		} catch (Exception r) {
			throw new ExceptionOccured();
		}
		
	}
	
	public Post createPost(Post post) {
		try {
			return p.save(post);
		} catch (Exception r) {
			throw new ExceptionOccured();
		}
		
	}
	
	public void deletePost(int id) {
		try {
			p.deleteById(id);
		} catch (Exception r) {
			throw new ExceptionOccured();
		} 
		
	}

	public List<Post> findAllPost() {
		try {
			return p.findAll(Sort.by(Sort.Direction.DESC,"id"));
		} catch (Exception r) {
			throw new ExceptionOccured();
		}
		
	}
	
	public List<Post> findPostByuser(Users u){
		try {
			return p.findByUserOrderByDDesc(u);
		} catch (Exception r) {
			throw new ExceptionOccured();
		}
		
	}
}
