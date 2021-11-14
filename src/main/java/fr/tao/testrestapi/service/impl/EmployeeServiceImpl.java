package fr.tao.testrestapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.tao.testrestapi.error.EmployeeNotFoundException;
import fr.tao.testrestapi.error.InvalidRequestException;
import fr.tao.testrestapi.model.Employee;
import fr.tao.testrestapi.repository.EmployeeRepository;
import fr.tao.testrestapi.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public List<Employee> getAllEmplyees() {
		return employeeRepository.findAll();
	}

	@Override
	public Employee getEmployeeById(Long id) {
		Optional<Employee> optEmployee = employeeRepository.findById(id);
		if(optEmployee.isEmpty()) {
			throw new EmployeeNotFoundException(id);
		}
		return optEmployee.get();
	}

	@Override
	public Employee addEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Override
	public Employee updateEmployee(Employee employee) throws EmployeeNotFoundException {
		if(employee == null || employee.getId() == null) {
			throw new InvalidRequestException("Employee or ID must not be null!");
		}
	
		Optional<Employee> optionalEmployee = employeeRepository.findById(employee.getId());
		if(optionalEmployee.isEmpty()) {
			throw new EmployeeNotFoundException(employee.getId());
		}
		
		var existingEmployee = optionalEmployee.get();
		
		existingEmployee.setName(employee.getName());
		existingEmployee.setAge(employee.getAge());
		existingEmployee.setAddress(employee.getAddress());
		
		return employeeRepository.save(existingEmployee);
	}

	@Override
	public void deleteEmployeeById(Long id) throws EmployeeNotFoundException {
		if(employeeRepository.findById(id).isEmpty()) {
			throw new EmployeeNotFoundException(id);
		}
		employeeRepository.deleteById(id);
	}

}
