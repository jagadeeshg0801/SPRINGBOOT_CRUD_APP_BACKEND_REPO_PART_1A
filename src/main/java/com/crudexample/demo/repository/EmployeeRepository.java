package com.crudexample.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crudexample.demo.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
 