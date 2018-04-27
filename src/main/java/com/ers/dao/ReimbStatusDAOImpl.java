package com.ers.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.ers.pojos.ReimbursementStatus;
import com.ers.util.ConnectionFactory;

public class ReimbStatusDAOImpl implements ReimbStatusDAO{

	@Override
	public ArrayList<ReimbursementStatus> getAllReimbStatuses() {
		ArrayList<ReimbursementStatus> statuses = new ArrayList<ReimbursementStatus>();

		try(Connection conn = ConnectionFactory.getInstance().getConnection();) {
			
			// get all users 
			String sql = "SELECT * FROM ERS_REIMBURSEMENT_STATUS";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			// take a row and convert it into a User object
			while(rs.next()) {
				ReimbursementStatus temp = new ReimbursementStatus();
				temp.setReimbStatusId(rs.getInt("reimb_status_id"));
				temp.setReimbStatus(rs.getString("reimb_status"));
				statuses.add(temp);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return statuses;
	}

}
