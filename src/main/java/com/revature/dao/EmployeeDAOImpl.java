package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.revature.exceptions.EmailExistsException;
import com.revature.models.Employee;
import com.revature.models.EmployeeRole;

public class EmployeeDAOImpl implements EmployeeDAO {

	@Override
	public Set<Employee> getAllEmployees() {
		Set<Employee> employees = new HashSet<Employee>();
		
		try (Connection conn = ConnectionFactory.getConnection()) {
			String sql = "SELECT * FROM employees_with_roles";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Employee c = new Employee(
							rs.getLong("employee_id"), 
							rs.getString("first_name"),
							rs.getString("last_name"),
							rs.getString("email"),
							EmployeeRole.valueOf(rs.getString("role_name"))
						);
				employees.add(c);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			
		}
		return employees;
	}

	@Override
	public Employee getUserById(Long id) {
		List<Employee> employees = new ArrayList<Employee>();
		
		try (Connection conn = ConnectionFactory.getConnection()) {
			String sql = "SELECT * FROM employees_with_roles WHERE employee_id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setLong(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Employee c = new Employee(
							rs.getLong("employee_id"), 
							rs.getString("first_name"),
							rs.getString("last_name"),
							rs.getString("email"),
							EmployeeRole.valueOf(rs.getString("role_name"))
						);
				employees.add(c);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			
		}
		Employee e = null;
		if (employees.size() > 0) {
			e = employees.get(0);
		}
		return e;
	}

	@Override
	public Employee getUserByEmail(String email) {
		List<Employee> employees = new ArrayList<Employee>();
		
		try (Connection conn = ConnectionFactory.getConnection()) {
			String sql = "SELECT * FROM employees_with_roles WHERE email=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, email);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Employee c = new Employee(
							rs.getLong("employee_id"), 
							rs.getString("first_name"),
							rs.getString("last_name"),
							rs.getString("email"),
							EmployeeRole.valueOf(rs.getString("role_name"))
						);
				employees.add(c);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			
		}
		Employee e = null;
		if (employees.size() > 0) {
			e = employees.get(0);
		}
		return e;
	}
	
	public Employee authenticateUser(String email, String password) {
		List<Employee> employees = new ArrayList<Employee>();
		Boolean valid = false;
		
		try (Connection conn = ConnectionFactory.getConnection()) {
			String sql = "SELECT * FROM employees_with_roles WHERE email=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, email);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Employee c = new Employee(
							rs.getLong("employee_id"), 
							rs.getString("first_name"),
							rs.getString("last_name"),
							rs.getString("email"),
							rs.getString("password"),
							EmployeeRole.valueOf(rs.getString("role_name"))
						);
				employees.add(c);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			
		}
		Employee e = null;
		if (employees.size() > 0) {
			valid = employees.get(0).getPassword().equals(password);
			if (valid) {
				e = employees.get(0);
			}
		}
		
		
		return e;
	};
	
	@Override 
	public Boolean checkForEmail(String email) {
		Employee e = getUserByEmail(email);
		Boolean exists = false;
		if (e != null) {
			exists = true;
		}
		return exists;
	}

	@Override
	public Long insertEmployee(Employee e) throws SQLException, EmailExistsException {
		String sql = "INSERT INTO employees (first_name, last_name, email, password, role_id) "
				  + "VALUES (?,?,?,?,?);";
		
		Long id = null;
		
		if(checkForEmail(e.getEmail())) {
			throw new EmailExistsException();
		}
		try(Connection conn = ConnectionFactory.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			int role_id = 1;
			switch(e.getEmployee_roll()) {
				case EMPLOYEE:
					role_id = 1;
					break;
				case ADMIN:
					role_id = 2;
					break;
			}
			
			ps.setString(1, e.getFirstName());
			ps.setString(2, e.getLastName());
			ps.setString(3, e.getEmail());
			ps.setString(4, e.getPassword());
			ps.setInt(5, role_id);
			
			int affectedRows = ps.executeUpdate();
			
			if (affectedRows == 0) {
				throw new SQLException("Creating employee failed, no rows affected");
			}
			
			try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					id = generatedKeys.getLong(1);
				}
				else {
					throw new SQLException("Creating user failed, no ID obtained.");
				}
			}
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			
		}
		
		return id;
	}

	@Override
	public Boolean updateEmployee(Employee e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteEmployee(Employee e) {
		// TODO Auto-generated method stub
		return null;
	}

}
