package fr.tao.testrestapi.error;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmployeeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmployeeNotFoundException(Long id) {
		super("Employee with ID " + id + " does not exist.");
	}

	public EmployeeNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
