package com.ers.services;

import java.util.*;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.ers.dao.ReimbStatusDAO;
import com.ers.dao.ReimbStatusDAOImpl;
import com.ers.dao.ReimbTypeDAO;
import com.ers.dao.ReimbTypeDAOImpl;
import com.ers.dao.ReimbursementDAO;
import com.ers.dao.ReimbursementDAOImpl;
import com.ers.pojos.Reimbursement;
import com.ers.pojos.ReimbursementStatus;
import com.ers.pojos.ReimbursementType;
import com.ers.pojos.TopEmployees;
import com.ers.pojos.TypeTotals;

public class ReimbursementService {
	public static ReimbursementDAO reimbDao = new ReimbursementDAOImpl();
	public static ReimbTypeDAO reimbTypeDao = new ReimbTypeDAOImpl();
	public static ReimbStatusDAO reimbStatusDao = new ReimbStatusDAOImpl();
	
	public Reimbursement submitReimb(Reimbursement reimb) {
		Date d = new Date();
		Timestamp time = new Timestamp (d.getTime());
		reimb.setSubmitted(time.toString());
		// submit reimb
		reimb = reimbDao.insertReimbursement(reimb);
		return reimb;
	}
	
	public Reimbursement approveReimb(Reimbursement reimb) {
		Date d = new Date();
		Timestamp time = new Timestamp (d.getTime());
		reimb.setResolved(time.toString());
		// approve or deny reimb
		reimb = reimbDao.updateReimbursement(reimb);
		return reimb;
	}
	
	public Reimbursement getReimbursement(int id) {
		Reimbursement reimb = new Reimbursement();
		
		reimb = reimbDao.getReimbursementByReimbId(id);
		
		return reimb;
	}

	public ArrayList<Reimbursement> getReimbursements(String status) {
		ArrayList<Reimbursement> reimbs = new ArrayList<Reimbursement>();
		
		if(status.equals("all")) {
			reimbs = reimbDao.getAllReimbusements();
		} else if (status.equals("pending")) {
			reimbs = reimbDao.getReimbursementsByStatus(status);
		} else if (status.equals("resolved")) {
			reimbs = reimbDao.getReimbursementsByStatus(status);
		}
		
		return reimbs;
	}
	
	public ArrayList<Reimbursement> getMyReimbursements(String status, int id) {
		ArrayList<Reimbursement> reimbs = new ArrayList<Reimbursement>();
		
		if (status.equals("pending")) {
			reimbs = reimbDao.getReimbursementsByStatusAndByUserId(status, id);
		} else if (status.equals("resolved")) {
			reimbs = reimbDao.getReimbursementsByStatusAndByUserId(status, id);
		}
		
		return reimbs;
	}

	public ArrayList<ReimbursementType> getReimbTypes() {
		return reimbTypeDao.getAllReimbTypes();
	}

	public ArrayList<ReimbursementStatus> getReimbStatuses() {
		return reimbStatusDao.getAllReimbStatuses();
	}
	
	public ArrayList<TopEmployees> getTopEmployees() {
		return reimbDao.getTopEmployees();
	}
	
	public ArrayList<TypeTotals> getTypeTotals() {
		return reimbDao.getTypeTotals();
	}

}
