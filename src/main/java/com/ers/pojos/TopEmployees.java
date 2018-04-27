package com.ers.pojos;

public class TopEmployees {
	private int employeeId;
	private double total;
	
	public TopEmployees() {
		super();
	}

	public TopEmployees(int employeeId, double total) {
		super();
		this.employeeId = employeeId;
		this.total = total;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + employeeId;
		long temp;
		temp = Double.doubleToLongBits(total);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TopEmployees other = (TopEmployees) obj;
		if (employeeId != other.employeeId)
			return false;
		if (Double.doubleToLongBits(total) != Double.doubleToLongBits(other.total))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TopEmployees [employeeId=" + employeeId + ", total=" + total + "]";
	}
}
