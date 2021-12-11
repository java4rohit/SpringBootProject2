package com.java4rohit.Employeemanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java4rohit.Employeemanager.model.Employee;

public interface EmployeeRepo extends JpaRepository<Employee,Long> {

}
