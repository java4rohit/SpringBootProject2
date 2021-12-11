package com.java4rohit.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.java4rohit.entities.Employee;
import com.java4rohit.services.EmployeeServices;


@RestController
public class EmployeeController {

	@Autowired
	EmployeeServices employeeServices;

	@PostMapping(value = "/save")
	public ResponseEntity<Employee> saveEmployee(@Valid @RequestBody Employee employee) {
		Employee responseEmployee = employeeServices.saveEmployee(employee);
		return new ResponseEntity<Employee>(responseEmployee, HttpStatus.CREATED);

	}

	@GetMapping(value = "/getEmployee", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Employee>> getCareers() {
		List<Employee> list = employeeServices.getEmployee();
		return new ResponseEntity<List<Employee>>(list, list.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
	}

	@GetMapping(value = "/getEmployee/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Employee> findById(@PathVariable("id") Long employeeId) {

		Optional<Employee> careersOPt = employeeServices.findById(employeeId);
		return new ResponseEntity<Employee>(careersOPt.isPresent() ? careersOPt.get() : null,
				careersOPt.isPresent() ? HttpStatus.OK : HttpStatus.NO_CONTENT);

	}

}
