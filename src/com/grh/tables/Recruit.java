package com.grh.tables;

public class Recruit {
	private int idRecruit;
	private int idJob;
	private String firstName;
	private String lastName;
	private String email;
	private String jobName;
	private String applicationDate;
	private String status;
	private String closingDate;
	private String cvPath;
	public Recruit(){
		
	}
	public Recruit(int idRecruit, int idJob, String firstName, String lastName, String email, String jobName,
			String applicationDate, String status, String closingDate, String cvPath) {
		super();
		this.idRecruit = idRecruit;
		this.idJob = idJob;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.jobName = jobName;
		this.applicationDate = applicationDate;
		this.status = status;
		this.closingDate = closingDate;
		this.cvPath = cvPath;
	}
	/**
	 * @return the idRecruit
	 */
	public int getIdRecruit() {
		return idRecruit;
	}
	/**
	 * @param idRecruit the idRecruit to set
	 */
	public void setIdRecruit(int idRecruit) {
		this.idRecruit = idRecruit;
	}
	/**
	 * @return the idJob
	 */
	public int getIdJob() {
		return idJob;
	}
	/**
	 * @param idJob the idJob to set
	 */
	public void setIdJob(int idJob) {
		this.idJob = idJob;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the jobName
	 */
	public String getJobName() {
		return jobName;
	}
	/**
	 * @param jobName the jobName to set
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	/**
	 * @return the applicationDate
	 */
	public String getApplicationDate() {
		return applicationDate;
	}
	/**
	 * @param applicationDate the applicationDate to set
	 */
	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the closingDate
	 */
	public String getClosingDate() {
		return closingDate;
	}
	/**
	 * @param closingDate the closingDate to set
	 */
	public void setClosingDate(String closingDate) {
		this.closingDate = closingDate;
	}
	/**
	 * @return the cvPath
	 */
	public String getCvPath() {
		return cvPath;
	}
	/**
	 * @param cvPath the cvPath to set
	 */
	public void setCvPath(String cvPath) {
		this.cvPath = cvPath;
	}
	
	
	
	
}
