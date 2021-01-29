package com.revature.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.api.controllers.*;
import com.revature.controllers.ServletController;

public class ApiHelper {
	
	private static Map<String, ServletController> paths;
	
	static {
		if (paths == null) {
			setPaths();			
		}
	}
	
	private static void setPaths() {
		paths = new HashMap<String, ServletController>();
		String uriBase = "/P1/api/";
		
		paths.put(uriBase+"login", new LoginApiController());
		paths.put(uriBase+"register", new RegisterApiController());
		paths.put(uriBase+"employees", new EmployeeApiController());
		paths.put(uriBase+"tickets", new TicketApiController());
		paths.put(uriBase+"manager-tickets", new ManagerTicketsApiController());
		paths.put(uriBase+"reimbursement-types", new ReimbursementTypeApiController());
	};
	
	public static void process(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// Gets Request URI
		String endpoint = req.getRequestURI();
		System.out.println(endpoint);
		// Checks if end point has a controller
		if (paths.containsKey(endpoint)) {
			// Checks request method
			String method = req.getMethod();
			System.out.println(method);
			// Gets the appropriate controller for the end point
			ServletController controller = paths.get(endpoint);
			// Switch statement to handle the proper method
			switch(method) {
				case "GET":
					controller.get(req, res);
					break;
				case "POST":
					controller.post(req, res);
					break;
				case "PUT":
					controller.put(req, res);
					break;
				case "DELETE":
					controller.delete(req, res);
					break;
				default:
					System.out.println("Method not allowed.");
					res.setStatus(405);
			}
		} else {
			System.out.println("View does not exist.");
			res.setStatus(404);
			RequestDispatcher redis = req.getRequestDispatcher("/page-not-found.html");
			redis.forward(req, res);
		}
	}

}
