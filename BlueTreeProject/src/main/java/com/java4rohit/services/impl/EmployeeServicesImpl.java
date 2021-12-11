package com.java4rohit.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java4rohit.entities.Employee;
import com.java4rohit.repository.EmployeeRespository;
import com.java4rohit.services.EmployeeServices;

@Service
public class EmployeeServicesImpl implements EmployeeServices {

	@Autowired
	EmployeeRespository employeeRespository;

	@Override
	public List<Employee> getEmployee() {
		return (List<Employee>) employeeRespository.findAll();
	}

	@Override
	public Optional<Employee> findById(Long EmployeeId) {
		// TODO Auto-generated method stub
		return employeeRespository.findById(EmployeeId);
	}

	@Override
	public Employee saveEmployee(Employee employee) {

		return employeeRespository.save(employee);
	}

	@Override
	public Employee updateEmployee(Employee employee) {

		employeeRespository.save(employee);
		
		return  employee;

	} 

	@Override
	public void deleteEmployee(Long EmployeeId) {

		employeeRespository.deleteById(EmployeeId);

	}

	@Override
	public EmployeeServices findByActivitiesName(String name) {
		return employeeRespository.findByName(name);
	}

}
