package com.ers.dao;

import java.util.ArrayList;

import com.ers.pojos.Reimbursement;
import com.ers.pojos.TopEmployees;
import com.ers.pojos.TypeTotals;

public interface ReimbursementDAO {
	// insert
	public Reimbursement insertReimbursement(Reimbursement reimb);
	// update
	public Reimbursement updateReimbursement(Reimbursement updatedReimb);
	// get
	public Reimbursement getReimbursementByReimbId(int reimbid);
	public ArrayList<Reimbursement> getReimbursementsByUserId(int userid);
	public ArrayList<Reimbursement> getReimbursementsByStatusAndByUserId(String status, int userId);
	public ArrayList<Reimbursement> getReimbursementsByStatus(String status);
	public ArrayList<Reimbursement> getAllReimbusements();
	// get stats
	public ArrayList<TopEmployees> getTopEmployees();
	public ArrayList<TypeTotals> getTypeTotals();
}
