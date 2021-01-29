package com.revature.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.revature.models.Employee;

public class LogoutController implements ServletController {

	@Override
	public void get(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if(session != null) {
			if(session.getAttribute("user") != null) {
				Employee e = (Employee) session.getAttribute("user");
				Logger.getLogger(this.getClass()).info(e.getEmail() + " has logged out");				
			}
			session.setAttribute("user", null);
			session.invalidate();			
		}
		res.sendRedirect("login");
	}

	@Override
	public void post(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("LogoutController GET method");
	}

	@Override
	public void put(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("LogoutController GET method");
	}

	@Override
	public void delete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("LogoutController GET method");
	}

}
