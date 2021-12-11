package com.java4rohit.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.java4rohit.security.entity.User;
import com.java4rohit.security.repo.UserRepository;

public class LoginUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> user= userRepo.findByUserName(username);
		
		System.out.println(user);
		
		User u=user.orElseThrow(()->new UsernameNotFoundException("User not Found") );
		return u;
	}

}
