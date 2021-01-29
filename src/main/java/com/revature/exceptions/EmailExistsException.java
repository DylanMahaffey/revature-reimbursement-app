package com.revature.exceptions;

public class EmailExistsException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmailExistsException() {
		super("This email already exists");
	}

}
