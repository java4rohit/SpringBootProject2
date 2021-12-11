package com.java4rohit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.java4rohit.dao.UserRepositary;
import com.java4rohit.enties.User;


@SpringBootApplication
public class FirstSpringBootProjectApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(FirstSpringBootProjectApplication.class, args);

		UserRepositary userRepositary = context.getBean(UserRepositary.class);

		User user = new User();
		user.setName("Durgesh kumar yadav");
		user.setCity("Assam");
		user.setStatus("I am java programmer");
		User user1 = userRepositary.save(user);
		System.out.println(user1);

	}

}
