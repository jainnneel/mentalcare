package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.Post;
import com.model.Users;

@Repository
public interface postrepo extends JpaRepository<Post,Integer> {

	
	
	List<Post> findByUserOrderByDDesc(Users u);

	
	/*
	 * @Query(value="select * from post  where id=?1 and user_id!=?2",nativeQuery =
	 * true) Post findById(int i,int u);
	 */
}
	