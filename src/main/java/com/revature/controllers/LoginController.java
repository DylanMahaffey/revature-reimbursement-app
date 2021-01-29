package com.revature.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.revature.models.Employee;
import com.revature.models.EmployeeRole;
import com.revature.services.LoginService;

public class LoginController implements ServletController {
	;

	@Override
	public void get(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("LoginController GET method");
		RequestDispatcher redis = req.getRequestDispatcher("/login/login.html");
		redis.forward(req, res);
	}

	@Override
	public void post(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("LoginController POST method");
		String email = req.getParameter("email");
        String password = req.getParameter("password");
		Employee e = LoginService.getInstance().authenticate(email, password);
		if (e == null) {
			RequestDispatcher redis = req.getRequestDispatcher("/login/login.html");
			redis.forward(req, res);
		} else {
			HttpSession session = req.getSession();
			session.setAttribute("user", e);
			Logger.getLogger(this.getClass()).info(e.getEmail() + " has logged in");
			
			if(e.getEmployee_roll() == EmployeeRole.EMPLOYEE) {
				res.sendRedirect("home");
			} else if (e.getEmployee_roll() == EmployeeRole.ADMIN) {
				res.sendRedirect("manager-home");
			}
		}
	}

	@Override
	public void put(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
	}

	@Override
	public void delete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
	}

}
