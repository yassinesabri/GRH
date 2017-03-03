package com.grh.tables;

public class Leave {
	private int idLeave;
	private int idEmp;
	private String firstName;
	private String lastName;
	private String status;
	private String leaveDate;
	private String description;
	public Leave(){
		
	}
	public Leave(int idLeave, int idEmp, String firstName, String lastName, String status, String leaveDate,
			String description) {
		super();
		this.idLeave = idLeave;
		this.idEmp = idEmp;
		this.firstName = firstName;
		this.lastName = lastName;
		this.status = status;
		this.leaveDate = leaveDate;
		this.description = description;
	}
	/**
	 * @return the idLeave
	 */
	public int getIdLeave() {
		return idLeave;
	}
	/**
	 * @param idLeave the idLeave to set
	 */
	public void setIdLeave(int idLeave) {
		this.idLeave = idLeave;
	}
	/**
	 * @return the idEmp
	 */
	public int getIdEmp() {
		return idEmp;
	}
	/**
	 * @param idEmp the idEmp to set
	 */
	public void setIdEmp(int idEmp) {
		this.idEmp = idEmp;
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
	 * @return the leaveDate
	 */
	public String getLeaveDate() {
		return leaveDate;
	}
	/**
	 * @param leaveDate the leaveDate to set
	 */
	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
}
