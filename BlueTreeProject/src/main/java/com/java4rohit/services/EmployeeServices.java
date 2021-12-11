package com.java4rohit.services;

import java.util.List;
import java.util.Optional;

import com.java4rohit.entities.Employee;

public interface EmployeeServices {
	
	public List<Employee> getEmployee();

	
	public void deleteEmployee(Long employeeId);
	
	public EmployeeServices findByActivitiesName(String name);
	
	public Optional<Employee> findById(Long Id);

	public Employee saveEmployee(Employee employee);

	public Employee updateEmployee(Employee employee);


}
