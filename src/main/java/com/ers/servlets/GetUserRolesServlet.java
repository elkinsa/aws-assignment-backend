package com.ers.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ers.pojos.UserRole;
import com.ers.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/getuserroles")
public class GetUserRolesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static UserService userService = new UserService();
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<UserRole> userRoles = userService.getUserRoles();
	
		ObjectMapper om = new ObjectMapper();
		String userRolesString = "";
		PrintWriter pw = response.getWriter();
		
		userRolesString = om.writeValueAsString(userRoles);
		pw.write(userRolesString);
	}

}
