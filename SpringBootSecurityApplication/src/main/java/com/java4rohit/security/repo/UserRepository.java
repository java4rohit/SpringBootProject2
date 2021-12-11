package com.java4rohit.security.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java4rohit.security.entity.User;

@Repository
public interface UserRepository  extends JpaRepository<User, Integer> {

	Optional<User> findByUserName(String uname);
	 
	

}
