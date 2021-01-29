package com.revature.api.controllers;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controllers.ServletController;
import com.revature.dao.EmployeeDAO;
import com.revature.dao.EmployeeDAOImpl;
import com.revature.models.Employee;

public class EmployeeApiController implements ServletController {

	@Override
	public void get(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("application/json"); // This is redundant, default type
		EmployeeDAO dao = new EmployeeDAOImpl();
		
		Set<Employee> users = dao.getAllEmployees();
		
		ObjectMapper om = new ObjectMapper();
		res.getWriter().write(om.writeValueAsString(users)); // This will parse our JAva object into a JSON

	}

	@Override
	public void post(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	}

	@Override
	public void put(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	}

	@Override
	public void delete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	}

}
