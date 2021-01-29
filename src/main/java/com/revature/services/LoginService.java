package com.revature.services;

import java.sql.SQLException;

import com.revature.dao.EmployeeDAO;
import com.revature.dao.EmployeeDAOImpl;
import com.revature.models.Employee;
import com.revature.models.EmployeeRole;

public class LoginService {
	
	private static LoginService loginService;
	
	private LoginService() {
		super();
	}
	
	public static LoginService getInstance() {
		if(loginService == null) {
			loginService = new LoginService();
		}
		
		return loginService;
	}
	
	public boolean checkIfEmailExists(String username) {
		EmployeeDAO dao = new EmployeeDAOImpl();
		return dao.checkForEmail(username);
	}
	
	public Employee authenticate(String username, String password) {
		EmployeeDAO dao = new EmployeeDAOImpl();
		return dao.authenticateUser(username, password);
	}
	
	public Employee register(String first, String last, String email, String password) {
		EmployeeDAO dao = new EmployeeDAOImpl();
		Employee e = new Employee(first, last, email, password, EmployeeRole.EMPLOYEE);
		try {
			Long id = dao.insertEmployee(e);
			if (id != null) {
				e.setId(id);				
			}
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
			e = null;
		} catch (Exception ex) {
			ex.printStackTrace();
			e = null;
		}
		return e;
	}

}
