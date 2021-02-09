package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.Users;

@Repository
public interface Userrepo extends JpaRepository<Users, Integer> {

	Users findByemail(String email);

}
