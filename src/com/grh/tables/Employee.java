package com.grh.tables;

public class Employee {
	private int idEmp;
	private int idJob;
	private int idDepart;
	private String firstName;
	private String lastName;
	private String jobName;
	private int salary;
	private String departementName;
	private String email;
	private String phone;
	private String address;
	private String hiredDate;
	private int bonus;
	
	public Employee(){
		
	}
	public Employee(int idEmp, int idJob, int idDepart, String firstName, String lastName, String jobName, int salary,
			String departementName, String email, String phone, String address, String hiredDate, int bonus) {
		super();
		this.idEmp = idEmp;
		this.idJob = idJob;
		this.idDepart = idDepart;
		this.firstName = firstName;
		this.lastName = lastName;
		this.jobName = jobName;
		this.salary = salary;
		this.departementName = departementName;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.hiredDate = hiredDate;
		this.bonus = bonus;
	}
	public int getIdEmp() {
		return idEmp;
	}
	public void setIdEmp(int idEmp) {
		this.idEmp = idEmp;
	}
	public int getIdJob() {
		return idJob;
	}
	public void setIdJob(int idJob) {
		this.idJob = idJob;
	}
	public int getIdDepart() {
		return idDepart;
	}
	public void setIdDepart(int idDepart) {
		this.idDepart = idDepart;
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
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public String getDepartementName() {
		return departementName;
	}
	public void setDepartementName(String departementName) {
		this.departementName = departementName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getHiredDate() {
		return hiredDate;
	}
	public void setHiredDate(String hiredDate) {
		this.hiredDate = hiredDate;
	}
	public int getBonus() {
		return bonus;
	}
	public void setBonus(int bonus) {
		this.bonus = bonus;
	}
	
	

}

