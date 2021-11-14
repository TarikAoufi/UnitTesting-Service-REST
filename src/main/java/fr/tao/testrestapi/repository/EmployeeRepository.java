package fr.tao.testrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.tao.testrestapi.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
