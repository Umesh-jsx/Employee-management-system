package com.example.empms.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.empms.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
