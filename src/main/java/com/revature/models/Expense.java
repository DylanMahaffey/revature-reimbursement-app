package com.revature.models;

public class Expense {
	
	private long expense_id;
	private long ticket_id;
	private String expense_name;
	private double price;
	
	public Expense(long expense_id, long ticket_id, String expense_name, double price) {
		super();
		this.expense_id = expense_id;
		this.ticket_id = ticket_id;
		this.expense_name = expense_name;
		this.price = price;
	}
	public Expense() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public long getExpense_id() {
		return expense_id;
	}
	public void setExpense_id(long expense_id) {
		this.expense_id = expense_id;
	}
	public long getTicket_id() {
		return ticket_id;
	}
	public void setTicket_id(long ticket_id) {
		this.ticket_id = ticket_id;
	}
	public String getExpense_name() {
		return expense_name;
	}
	public void setExpense_name(String expense_name) {
		this.expense_name = expense_name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "Expense [expense_id=" + expense_id + ", ticket_id=" + ticket_id + ", expense_name=" + expense_name
				+ ", price=" + price + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (expense_id ^ (expense_id >>> 32));
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
		Expense other = (Expense) obj;
		if (expense_id != other.expense_id)
			return false;
		return true;
	}
	
	
	
}
