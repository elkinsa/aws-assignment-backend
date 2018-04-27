package com.ers.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ers.pojos.ReimbursementStatus;
import com.ers.pojos.TopEmployees;
import com.ers.services.ReimbursementService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/topemployees")
public class TopEmployeesServlet  extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ReimbursementService reimbService = new ReimbursementService();
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<TopEmployees> emps = reimbService.getTopEmployees();
	
		ObjectMapper om = new ObjectMapper();
		String empsString = "";
		PrintWriter pw = response.getWriter();
		
		empsString = om.writeValueAsString(emps);
		pw.write(empsString);
	}

}
