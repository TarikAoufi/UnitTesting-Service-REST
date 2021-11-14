package fr.tao.testrestapi.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.tao.testrestapi.error.EmployeeNotFoundException;
import fr.tao.testrestapi.model.Employee;
import fr.tao.testrestapi.service.EmployeeService;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping
	public List<Employee> getAllEmplyees() {
		return employeeService.getAllEmplyees();		
	}
	
	@GetMapping("/{id}")
	public Employee getEmployeeById(@PathVariable(value = "id") Long id) {
		return employeeService.getEmployeeById(id);
	}
	
	@PostMapping
	public Employee createEmployee(@RequestBody @Valid Employee employee) {
		return employeeService.addEmployee(employee);
	}
	
	@PutMapping("/{id}")
	public Employee updateEmployee(@PathVariable("id") Long id, @RequestBody Employee employee) throws EmployeeNotFoundException {		
		Optional<Employee> employeeData = Optional.ofNullable(employeeService.getEmployeeById(id));
		if(employeeData.isPresent()) {
			employee.setId(employeeData.get().getId());
		}
		return employeeService.updateEmployee(employee);
	}
	
	@DeleteMapping("/{id}")
	public void deleteEmployeeById(Long id) throws EmployeeNotFoundException {
		employeeService.deleteEmployeeById(id);		
	}

}
