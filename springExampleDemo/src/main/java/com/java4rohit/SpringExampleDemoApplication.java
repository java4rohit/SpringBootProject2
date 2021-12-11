package com.java4rohit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.java4rohit.Demo.Car;

@SpringBootApplication
public class SpringExampleDemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext=SpringApplication.run(SpringExampleDemoApplication.class, args);
		Car car= applicationContext.getBean(Car.class);
	
		System.out.println(car.getEngine());
	}

}
