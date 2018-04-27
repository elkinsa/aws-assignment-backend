package com.ers.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ers.pojos.Reimbursement;
import com.ers.services.ReimbursementService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/getreimbs")
public class GetReimbursementsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ReimbursementService ReimbursementService = new ReimbursementService();
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Reimbursement> reimbursements = ReimbursementService.getReimbursements(request.getParameter("status"));
	
		ObjectMapper om = new ObjectMapper();
		String reimbursementsString = "";
		PrintWriter pw = response.getWriter();
		
		reimbursementsString = om.writeValueAsString(reimbursements);
		pw.write(reimbursementsString);
	}
}