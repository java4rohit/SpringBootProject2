package com.java4rohit.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import com.java4rohit.security.entity.User;



@Service
public class MyUserDeaitlsService {
	
	@Autowired
	UserDetailsManager manager;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	public void CreateUser(User user) {
		
		user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
		
		manager.createUser(user);
	}
	
}
