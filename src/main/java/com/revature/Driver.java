package com.revature;

import org.apache.log4j.Logger;

import com.revature.dao.TicketDAO;
import com.revature.dao.TicketDAOImpl;
import com.revature.exceptions.InsertFailedException;
import com.revature.models.ReimbursementType;
import com.revature.models.Ticket;

public class Driver {
	
	public static void main(String[] args) {
		System.out.println("running");
		
		TicketDAO tDao = new TicketDAOImpl();
//		Ticket t = tDao.getTicketById(5);
//		t.setApprovalStatus(true);
//		System.out.println(tDao.updateTicket(t));
		Logger.getLogger(Driver.class).info("Testing again......");
	}

}
