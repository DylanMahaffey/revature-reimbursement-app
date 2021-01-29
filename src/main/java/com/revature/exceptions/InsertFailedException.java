package com.revature.exceptions;

public class InsertFailedException extends Exception {
	
	public InsertFailedException(String msg) {
		super("Insert failed: " + msg);
	}

}
