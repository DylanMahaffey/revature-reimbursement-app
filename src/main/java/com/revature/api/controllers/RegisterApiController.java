package com.revature.api.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controllers.ServletController;
import com.revature.models.Employee;
import com.revature.models.JsonResponse;
import com.revature.services.LoginService;

public class RegisterApiController implements ServletController {

	@Override
	public void get(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	}

	@Override
	public void post(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		ObjectMapper om = new ObjectMapper();
        Employee e = om.readValue(req.getReader(), com.revature.models.Employee.class);
        
        boolean emailExists = LoginService.getInstance().checkIfEmailExists(e.getEmail());
		
        res.getWriter().write(om.writeValueAsString(emailExists));
	}

	@Override
	public void put(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	}

	@Override
	public void delete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	}

}
