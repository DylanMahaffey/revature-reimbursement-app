package com.revature.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NewTicketController implements ServletController {

	@Override
	public void get(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("NewTicketController GET method");
		RequestDispatcher redis = req.getRequestDispatcher("/new-ticket/new-ticket.html");
		redis.forward(req, res);
	}

	@Override
	public void post(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("NewTicketController POST method");
	}

	@Override
	public void put(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("NewTicketController PUT method");
	}

	@Override
	public void delete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("NewTicketController DELETE method");
	}

}
