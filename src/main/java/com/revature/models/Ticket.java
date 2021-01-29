package com.revature.models;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Ticket {
	
	private long ticketId;
	private long employeeId;
	private Employee employee;
	private Timestamp requestDate;
	private Timestamp responseDate;
	private String ticketName;
	private String description;
	private Boolean approvalStatus;
	private ReimbursementType typeName;
	
	private Expense[] expenses;
	
	
	public Ticket(long ticketId, long employeeId, Employee employee, Timestamp requestDate, Timestamp responseDate,
			String ticketName, String description, Boolean approvalStatus, ReimbursementType typeName) {
		super();
		this.ticketId = ticketId;
		this.employeeId = employeeId;
		this.employee = employee;
		this.requestDate = requestDate;
		this.responseDate = responseDate;
		this.ticketName = ticketName;
		this.description = description;
		this.approvalStatus = approvalStatus;
		this.typeName = typeName;
	}
	public Ticket(long ticketId, long employeeId, Timestamp requestDate, Timestamp responseDate, String ticketName,
			String description, Boolean approvalStatus, ReimbursementType typeName) {
		super();
		this.ticketId = ticketId;
		this.employeeId = employeeId;
		this.requestDate = requestDate;
		this.responseDate = responseDate;
		this.ticketName = ticketName;
		this.description = description;
		this.approvalStatus = approvalStatus;
		this.typeName = typeName;
	}
	public Ticket(String ticketName, String description, long employeeId, ReimbursementType typeName) {
		this.ticketName = ticketName;
		this.description = description;
		this.employeeId = employeeId;
		this.typeName = typeName;
	}
	public Ticket() {
		super();
	}
	public long getTicketId() {
		return ticketId;
	}
	public void setTicketId(long ticketId) {
		this.ticketId = ticketId;
	}
	public long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Timestamp getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Timestamp requestDate) {
		this.requestDate = requestDate;
	}
	public Timestamp getResponseDate() {
		return responseDate;
	}
	public void setResponseDate(Timestamp responseDate) {
		this.responseDate = responseDate;
	}
	public String getTicketName() {
		return ticketName;
	}
	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(Boolean approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public ReimbursementType getReimbursementType() {
		return typeName;
	}
	public void setReimbursementType(ReimbursementType reimbursementType) {
		this.typeName = reimbursementType;
	}
	
	public Expense[] getExpenses() {
		return expenses;
	}
	public void setExpenses(Expense[] expenses) {
		this.expenses = expenses;
	}
	public void addExpense(Expense expense) {
		Expense[] arr = Arrays.copyOf(this.expenses, this.expenses.length + 1);
	    arr[arr.length - 1] = expense;
		this.expenses = arr;
	}
	
	@Override
	public String toString() {
		return "Ticket [ticketId=" + ticketId + ", employeeId=" + employeeId + ", employee=" + employee
				+ ", requestDate=" + requestDate + ", responseDate=" + responseDate + ", ticketName=" + ticketName
				+ ", description=" + description + ", approvalStatus=" + approvalStatus + ", typeName=" + typeName
				+ ", expenses2=" + Arrays.toString(expenses) + "]\n";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (ticketId ^ (ticketId >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		if (ticketId != other.ticketId)
			return false;
		return true;
	}
	

}
