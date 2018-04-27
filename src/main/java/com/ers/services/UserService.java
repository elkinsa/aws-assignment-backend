package com.ers.services;

import com.ers.dao.UserDAO;
import com.ers.dao.UserDAOImpl;
import com.ers.dao.UserRolesDAO;
import com.ers.dao.UserRolesDAOImpl;
import com.ers.pojos.User;
import com.ers.pojos.UserRole;
import com.ers.util.BCrypt;
import com.ers.util.HtmlEmailSender;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.commons.lang3.RandomStringUtils;

public class UserService {
	
	public static UserDAO userDao = new UserDAOImpl();
	public static UserRolesDAO userRolesDao = new UserRolesDAOImpl();

	public User createUser(User user) {
		
		if(isUsernameAvailable(user.getUsername())) {
			// generate random password and hash it
			String password = RandomStringUtils.randomAlphanumeric(10);
			String generatedSecuredPasswordHash = BCrypt.hashpw(password, BCrypt.gensalt(12));
			user.setPassword(generatedSecuredPasswordHash);
			// insert user
			user = userDao.insertUser(user);
			if(user.getUsername() != null) {
				// email new user
				emailNewUser(user,password);
				return user;
			}
		}
		
		return null;
	}
	
	private void emailNewUser(User user, String psswrd) {
		// SMTP server information
        String host = "smtp.gmail.com";
        String port = "587";
        String mailFrom = "";
        String password = "";
 
        // outgoing message information
        String mailTo = user.getEmail();
        String subject = "Welcome to Revature's Expense Reimbursement System!";
 
        // message contains HTML markups
        String message;
		try {
			message = new String(Files.readAllBytes(Paths.get("C:\\Users\\Taizia2u\\Documents\\Revature\\Week 4" 
						+ "\\ERS\\src\\main\\resources\\welcome-email-part1.html")));
			message += "<strong>Username: </strong>" + user.getUsername() + 
						"<br><br><strong>Password: </strong>" + psswrd;
			message += new String(Files.readAllBytes(Paths.get("C:\\Users\\Taizia2u\\Documents\\Revature\\Week 4" 
						+ "\\ERS\\src\\main\\resources\\welcome-email-part2.html")));
		} catch (IOException e) {
			message = "<strong>Username: </strong>" + user.getUsername() + 
						"<br><br><strong>Password: </strong>" + psswrd;
		}
 
        HtmlEmailSender mailer = new HtmlEmailSender();
 
        try {
            mailer.sendHtmlEmail(host, port, mailFrom, password, mailTo,
                    subject, message);
            System.out.println("Email sent.");
        } catch (Exception ex) {
            System.out.println("Failed to sendemail.");
            ex.printStackTrace();
        }
	}

	public User getUserByUsername(String username) {
		return userDao.getUserByUsername(username);
	}
	
	public ArrayList<User> getUsers(){
		return userDao.getAllUsers();
	}
	
	public User updateUser(User user) {
		String generatedSecuredPasswordHash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
		user.setPassword(generatedSecuredPasswordHash);
		
		if(user.getUserRoleId() == 3) {
			user.setUserRoleId(1);
		} else if(user.getUserRoleId() == 4) {
			user.setUserRoleId(2);
		}
		
		return userDao.comfirmUser(user);
	}
	
	public User loginUser(User user) {
		
		User usernameMatch = userDao.getUserByUsername(user.getUsername());
		
		if(user.getUsername() != null && usernameMatch.getUsername() != null) {
			if(BCrypt.checkpw(user.getPassword(), usernameMatch.getPassword())) {
				return usernameMatch;
			}
		}
		
		return null;
	}
	
	public boolean isUsernameAvailable(String username) {
		
		for(User user : userDao.getAllUsers()) {
			if(user.getUsername().equalsIgnoreCase(username)) {
				return false;
			}
		}
		
		return true;
		
	}

	public ArrayList<UserRole> getUserRoles(){
		return userRolesDao.getAllUserRoles();
	}
}

