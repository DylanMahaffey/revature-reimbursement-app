package com.revature.api.controllers;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controllers.ServletController;
import com.revature.dao.TicketDAO;
import com.revature.dao.TicketDAOImpl;
import com.revature.models.Employee;
import com.revature.models.Ticket;
import com.revature.services.TicketService;

public class TicketApiController implements ServletController {
	
	TicketDAO tDao = new TicketDAOImpl();

	@Override
	public void get(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Employee e = (Employee) session.getAttribute("user");
		Set<Ticket> tickets = tDao.getEmployeeTickets(e.getId());
		
		ObjectMapper om = new ObjectMapper();
		res.getWriter().write(om.writeValueAsString(tickets));
	}

	@Override
	public void post(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("post ticket");
		ObjectMapper om = new ObjectMapper();
		HttpSession session = req.getSession();
		Employee e = (Employee) session.getAttribute("user");
        Ticket t = om.readValue(req.getReader(), com.revature.models.Ticket.class);
        t.setEmployeeId(e.getId());
        System.out.println(t);
		TicketService.postTicket(t);
		Logger.getLogger(this.getClass()).info(e.getEmail() + " has requested a new ticket");
	}

	@Override
	public void put(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		ObjectMapper om = new ObjectMapper();
        Ticket t = om.readValue(req.getReader(), com.revature.models.Ticket.class);
		TicketService.updateTicket(t);
	}

	@Override
	public void delete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	}

}
