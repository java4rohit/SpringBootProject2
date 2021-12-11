package com.java4rohit.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.java4rohit.enties.User;


@Repository
public interface UserRepositary extends CrudRepository<User, Integer>{

}


//@Component --> @Service, @Repository