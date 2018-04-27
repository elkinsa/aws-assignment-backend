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

@WebServlet("/submitReimb")
public class SubmitReimbursementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ReimbursementService reimbService = new ReimbursementService();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if(req.getInputStream() != null) {
			ObjectMapper mapper = new ObjectMapper();
			Reimbursement reimb = mapper.readValue(req.getInputStream(), Reimbursement.class);


			reimb = reimbService.submitReimb(reimb);

			PrintWriter out = resp.getWriter();
			out.write(mapper.writeValueAsString(reimb));
		}
	}

}
