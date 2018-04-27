package com.ers.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ers.pojos.User;
import com.ers.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/getusers")
public class GetUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static UserService userService = new UserService();
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<User> users = userService.getUsers();
	
		ObjectMapper om = new ObjectMapper();
		String usersString = "";
		PrintWriter pw = response.getWriter();
		
		usersString = om.writeValueAsString(users);
		pw.write(usersString);
	}

}
