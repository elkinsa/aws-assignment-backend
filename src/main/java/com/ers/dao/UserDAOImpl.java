package com.ers.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.ers.pojos.User;

import com.ers.util.ConnectionFactory;

public class UserDAOImpl implements UserDAO{

	@Override
	public User insertUser(User newUser) {
		User usr = new User();

		try(Connection conn = ConnectionFactory.getInstance().getConnection();) {
			conn.setAutoCommit(false);

			// insert into new users row the fields from newUser
			String sql = "INSERT INTO ERS_USERS (ERS_USERNAME,ERS_PASSWORD,USER_FIRST_NAME,USER_LAST_NAME,USER_EMAIL,USER_ROLE_ID) VALUES (?,?,?,?,?,?)";
			String[] keys = new String [1];
			keys[0] = "ers_users_id";

			PreparedStatement pstmt = conn.prepareStatement(sql, keys);
			pstmt.setString(1, newUser.getUsername());
			pstmt.setString(2, newUser.getPassword());
			pstmt.setString(3, newUser.getFirstName());
			pstmt.setString(4, newUser.getLastName());
			pstmt.setString(5, newUser.getEmail());
			pstmt.setInt(6, newUser.getUserRoleId());
			
			int rowsUpdated = pstmt.executeUpdate();

			ResultSet rs = pstmt.getGeneratedKeys();

			// get userid value generated in database
			if(rowsUpdated != 0) {
				while(rs.next()) {
					usr.setUserId(rs.getInt(1));
				}
				usr.setUsername(newUser.getUsername());
				usr.setPassword(newUser.getPassword());
				usr.setFirstName(newUser.getFirstName());
				usr.setLastName(newUser.getLastName());
				usr.setEmail(newUser.getEmail());
				usr.setUserRoleId(newUser.getUserRoleId());
							
				conn.commit();
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return usr;
	}

	@Override
	public User comfirmUser(User user) {
		User usr = new User();

		try(Connection conn = ConnectionFactory.getInstance().getConnection();) {

			conn.setAutoCommit(false);

			// update user with the same userid as updatedUser with updatedUser's fields
			String sql = "UPDATE ERS_USERS SET ERS_PASSWORD = ?, USER_ROLE_ID = ? WHERE ERS_USERS_ID = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getPassword());
			pstmt.setInt(2, user.getUserRoleId());
			pstmt.setInt(3, user.getUserId());

			int rowsUpdated = pstmt.executeUpdate();

			if(rowsUpdated != 0) {
				usr.setUserId(user.getUserId());
				usr.setUsername(user.getUsername());
				usr.setPassword(user.getPassword());
				usr.setFirstName(user.getFirstName());
				usr.setLastName(user.getLastName());
				usr.setEmail(user.getEmail());
				usr.setUserRoleId(user.getUserRoleId());
				conn.commit();
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return usr;
	}

	@Override
	public User getUserByUsername(String username) {
		User usr = new User();

		try(Connection conn = ConnectionFactory.getInstance().getConnection();) {

			// select all users where USERNAME = username
			String sql = "SELECT * FROM ERS_USERS WHERE ERS_USERNAME = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			// put results into usr
			while(rs.next()) {
				usr.setUserId(rs.getInt("ers_users_id"));
				usr.setUsername(username);
				usr.setPassword(rs.getString("ers_password"));
				usr.setFirstName(rs.getString("user_first_name"));
				usr.setLastName(rs.getString("user_last_name"));
				usr.setEmail(rs.getString("user_email"));
				usr.setUserRoleId(rs.getInt("user_role_id"));
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return usr;
	}

	@Override
	public ArrayList<User> getAllUsers() {
		ArrayList<User> users = new ArrayList<User>();

		try(Connection conn = ConnectionFactory.getInstance().getConnection();) {
			
			// get all users 
			String sql = "SELECT * FROM ERS_USERS";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			// take a row and convert it into a User object
			while(rs.next()) {
				User temp = new User();
				temp.setUserId(rs.getInt("ers_users_id"));
				temp.setUsername(rs.getString("ers_username"));
				temp.setPassword(rs.getString("ers_password"));
				temp.setFirstName(rs.getString("user_first_name"));
				temp.setLastName(rs.getString("user_last_name"));
				temp.setEmail(rs.getString("user_email"));
				temp.setUserRoleId(rs.getInt("user_role_id"));
				users.add(temp);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return users;
	}

}
