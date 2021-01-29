package com.revature.dao;

import java.sql.SQLException;
import java.util.Set;

import com.revature.exceptions.EmailExistsException;
import com.revature.models.Employee;

public interface EmployeeDAO {
	
	// SELECT
	public Set<Employee> getAllEmployees();
	public Employee getUserById(Long id);
	public Employee getUserByEmail(String email);
	public Employee authenticateUser(String email, String password);
	public Boolean checkForEmail(String email);
	
	// INSERT
	public Long insertEmployee(Employee e) throws SQLException, EmailExistsException;
	
	// UPDATE
	public Boolean updateEmployee(Employee e);
	
	// DELETE
	public Boolean deleteEmployee(Employee e);

}
