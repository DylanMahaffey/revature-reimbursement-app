package com.revature.api.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controllers.ServletController;
import com.revature.models.ReimbursementType;
import com.revature.models.Ticket;

public class ReimbursementTypeApiController implements ServletController {

	@Override
	public void get(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		ReimbursementType[] types = ReimbursementType.values();
		ObjectMapper om = new ObjectMapper();
		res.getWriter().write(om.writeValueAsString(types));
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
