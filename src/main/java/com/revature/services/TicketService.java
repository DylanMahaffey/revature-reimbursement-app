package com.revature.services;

import com.revature.dao.TicketDAO;
import com.revature.dao.TicketDAOImpl;
import com.revature.exceptions.InsertFailedException;
import com.revature.models.Ticket;

public class TicketService {
	
	public static void postTicket(Ticket t) {
		TicketDAO tDao = new TicketDAOImpl();
		try {
			tDao.insertTicket(t);
		} catch(InsertFailedException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateTicket(Ticket t) {
		TicketDAO tDao = new TicketDAOImpl();
		tDao.updateTicket(t);
	}

}
