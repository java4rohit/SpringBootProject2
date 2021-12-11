package com.java4rohit.rest.webservices.restfulwebservices.helloWorld;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins ="http://localhost:4200" )
@RestController
public class HelloWorldController {
	
	
	
	//@RequestMapping(method = RequestMethod.GET,path ="/hello-world" )
	@GetMapping(path = "/hello-world") 
	public String helloWorld() {
		return "hello World";
	}
	//hello-world-bean
	@GetMapping(path = "/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		throw new RuntimeException("Som Error has Happen !.... Contact Support at ******");
		//return new HelloWorldBean("hello World");
	} 
	
	//hello-world/path-varible/java4rohit 
	@GetMapping(path = "/hello-world/path-varible/{name}")
	public HelloWorldBean helloWorldPathVarible (@PathVariable String name) {
		
	return new HelloWorldBean(String.format("Hello World, %s", name));
	}
}
