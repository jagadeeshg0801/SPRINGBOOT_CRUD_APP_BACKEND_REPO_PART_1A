package com.crudexample.demo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crudexample.demo.exceptions.ResourceNotException;
import com.crudexample.demo.models.Employee;
import com.crudexample.demo.repository.EmployeeRepository;




@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	
	@GetMapping("/employees")
	public List<Employee> getEmployees(){
		return employeeRepository.findAll();
	}
	
	//Create Employee 	
	@PostMapping("/add-emp")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	
	//get EMployee by Id
	@GetMapping("/employee/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		Employee employee =  employeeRepository.findById(id).
				orElseThrow(() -> new ResourceNotException("Employee Id Not Exists with id"+ id));
		return ResponseEntity.ok(employee);
	}
	
	
	//Updating Employee Details by Id base
	@PutMapping("/update-emp/{id}")
	public ResponseEntity<Employee> updateEmployeeDetails(@PathVariable Long id, @RequestBody Employee emp) {
		Employee employee = employeeRepository.findById(id).orElseThrow(()-> new ResourceNotException("Employee Not found with given Id"+ id));
		
		employee.setEmailId(emp.getEmailId());
		employee.setFirstName(emp.getFirstName());
		employee.setLastName(emp.getLastName());
		Employee updateEmployeeDetails = employeeRepository.save(employee);
		
		return ResponseEntity.ok(updateEmployeeDetails);
	}
	
	//Delete employee By Id
	@DeleteMapping("/delete-emp/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
		Employee employee = employeeRepository.findById(id).orElse(null);
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		if(employee != null) {
			employeeRepository.delete(employee);		
			response.put("deleted", Boolean.TRUE);
			return ResponseEntity.ok(response);
		}	
		response.put("deleted", Boolean.FALSE);
		return ResponseEntity.ok(response);
	}
}
