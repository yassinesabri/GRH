package com.grh.tables;

public class Promotion {
	private int idProm;
	private int idEmp;
	private String firstName;
	private String lastName;
	private String status;
	private String dateProm;
	private String description;
	public Promotion(){
		
	}
	public Promotion(int idProm, int idEmp, String firstName, String lastName, String status, String dateProm,
			String description) {
		super();
		this.idProm = idProm;
		this.idEmp = idEmp;
		this.firstName = firstName;
		this.lastName = lastName;
		this.status = status;
		this.dateProm = dateProm;
		this.description = description;
	}
	/**
	 * @return the idProm
	 */
	public int getIdProm() {
		return idProm;
	}
	/**
	 * @param idProm the idProm to set
	 */
	public void setIdProm(int idProm) {
		this.idProm = idProm;
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
	 * @return the dateProm
	 */
	public String getDateProm() {
		return dateProm;
	}
	/**
	 * @param dateProm the dateProm to set
	 */
	public void setDateProm(String dateProm) {
		this.dateProm = dateProm;
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
