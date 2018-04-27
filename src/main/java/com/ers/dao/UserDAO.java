package com.ers.dao;

import java.util.ArrayList;

import com.ers.pojos.User;

public interface UserDAO {
	// insert
	public User insertUser(User newUser);
	// update
	public User comfirmUser(User user);
	// get
	public User getUserByUsername(String username);
	public ArrayList<User> getAllUsers();
}
