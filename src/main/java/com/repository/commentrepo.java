package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.model.Comment;
import com.model.Post;

@Repository
public interface commentrepo extends JpaRepository<Comment, Integer> {
	List<Comment> findByPost(Post p);

	@Query(value="select post_id from comment where user_id=?1",nativeQuery=true)
	List<Integer> findByuser(int u);
}
