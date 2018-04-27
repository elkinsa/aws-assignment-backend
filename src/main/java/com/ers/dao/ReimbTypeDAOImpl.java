package com.ers.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.ers.pojos.ReimbursementType;
import com.ers.util.ConnectionFactory;

public class ReimbTypeDAOImpl implements ReimbTypeDAO{

	@Override
	public ArrayList<ReimbursementType> getAllReimbTypes() {
		ArrayList<ReimbursementType> types = new ArrayList<ReimbursementType>();

		try(Connection conn = ConnectionFactory.getInstance().getConnection();) {
			
			// get all users 
			String sql = "SELECT * FROM ERS_REIMBURSEMENT_TYPE";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			// take a row and convert it into a User object
			while(rs.next()) {
				ReimbursementType temp = new ReimbursementType();
				temp.setReimbTypeId(rs.getInt("reimb_type_id"));
				temp.setReimbType(rs.getString("reimb_type"));
				types.add(temp);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return types;
	}

}
