package com.revature.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.controllers.*;

public class RequestHelper {
	
	private static Map<String, ServletController> paths;
	
	static {
		if (paths == null) {
			setPaths();			
		}
	}
	
	private static void setPaths() {
		paths = new HashMap<String, ServletController>();
		String uriBase = "/P1/view/";
		
		paths.put(uriBase+"", new StartController());
		paths.put(uriBase+"login", new LoginController());
		paths.put(uriBase+"logout", new LogoutController());
		paths.put(uriBase+"register", new RegisterController());
		paths.put(uriBase+"home", new HomeController());
		paths.put(uriBase+"manager-home", new ManagerHomeController());
		paths.put(uriBase+"new-ticket", new NewTicketController());
		paths.put(uriBase+"view-ticket", new ViewTicketController());
		paths.put(uriBase+"orders", new OrderController());
	};
	
	public static void process(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// Gets Request URI
		String endpoint = req.getRequestURI();
		
		// Checks if end point has a controller
		if (paths.containsKey(endpoint)) {
			// Checks request method
			String method = req.getMethod();
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
					res.setStatus(405);
			}
		} else {
			RequestDispatcher redis = req.getRequestDispatcher("/page-not-found.html");
			redis.forward(req, res);
			res.setStatus(404);
		}
	}

}
