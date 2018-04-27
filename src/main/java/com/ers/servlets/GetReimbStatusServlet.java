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
import com.ers.services.ReimbursementService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/getstatuses")
public class GetReimbStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ReimbursementService reimbService = new ReimbursementService();
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<ReimbursementStatus> statuses = reimbService.getReimbStatuses();
	
		ObjectMapper om = new ObjectMapper();
		String statusesString = "";
		PrintWriter pw = response.getWriter();
		
		statusesString = om.writeValueAsString(statuses);
		pw.write(statusesString);
	}

}
