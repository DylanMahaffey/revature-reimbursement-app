package com.revature.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.revature.models.Employee;
import com.revature.services.LoginService;

public class RegisterController implements ServletController {

	@Override
	public void get(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.sendRedirect("login");
	}

	@Override
	public void post(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String first = req.getParameter("firstName");
        String last = req.getParameter("lastName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        
		Employee e = LoginService.getInstance().register(first, last, email, password);
		
		if (e == null) {
			RequestDispatcher redis = req.getRequestDispatcher("/login/login.html?login=fail");
			redis.forward(req, res);
		} else {
			HttpSession session = req.getSession();
			session.setAttribute("user", e);
			Logger.getLogger(this.getClass()).info(e.getEmail() + " has logged in.");
			res.sendRedirect("home");
		}
	}

	@Override
	public void put(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	}

	@Override
	public void delete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	}

}
