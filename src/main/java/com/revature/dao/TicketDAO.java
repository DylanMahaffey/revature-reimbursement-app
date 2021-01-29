package com.revature.dao;

import java.util.Set;

import com.revature.exceptions.InsertFailedException;
import com.revature.models.Ticket;

public interface TicketDAO {
	
	public Set<Ticket> getAllTickets();
	public Set<Ticket> getPendingTickets();
	public Set<Ticket> getEmployeeTickets(long employeeId);
	public Set<Ticket> getOtherEmployeeTickets(long employeeId);
	public Ticket getTicketById(long ticket_id);
	
	public Ticket insertTicket(Ticket t) throws InsertFailedException;
	
	public Ticket updateTicket(Ticket t);
	
	public void deleteTicket(long ticket_id);
}
