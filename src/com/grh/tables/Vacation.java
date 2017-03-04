package com.grh.tables;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.grh.utilities.Checks;

public class Vacation {
	private int idVac;
	private int idEmp;
	private String firstName;
	private String lastName;
	private String status;
	private String startDate;
	private String endDate;
	private long remaining;
	
	public Vacation() {
	}

	public Vacation(int idVac, int idEmp, String firstName, String lastName, String status, String startDate,
			String endDate, long remaining) {
		super();
		this.idVac = idVac;
		this.idEmp = idEmp;
		this.firstName = firstName;
		this.lastName = lastName;
		this.status = status;
		this.startDate = startDate;
		this.endDate = endDate;
		this.remaining = remaining;
	}

	public int getIdVac() {
		return idVac;
	}

	public void setIdVac(int idVac) {
		this.idVac = idVac;
	}

	public int getIdEmp() {
		return idEmp;
	}

	public void setIdEmp(int idEmp) {
		this.idEmp = idEmp;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus() {
		if(Checks.isLessThanCurrentDate(startDate))
		this.status = "leave";
		else
		this.status = "pending";
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public long getRemaining() {
		return remaining;
	}

	public void setRemaining() {
		LocalDate now = LocalDate.now();
		LocalDate end = LocalDate.parse(this.endDate);
		this.remaining = ChronoUnit.DAYS.between(now, end);
	}
		
}
