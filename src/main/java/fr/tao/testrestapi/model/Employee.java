package fr.tao.testrestapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "EMPLOYEES")
@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMPL_SEQ")
    @SequenceGenerator(sequenceName = "employee_seq", allocationSize = 1, name = "EMPL_SEQ")
	private Long id;
	@NonNull
	private String name;
	@NonNull
	private Integer age;
	@NonNull
	private String address;

}
