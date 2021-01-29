package com.revature.dao;

import java.util.Set;

import com.revature.models.Expense;

public interface ExpenseDAO {
	
	public Expense[] getTicketExpenses(long ticket_id);
	public void setExpenses(long ticket_id, Expense[] expenses);

}
