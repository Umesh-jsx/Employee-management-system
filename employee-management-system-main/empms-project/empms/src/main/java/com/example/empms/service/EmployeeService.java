package com.example.empms.service;

import java.util.List;

import com.example.empms.entity.Employee;

public interface EmployeeService {

	List<Employee> getAllEmployees();

	Employee createEmployee(Employee employee);

	Employee getEmployeeById(Long id);

	Employee updateEmployee(Long id, Employee employee);

	void deleteEmployee(Long id);

}
