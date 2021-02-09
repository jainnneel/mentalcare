package com.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.model.Users;
import com.repository.Userrepo;

@Component
public class UserServices implements UserDetailsService {

	@Autowired
	Userrepo r;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Users user = r.findByemail(username);

		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		System.out.println(user.getEmail());
		UserdetailsImpl userdetailsImpl = new UserdetailsImpl(user);
        return userdetailsImpl;
	}

}
