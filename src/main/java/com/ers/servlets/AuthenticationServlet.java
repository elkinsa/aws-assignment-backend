package com.ers.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ers.pojos.User;
import com.ers.services.UserService;

@WebServlet("/login")
public class AuthenticationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static UserService userService = new UserService();
       
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if(req.getInputStream() != null) {
			ObjectMapper mapper = new ObjectMapper();
			User user = mapper.readValue(req.getInputStream(), User.class);

			user = userService.loginUser(user);

			if(user != null) {
				user.setPassword(""); // once authenticated, do not pass user's password around
				HttpSession session = req.getSession();
				session.setAttribute("user", user); // persist user to session
			}

			PrintWriter out = resp.getWriter();
			out.write(mapper.writeValueAsString(user));
		}
	}

}
