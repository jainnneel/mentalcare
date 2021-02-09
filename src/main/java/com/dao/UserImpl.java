package com.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.UserDto;
import com.exceptionHandle.ExceptionOccured;
import com.model.Users;
import com.repository.Userrepo;

@Service
public class UserImpl {

	@Autowired
	Userrepo userrepo;

	public Users createUser(UserDto u) {
		try {
			return userrepo.save(new Users( u.getEmail(), u.getPass()));
		} catch (Exception r) {
			throw new ExceptionOccured();
		}
	}

	public Users getbyEmail(String email) {
		try {
			return userrepo.findByemail(email);
		} catch (Exception r) {
			throw new ExceptionOccured();
		}
		
	}

    public void createUserSave(Users u) {
            try {
                userrepo.save(u);
            } catch (Exception e) {
                e.printStackTrace();
            }        
    }

}
