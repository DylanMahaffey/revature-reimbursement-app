package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.revature.exceptions.EmailExistsException;
import com.revature.models.Expense;
import com.revature.models.Ticket;

public class ExpenseDAOImpl implements ExpenseDAO {

	@Override
	public Expense[] getTicketExpenses(long ticket_id) {
		Set<Expense> expenses = new HashSet<Expense>();
		
		try (Connection conn = ConnectionFactory.getConnection()) {
			String sql = "SELECT * FROM expenses WHERE ticket_id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setLong(1, ticket_id);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Expense e = new Expense(
							rs.getLong("expense_id"),
							rs.getLong("ticket_id"),
							rs.getString("expense_name"),
							rs.getDouble("expense_price")
						);
				expenses.add(e);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			
		}
		Expense[] exs = new Expense[expenses.size()];
		exs = expenses.toArray(exs);
		return exs;
	}

	@Override
	public void setExpenses(long ticket_id, Expense[] expenses) {
		for (Expense e : expenses) {
			e.setTicket_id(ticket_id);
			insertExpense(e);
		}
	}
	
	private void insertExpense(Expense e) {
		String sql = "INSERT INTO expenses (ticket_id, expense_name, expense_price) "
				  + "VALUES (?,?,?);";
		
		try(Connection conn = ConnectionFactory.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setLong(1, e.getTicket_id());
			ps.setString(2, e.getExpense_name());
			ps.setDouble(3, e.getPrice());
			
			ps.executeUpdate();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			
		}
	}
	
	

}
