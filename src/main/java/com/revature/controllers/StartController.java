package com.revature.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.models.Employee;
import com.revature.models.EmployeeRole;

public class StartController implements ServletController {

	@Override
	public void get(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("user") != null) {
			Employee user = (Employee) session.getAttribute("user");
			if (user.getEmployee_roll() == EmployeeRole.EMPLOYEE) {
				res.sendRedirect("home");				
			} else if (user.getEmployee_roll() == EmployeeRole.ADMIN) {
				res.sendRedirect("manager-home");				
			}
			
		} else {
			res.sendRedirect("login");				
		}
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
