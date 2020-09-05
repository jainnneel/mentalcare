package com.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Users;
import com.repository.Userrepo;

@Service
public class UserImpl {

	@Autowired
	Userrepo userrepo;

	public Users createUser(Users u) {
		try {
			return userrepo.save(u);
		} catch (Exception r) {
			throw new RuntimeException();
		}
	}

	public Users getbyEmail(String email) {
		try {
			return userrepo.findByemail(email);
		} catch (Exception r) {
			throw new RuntimeException();
		}
		
	}

}
