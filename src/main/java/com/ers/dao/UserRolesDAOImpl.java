package com.ers.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.ers.pojos.UserRole;
import com.ers.util.ConnectionFactory;

public class UserRolesDAOImpl implements UserRolesDAO{

	@Override
	public ArrayList<UserRole> getAllUserRoles() {
		ArrayList<UserRole> userRoles = new ArrayList<UserRole>();

		try(Connection conn = ConnectionFactory.getInstance().getConnection();) {
			
			// get all users 
			String sql = "SELECT * FROM ERS_USER_ROLES";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			// take a row and convert it into a User object
			while(rs.next()) {
				UserRole temp = new UserRole();
				temp.setUserRoleId(rs.getInt("ers_user_role_id"));
				temp.setUserRole(rs.getString("user_role"));
				userRoles.add(temp);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return userRoles;
	}

}
