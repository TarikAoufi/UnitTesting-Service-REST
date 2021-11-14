package fr.tao.testrestapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fr.tao.testrestapi.model.Employee;
import fr.tao.testrestapi.service.EmployeeService;

@SpringBootApplication
public class UnitTestingServiceRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnitTestingServiceRestApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner start(EmployeeService employeeService) {
		return args -> {
			employeeService.addEmployee(new Employee(1L, "Mohamed Ali", 26, "12, avenue la victoire 72000"));
			employeeService.addEmployee(new Employee(2L, "Karine Lebessou", 29, "33, rue la nation 33200"));
			employeeService.addEmployee(new Employee(3L, "Ali Mouha", 32, "44, avenue strasbourg 83000"));
			employeeService.addEmployee(new Employee(4L, "Iness Alami", 24, "12, rue victor hugo 06000"));
			employeeService.addEmployee(new Employee(5L, "Romain Boulanger", 39, "2, avenue de la renaissance 95130"));
			employeeService.addEmployee(new Employee(6L, "Sara Faïz", 34, "176, avenue de la resistance 83000"));
			employeeService.addEmployee(new Employee(7L, "Sonia Lecompte", 26, "6, rue albert einstein 13013"));
		};
	}
}
