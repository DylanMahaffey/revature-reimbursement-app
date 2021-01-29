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
import com.revature.models.EmployeeRole;
import com.revature.models.Ticket;
import com.revature.services.TicketService;

public class ManagerTicketsApiController implements ServletController {
	
	TicketDAO tDao = new TicketDAOImpl();

	@Override
	public void get(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Employee e = (Employee) session.getAttribute("user");
		Set<Ticket> tickets = tDao.getOtherEmployeeTickets(e.getId());
		
		ObjectMapper om = new ObjectMapper();
		res.getWriter().write(om.writeValueAsString(tickets));
	}

	@Override
	public void post(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	}

	@Override
	public void put(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		ObjectMapper om = new ObjectMapper();
		HttpSession session = req.getSession();
		Employee e = (Employee) session.getAttribute("user");
		if (e.getEmployee_roll() == EmployeeRole.ADMIN) {
			Ticket t = om.readValue(req.getReader(), com.revature.models.Ticket.class);
			if (t.getEmployeeId() != e.getId()) {
				Logger.getLogger(this.getClass()).info(e.getEmail() +  " has updated a ticket");
				TicketService.updateTicket(t);
			} else {
				Logger.getLogger(this.getClass()).warn(e.getEmail() + " attemped to update their own ticket");
			}
		} else {
			Logger.getLogger(this.getClass()).warn(e.getEmail() + " attempted to update ticket when they are not a manager");
		}
	}

	@Override
	public void delete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	}

}
