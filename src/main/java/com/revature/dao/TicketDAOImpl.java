package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.revature.exceptions.InsertFailedException;
import com.revature.models.Employee;
import com.revature.models.EmployeeRole;
import com.revature.models.ReimbursementType;
import com.revature.models.Ticket;

public class TicketDAOImpl implements TicketDAO {

	@Override
	public Set<Ticket> getAllTickets() {
		Set<Ticket> tickets = new HashSet<Ticket>();
		
		try (Connection conn = ConnectionFactory.getConnection()) {
			String sql = "SELECT * FROM get_full_ticket";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				tickets.add(setTicket(rs));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			
		}
		return tickets;
	}

	@Override
	public Set<Ticket> getPendingTickets() {
		Set<Ticket> tickets = new HashSet<Ticket>();
		
		try (Connection conn = ConnectionFactory.getConnection()) {
			String sql = "SELECT * FROM get_full_ticket WHERE approval_status IS NULL";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				tickets.add(setTicket(rs));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			
		}
		return tickets;
	}

	@Override
	public Set<Ticket> getEmployeeTickets(long employeeId) {
		Set<Ticket> tickets = new HashSet<Ticket>();
		
		try (Connection conn = ConnectionFactory.getConnection()) {
			String sql = "SELECT * FROM get_full_ticket WHERE employee_id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setLong(1, employeeId);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				tickets.add(setTicket(rs));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			
		}
		return tickets;
	}

	@Override
	public Set<Ticket> getOtherEmployeeTickets(long employeeId) {
		Set<Ticket> tickets = new HashSet<Ticket>();
		
		try (Connection conn = ConnectionFactory.getConnection()) {
			String sql = "SELECT * FROM get_full_ticket WHERE employee_id != ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setLong(1, employeeId);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				tickets.add(setTicket(rs));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			
		}
		return tickets;
	}

	@Override
	public Ticket getTicketById(long ticket_id) {
		Set<Ticket> tickets = new HashSet<Ticket>();
		
		try (Connection conn = ConnectionFactory.getConnection()) {
			String sql = "SELECT * FROM get_full_ticket WHERE ticket_id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setLong(1, ticket_id);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				tickets.add(setTicket(rs));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			
		}
		Ticket t = null;
		if(tickets.size()>0) {
			Iterator<Ticket> i = tickets.iterator();
			t = i.next();
		}
		return t;
	}

	@Override
	public Ticket insertTicket(Ticket t) throws InsertFailedException {
		
		try (Connection conn = ConnectionFactory.getConnection()) {
			
			String sql = "SELECT insert_new_ticket( ?,?,?,? )";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			int pos = 1;
			
			ps.setString(pos++, t.getTicketName());
			ps.setString(pos++, t.getDescription());
			ps.setLong(pos++, t.getEmployeeId());
			ps.setString(pos++, t.getReimbursementType().toString());
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				t.setTicketId(rs.getLong(1));
			}
			
			new ExpenseDAOImpl().setExpenses(t.getTicketId(), t.getExpenses());
			
			
		} catch(SQLException e) {
			e.printStackTrace();
			
		}
		return t;
	}

	@Override
	public Ticket updateTicket(Ticket t) {
		
		try (Connection conn = ConnectionFactory.getConnection()) {
			CallableStatement cs = conn.prepareCall("CALL update_ticket( ?,?,?,?,? );");
			int pos = 1;

			cs.setTimestamp(pos++, t.getResponseDate());
			cs.setString(pos++, t.getTicketName());
			cs.setString(pos++, t.getDescription());
			cs.setBoolean(pos++, t.getApprovalStatus());
			cs.setLong(pos++, t.getTicketId());
			
			cs.execute();
			
		} catch(SQLException e) {
			e.printStackTrace();
			
		}
		return t;
	}

	@Override
	public void deleteTicket(long ticket_id) {
		try (Connection conn = ConnectionFactory.getConnection()) {
			String sql = "DELETE FROM tickets WHERE ticket_id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			int pos = 1;
			ps.setLong(pos++, ticket_id);
			ps.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
			
		}
	}
	
	private Ticket setTicket(ResultSet rs) throws SQLException {
		Employee e = new Employee(
				rs.getLong("employee_id"), 
				rs.getString("first_name"),
				rs.getString("last_name"),
				rs.getString("email"),
				EmployeeRole.valueOf(rs.getString("role_name"))
			);
		Ticket t = new Ticket(
			rs.getLong("ticket_id"), 
			e.getId(), 
			e, 
			rs.getTimestamp("request_date"), 
			rs.getTimestamp("response_date"),
			rs.getString("ticket_name"), 
			rs.getString("description"), 
			rs.getObject("approval_status", Boolean.class),
			ReimbursementType.valueOf(rs.getString("type_name")));
		
		ExpenseDAO edao = new ExpenseDAOImpl();
		t.setExpenses(edao.getTicketExpenses(t.getTicketId()));
	
		return t;
	}

}
