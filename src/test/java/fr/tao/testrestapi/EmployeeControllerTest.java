package fr.tao.testrestapi;


import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.tao.testrestapi.controller.EmployeeController;
import fr.tao.testrestapi.error.EmployeeNotFoundException;
import fr.tao.testrestapi.error.InvalidRequestException;
import fr.tao.testrestapi.model.Employee;
import fr.tao.testrestapi.service.EmployeeService;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

	@Autowired
	MockMvc mockMvc; 
	@Autowired
	ObjectMapper mapper;

	@MockBean
	EmployeeService employeeService;
	
	final String ENDPOINT = "/api/employee/";

	Employee EMPLOYEE_1 = new Employee(1l, "Mohamed Ali", 26, "12, avenue la victoire 72000");
	Employee EMPLOYEE_2 = new Employee(2l, "Karine Lebessou", 29, "33, rue la nation 33200");
	Employee EMPLOYEE_3 = new Employee(3l, "Ali Mouha", 32, "44, avenue strasbourg 83000");
	Employee EMPLOYEE_4 = new Employee(4l, "Iness Alami", 24, "12, rue victor hugo 06000");
	
	/****** TU - Gestionnaire de requête GET ******/
    @Test
    void getAllEmployees_success() throws Exception {
        List<Employee> employees = new ArrayList<>(Arrays.asList(EMPLOYEE_1, EMPLOYEE_2, EMPLOYEE_3, EMPLOYEE_4)); 
        
        // renvoie une liste de quatre employés prédéfinis, 
        // au lieu de faire un appel à la bd.
        Mockito.when(employeeService.getAllEmplyees()).thenReturn(employees);
        
        /* Pour cet appel, nous avons défini 3 assertions dans les méthodes andExpect() : 
	         + la réponse renvoie un code d'état 200 ou OK, 
	         + la réponse renvoie une liste de taille 4 
			 + le troisième objet Employee de la liste a une propriété name de Ali Mouha.
         */
        mockMvc.perform(MockMvcRequestBuilders
                .get(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[2].name", is("Ali Mouha")));
    }
   
    @Test
    void getEmployeeById_success() throws Exception {
    	
    	Mockito.when(employeeService.getEmployeeById(EMPLOYEE_1.getId())).thenReturn(EMPLOYEE_1);
    	
    	mockMvc.perform(MockMvcRequestBuilders
    			.get(ENDPOINT + EMPLOYEE_1.getId())
    			.contentType(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk())
    			.andExpect(jsonPath("$", notNullValue()))
    			.andExpect(jsonPath("$.name", is("Mohamed Ali")));   	
    }
    
    /****** TU - Gestionnaire de requête POST ******/
    @Test
    void createEmployee_success() throws Exception {
    	Employee employee = Employee.builder()
    			.name("Christophe Loulouche")
    			.age(38)
    			.address("56 Bd des dames 13002")
    			.build();
    	
    	Mockito.when(employeeService.addEmployee(employee)).thenReturn(employee);
    	
    	MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(ENDPOINT)
    			.contentType(MediaType.APPLICATION_JSON)
    			.accept(MediaType.APPLICATION_JSON)
    			.content(this.mapper.writeValueAsString(employee));
    	
		mockMvc.perform(mockRequest)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$.name", is("Christophe Loulouche")));   						 
    }
    
    /****** TU - Gestionnaire de requête PUT ******/
    @Test
    void updateEmployee_success() throws Exception {
    	Employee updatedEmployee = Employee.builder()
                .id(1L)
                .name("Mohamed Amine")
                .age(26)
                .address("12, avenue la victoire 72000")
                .build();
  
        Mockito.when(employeeService.updateEmployee(updatedEmployee)).thenReturn(updatedEmployee);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put(ENDPOINT + 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(updatedEmployee));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Mohamed Amine")));
    }

    /** 
     * Cas où les données d'entrée ne sont pas correctes ou la bd 
     * ne contient pas l'entité que nous essayons de mettre à jour, 
     * l'application doit répondre avec une exception.
     */
    @Test
    void updateEmployee_nullId() throws Exception {
    	Employee updatedEmployee = Employee.builder()
                .name("Toto Felix")
                .age(44)
                .address("555 Bd de l'indépendance 13012")
                .build();

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(updatedEmployee));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                    assertTrue(result.getResolvedException() instanceof InvalidRequestException))
        .andExpect(result ->
            assertEquals("Employee or ID must not be null!", result.getResolvedException().getMessage()));
    }
    
    @Test
    void updateEmployee_employeeNotFound() throws Exception {
    	Employee updatedEmployee = Employee.builder()
                .id(9l)
                .name("Rahim Sterling")
                .age(50)
                .address("33 avenue la lune 54000")
                .build(); 
    	
        Mockito.when(employeeService.updateEmployee(updatedEmployee)).thenReturn(null);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(updatedEmployee));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                    assertTrue(result.getResolvedException() instanceof EmployeeNotFoundException))
        .andExpect(result ->
            assertEquals("Employee with ID 9 does not exist.", result.getResolvedException().getMessage()));
    }
    
    /****** TU - Gestionnaire de requête DELETE ******/
    @Test
    void deletePatientById_success() throws Exception {
        Mockito.when(employeeService.getEmployeeById(EMPLOYEE_2.getId())).thenReturn(EMPLOYEE_2);

        mockMvc.perform(MockMvcRequestBuilders
                .delete(ENDPOINT + EMPLOYEE_2.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteEmployeeById_notFound() throws Exception {
        Mockito.when(employeeService.getEmployeeById(9l)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                .delete(ENDPOINT + 2)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof EmployeeNotFoundException))
        .andExpect(result ->
                assertEquals("Employee with ID 9 does not exist.", result.getResolvedException().getMessage()));
    }

}
