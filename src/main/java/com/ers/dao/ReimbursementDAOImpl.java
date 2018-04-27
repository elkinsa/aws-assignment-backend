package com.ers.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.ers.pojos.Reimbursement;
import com.ers.pojos.TopEmployees;
import com.ers.pojos.TypeTotals;
import com.ers.util.ConnectionFactory;

import oracle.jdbc.OracleTypes;

public class ReimbursementDAOImpl implements ReimbursementDAO{

	@Override
	public Reimbursement insertReimbursement(Reimbursement reimb) {
		Reimbursement reimbursement = new Reimbursement();

		try(Connection conn = ConnectionFactory.getInstance().getConnection();) {
			conn.setAutoCommit(false);

			// insert into new USERS row the fields from newUser
			String sql = "INSERT INTO ERS_REIMBURSEMENT (REIMB_AMOUNT,REIMB_SUBMITTED, REIMB_DESCRIPTION,REIMB_AUTHOR,REIMB_STATUS_ID,REIMB_TYPE_ID) VALUES (?,?,?,?,?,?)";
			String[] keys = new String [1];
			keys[0] = "reimb_id";
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		    Date parsedDate;
			try {
				parsedDate = dateFormat.parse(reimb.getSubmitted());
				Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

				PreparedStatement pstmt = conn.prepareStatement(sql, keys);
				pstmt.setDouble(1, reimb.getAmount());
				pstmt.setTimestamp(2, timestamp);
				pstmt.setString(3, reimb.getDescription());
				pstmt.setInt(4, reimb.getAuthorId());
				pstmt.setInt(5, reimb.getStatusId());
				pstmt.setInt(6, reimb.getTypeId());
				
				int rowsUpdated = pstmt.executeUpdate();

				ResultSet rs = pstmt.getGeneratedKeys();

				// get reimbursement id value generated in database
				if(rowsUpdated != 0) {
					while(rs.next()) {
						reimbursement.setReimbId(rs.getInt(1));
					}
					reimbursement.setAmount(reimb.getAmount());
					reimbursement.setSubmitted(reimb.getSubmitted());
					reimbursement.setDescription(reimb.getDescription());
					reimbursement.setAuthorId(reimb.getAuthorId());
					reimbursement.setStatusId(reimb.getStatusId());
					reimbursement.setTypeId(reimb.getTypeId());
								
					conn.commit();
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return reimbursement;
	}

	@Override
	public Reimbursement updateReimbursement(Reimbursement updatedReimb) {
		Reimbursement reimbursement = new Reimbursement();

		try(Connection conn = ConnectionFactory.getInstance().getConnection();) {

			conn.setAutoCommit(false);

			// update reimbursement when resolved
			String sql = "UPDATE ERS_REIMBURSEMENT SET REIMB_RESOLVED = ?, REIMB_RECEIPT = ?, REIMB_RESOLVER = ?, REIMB_STATUS_ID  = ? WHERE REIMB_ID = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		    Date parsedDate;
			try {
				parsedDate = dateFormat.parse(updatedReimb.getSubmitted());
				Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
				
				
				pstmt.setTimestamp(1, timestamp);
				pstmt.setBlob(2, updatedReimb.getReceipt());
				pstmt.setInt(3, updatedReimb.getResolverId());	
				pstmt.setInt(4,  updatedReimb.getStatusId());
				pstmt.setInt(5, updatedReimb.getReimbId());
	
				int rowsUpdated = pstmt.executeUpdate();
	
				if(rowsUpdated != 0) {
					reimbursement.setReimbId(updatedReimb.getReimbId());
					reimbursement.setAmount(updatedReimb.getAmount());
					reimbursement.setSubmitted(updatedReimb.getSubmitted());
					reimbursement.setResolved(updatedReimb.getResolved());
					reimbursement.setReceipt(updatedReimb.getReceipt());
					reimbursement.setAuthorId(updatedReimb.getAuthorId());
					reimbursement.setResolverId(updatedReimb.getResolverId());
					reimbursement.setStatusId(updatedReimb.getStatusId());
					reimbursement.setTypeId(updatedReimb.getTypeId());
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return reimbursement;
	}
	
	@Override
	public Reimbursement getReimbursementByReimbId(int reimbid){
		Reimbursement reimb = new Reimbursement();

		try(Connection conn = ConnectionFactory.getInstance().getConnection();) {

			String sql = "SELECT * FROM ERS_REIMBURSEMENT WHERE REIMB_ID = ?";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reimbid);
			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
				reimb.setReimbId(reimbid);
				reimb.setAmount(rs.getDouble("reimb_amount"));
				reimb.setSubmitted(rs.getString("reimb_submitted"));
				reimb.setResolved(rs.getString("reimb_resolved"));
				reimb.setDescription(rs.getString("reimb_description"));
				reimb.setReceipt(rs.getBlob("reimb_receipt"));
				reimb.setAuthorId(rs.getInt("reimb_author"));
				reimb.setResolverId(rs.getInt("reimb_resolver"));
				reimb.setStatusId(rs.getInt("reimb_status_id"));
				reimb.setTypeId(rs.getInt("reimb_type_id"));
			}


		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return reimb;
	}

	@Override
	public ArrayList<Reimbursement> getReimbursementsByUserId(int userid) {
		ArrayList<Reimbursement> reimbs = new ArrayList<Reimbursement>();

		try(Connection conn = ConnectionFactory.getInstance().getConnection();) {

			// select all reimbursements for user
			String sql = "SELECT * FROM ERS_REIMBURSEMENT WHERE REIMB_AUTHOR = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userid);
			ResultSet rs = pstmt.executeQuery();
			// put results into reimb
			while(rs.next()) {
				Reimbursement temp = new Reimbursement();
				temp.setReimbId(rs.getInt("reimb_id"));
				temp.setAmount(rs.getDouble("reimb_amount"));
				temp.setSubmitted(rs.getString("reimb_submitted"));
				temp.setResolved(rs.getString("reimb_resolved"));
				temp.setDescription(rs.getString("reimb_description"));
				temp.setReceipt(rs.getBlob("reimb_receipt"));
				temp.setAuthorId(rs.getInt("reimb_author"));
				temp.setResolverId(rs.getInt("reimb_resolver"));
				temp.setStatusId(rs.getInt("reimb_status_id"));
				temp.setTypeId(rs.getInt("reimb_type_id"));
				reimbs.add(temp);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return reimbs;
	}

	@Override
	public ArrayList<Reimbursement> getReimbursementsByStatusAndByUserId(String status, int userId) {
		ArrayList<Reimbursement> reimbs = new ArrayList<Reimbursement>();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection();) {
			
			String sql = "{CALL GET_REIMB_BY_STATUS_AND_AUTHOR(?,?,?)}";
			CallableStatement cstmt = conn.prepareCall(sql);
			
			// Setting parameters is the same as we would if we were working with a PreparedStatement
			cstmt.setString(1, status);
			cstmt.setInt(2, userId);
			
			// define the index of our second ?, and its type
			cstmt.registerOutParameter(3, OracleTypes.CURSOR);
			
			// execute our callable statement
			cstmt.execute();
			
			ResultSet rs = (ResultSet) cstmt.getObject(3);
			
			while(rs.next()) {
				Reimbursement temp = getReimbursementByReimbId(rs.getInt("reimb_id"));
				temp.setReimbId(rs.getInt("reimb_id"));
				temp.setAmount(rs.getDouble("reimb_amount"));
				temp.setSubmitted(rs.getString("reimb_submitted"));
				temp.setResolved(rs.getString("reimb_resolved"));
				temp.setDescription(rs.getString("reimb_description"));
				temp.setReceipt(rs.getBlob("reimb_receipt"));
				temp.setAuthorId(rs.getInt("reimb_author"));
				temp.setResolverId(rs.getInt("reimb_resolver"));
				temp.setStatusId(rs.getInt("reimb_status_id"));
				temp.setTypeId(rs.getInt("reimb_type_id"));
				reimbs.add(temp);
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return reimbs;
	}

	@Override
	public ArrayList<Reimbursement> getReimbursementsByStatus(String status) {
		ArrayList<Reimbursement> reimbs = new ArrayList<Reimbursement>();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection();) {
			
			String sql = "{CALL GET_REIMB_BY_STATUS(?,?)}";
			CallableStatement cstmt = conn.prepareCall(sql);
			
			// Setting parameters is the same as we would if we were working with a PreparedStatement
			cstmt.setString(1, status);
			
			// define the index of our second ?, and its type
			cstmt.registerOutParameter(2, OracleTypes.CURSOR);
			
			// execute our callable statement
			cstmt.execute();
			
			ResultSet rs = (ResultSet) cstmt.getObject(2);
			
			while(rs.next()) {
				Reimbursement temp = getReimbursementByReimbId(rs.getInt("reimb_id"));
				temp.setReimbId(rs.getInt("reimb_id"));
				temp.setAmount(rs.getDouble("reimb_amount"));
				temp.setSubmitted(rs.getString("reimb_submitted"));
				temp.setResolved(rs.getString("reimb_resolved"));
				temp.setDescription(rs.getString("reimb_description"));
				temp.setReceipt(rs.getBlob("reimb_receipt"));
				temp.setAuthorId(rs.getInt("reimb_author"));
				temp.setResolverId(rs.getInt("reimb_resolver"));
				temp.setStatusId(rs.getInt("reimb_status_id"));
				temp.setTypeId(rs.getInt("reimb_type_id"));
				reimbs.add(temp);
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return reimbs;
	}

	@Override
	public ArrayList<Reimbursement> getAllReimbusements() {
		ArrayList<Reimbursement> reimbs = new ArrayList<Reimbursement>();

		try(Connection conn = ConnectionFactory.getInstance().getConnection();) {
			
			// get all reimbursements
			String sql = "SELECT * FROM ERS_REIMBURSEMENT";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			// take a row and convert it into a User object
			while(rs.next()) {
				Reimbursement temp = new Reimbursement();
				temp.setReimbId(rs.getInt("reimb_id"));
				temp.setAmount(rs.getDouble("reimb_amount"));
				temp.setSubmitted(rs.getString("reimb_submitted"));
				temp.setResolved(rs.getString("reimb_resolved"));
				temp.setDescription(rs.getString("reimb_description"));
				temp.setReceipt(rs.getBlob("reimb_receipt"));
				temp.setAuthorId(rs.getInt("reimb_author"));
				temp.setResolverId(rs.getInt("reimb_resolver"));
				temp.setStatusId(rs.getInt("reimb_status_id"));
				temp.setTypeId(rs.getInt("reimb_type_id"));
				reimbs.add(temp);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return reimbs;
	}

	@Override
	public ArrayList<TopEmployees> getTopEmployees() {
		ArrayList<TopEmployees> emps = new ArrayList<TopEmployees>();

		try(Connection conn = ConnectionFactory.getInstance().getConnection();) {

			// select all reimbursements for user
			String sql = "SELECT ERS_REIMBURSEMENT.REIMB_AUTHOR, SUM(ERS_REIMBURSEMENT.REIMB_AMOUNT) as TOTAL " + 
					"FROM ERS_REIMBURSEMENT " + 
					"WHERE ERS_REIMBURSEMENT.REIMB_STATUS_ID = ? AND ROWNUM <= ? " + 
					"GROUP BY ERS_REIMBURSEMENT.REIMB_AUTHOR";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 2);
			pstmt.setInt(2, 5);
			
			ResultSet rs = pstmt.executeQuery();
			
			// put results into reimb
			while(rs.next()) {
				TopEmployees temp = new TopEmployees();
				temp.setEmployeeId(rs.getInt("reimb_author"));
				temp.setTotal(rs.getDouble("total"));
				emps.add(temp);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return emps;
	}

	@Override
	public ArrayList<TypeTotals> getTypeTotals() {
		ArrayList<TypeTotals> types = new ArrayList<TypeTotals>();

		try(Connection conn = ConnectionFactory.getInstance().getConnection();) {

			// select all reimbursements for user
			String sql = "SELECT ERS_REIMBURSEMENT.REIMB_TYPE_ID, SUM(ERS_REIMBURSEMENT.REIMB_AMOUNT) as TOTAL "
					+ "FROM ERS_REIMBURSEMENT "
					+ "WHERE ERS_REIMBURSEMENT.REIMB_STATUS_ID = ? "
					+ "GROUP BY ERS_REIMBURSEMENT.REIMB_TYPE_ID";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 2);
			
			ResultSet rs = pstmt.executeQuery();
			// put results into reimb
			while(rs.next()) {
				TypeTotals temp = new TypeTotals();
				temp.setTypeId(rs.getInt("reimb_type_id"));
				temp.setTotal(rs.getDouble("total"));
				types.add(temp);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return types;
	}

}
