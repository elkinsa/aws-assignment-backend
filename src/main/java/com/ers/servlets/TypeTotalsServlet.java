package com.ers.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ers.pojos.TypeTotals;
import com.ers.services.ReimbursementService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/typetotals")
public class TypeTotalsServlet  extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ReimbursementService reimbService = new ReimbursementService();
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<TypeTotals> types = reimbService.getTypeTotals();
	
		ObjectMapper om = new ObjectMapper();
		String typesString = "";
		PrintWriter pw = response.getWriter();
		
		typesString = om.writeValueAsString(types);
		pw.write(typesString);
	}

}
