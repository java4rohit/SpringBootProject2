package com.java4rohit.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.java4rohit.entities.Employee;
import com.java4rohit.services.EmployeeServices;


@Repository
public  interface  EmployeeRespository extends CrudRepository<Employee,Long>{

	EmployeeServices findByName(String name);

}
