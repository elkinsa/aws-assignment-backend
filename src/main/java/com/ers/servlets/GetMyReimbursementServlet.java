package com.ers.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ers.pojos.Reimbursement;
import com.ers.services.ReimbursementService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/getreimb")
public class GetMyReimbursementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ReimbursementService ReimbursementService = new ReimbursementService();
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Reimbursement reimbursements = ReimbursementService.getReimbursement(Integer.parseInt(request.getParameter("reimbId")));
	
		ObjectMapper om = new ObjectMapper();
		String reimbursementsString = "";
		PrintWriter pw = response.getWriter();
		
		reimbursementsString = om.writeValueAsString(reimbursements);
		pw.write(reimbursementsString);
	}
}