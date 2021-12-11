package com.java4rohit.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.java4rohit.security.entity.User;
import com.java4rohit.security.service.MyUserDeaitlsService;

@RestController
public class HomeController {

	@Autowired
	MyUserDeaitlsService manger;

	@GetMapping("/index")
	public ResponseEntity<String> sayHi() {

		return new ResponseEntity<String>("Welcome Spring Security???", HttpStatus.OK);
	}

	@PostMapping("/createUser")
	public ResponseEntity<String> createUser(@RequestBody User user) {
		manger.CreateUser(user);
		return new ResponseEntity<String>("User Created Successfully", HttpStatus.OK);
	}

}
