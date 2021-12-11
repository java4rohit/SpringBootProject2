package com.java4rohit.security.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class BasicAuthRedirection extends BasicAuthenticationFilter{

	public BasicAuthRedirection(AuthenticationManager authenticationManager) {
		super(authenticationManager);
		
	}
	
	@Override
	public void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			Authentication authResult) throws IOException {
		
		System.out.println("SUCCESS");
	}

	@Override
	public void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException {
		
		System.out.println("FAILED");
	}
	
	

}
