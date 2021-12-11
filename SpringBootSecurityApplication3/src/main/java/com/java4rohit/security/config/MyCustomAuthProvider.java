package com.java4rohit.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MyCustomAuthProvider implements AuthenticationProvider {

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		UserDetails user = userDetailsService.loadUserByUsername(authentication.getName());

		if (user != null && passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())) {
			return new UsernamePasswordAuthenticationToken(authentication.getName(),
					authentication.getCredentials().toString());
		}

		return (Authentication) new BadCredentialsException("Erro vaild");

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.equals(authentication);

	}

}
