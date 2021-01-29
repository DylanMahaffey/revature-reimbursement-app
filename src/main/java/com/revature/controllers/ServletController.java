package com.revature.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ServletController {
	
	public void get(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException;
	public void post(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException;
	public void put(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException;
	public void delete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException;

}
