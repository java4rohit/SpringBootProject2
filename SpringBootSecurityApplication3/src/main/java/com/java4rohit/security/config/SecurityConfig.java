package com.java4rohit.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Configuration
public class SecurityConfig {

	@Bean
	public UserDetailsService userDetailsService() {
		
		InMemoryUserDetailsManager manager= new InMemoryUserDetailsManager();
		
		manager.createUser(
				User
				.withUsername("rohit")
				.password("234")
				.build());
		
		return manager;

	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

}
