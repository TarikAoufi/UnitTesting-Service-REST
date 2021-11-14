package fr.tao.testrestapi.service;

import java.util.List;

import fr.tao.testrestapi.model.Employee;

public interface EmployeeService {
	
	public List<Employee> getAllEmplyees();
	public Employee getEmployeeById(Long id);
	public Employee addEmployee(Employee employee);
	public Employee updateEmployee(Employee employee);
	public void deleteEmployeeById(Long id);
	
}
