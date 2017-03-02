package com.grh.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.grh.tables.Leave;
import com.grh.utilities.DBUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LeaveManager {
	public static Leave getRow(int idLeave) {
		String query ="SELECT * from `leave`,`employee` where leave.idEmp=employee.idEmp and idleave=?";
		ResultSet res = null;
		try (
				Connection conn = DBUtil.getConnection();
				PreparedStatement stat = conn.prepareStatement(query);
				){
			stat.setInt(1, idLeave);
			res = stat.executeQuery();
			
			if (res.next()) {
				Leave leave = new Leave();
				leave.setIdLeave(res.getInt("idLeave"));
				leave.setFirstName(res.getString("firstName"));
				leave.setLastName(res.getString("lastName"));
				leave.setLeaveDate(res.getString("leaveDate"));
				leave.setStatus(res.getString("status"));
				leave.setDescription(res.getString("description"));		
				return leave;
			}
			else 
				return null;
			
		} catch (SQLException e) {
			System.err.println("load row failed");
			e.printStackTrace();
			return null;
		}
	}
	public static boolean checkRow(String firstName,String lastName) {
		String query ="SELECT * from `employee` where firstName=? and lastName=?";
		ResultSet res = null;
		try (
				Connection conn = DBUtil.getConnection();
				PreparedStatement stat = conn.prepareStatement(query);
				){
			stat.setString(1, firstName);
			stat.setString(2, lastName);
			res = stat.executeQuery();
			
			if (res.next()) {	
				return true;
			}
			else 
				return false;
			
		} catch (SQLException e) {
			System.err.println("check row failed");
			e.printStackTrace();
			return false;
		}
	}
	
	public static ObservableList<Leave> getAllRows() throws SQLException {
		ObservableList<Leave> list = FXCollections.observableArrayList();
		String sql = "SELECT * from `leave`,`employee` where leave.idEmp=employee.idEmp ORDER BY leave.idleave ASC";
		try (
				Connection conn = DBUtil.getConnection();
				Statement stat = conn.createStatement();
				ResultSet res = stat.executeQuery(sql);
				){

			while (res.next()) {
				Leave leave = new Leave();
				leave.setIdLeave(res.getInt("idLeave"));
				leave.setFirstName(res.getString("firstName"));
				leave.setLastName(res.getString("lastName"));
				leave.setLeaveDate(res.getString("leaveDate"));
				leave.setStatus(res.getString("status"));
				leave.setDescription(res.getString("description"));	
				list.add(leave);
			}
			return list;
		} catch (SQLException e) {
			System.err.println("load failed");
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static boolean insert(Leave leave) throws SQLException {
		
		String query = "INSERT INTO `leave` (idEmp,status,leaveDate,description) VALUES(?,?,?,?)";
		try (
				Connection conn = DBUtil.getConnection();
				PreparedStatement stat = conn.prepareStatement(query);
				) {			
			stat.setInt(1, leave.getIdEmp());
			stat.setString(2, leave.getStatus());
			stat.setString(3, leave.getLeaveDate());
			stat.setString(4, leave.getDescription());
			int affected = stat.executeUpdate();
			//to test if the insert is successful
			if(affected == 1){
				return true;
			}
			else{
				System.err.println("No row inserted");
				return false;
			}
			
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
	}
	
	public static boolean delete(int idLeave) throws Exception {

		String query = "DELETE FROM `leave` WHERE idleave=?";
		try (
				Connection conn = DBUtil.getConnection();
				PreparedStatement stat = conn.prepareStatement(query);
				) {
			stat.setInt(1,idLeave);
			int affected = stat.executeUpdate();
			//to test if the delete is successful
			if(affected == 1){
				return true;
			}
			else{
				System.err.println("Delete operation failed");
				return false;
			}
			
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
	}
	public static boolean update(Leave leave) throws Exception {
		String query = "UPDATE `leave` SET status=?,leaveDate=?,description=? WHERE idleave=?";
		try (
				Connection conn = DBUtil.getConnection();
				PreparedStatement stat = conn.prepareStatement(query);
			) {
			stat.setString(1, leave.getStatus());
			stat.setString(2, leave.getLeaveDate());
			stat.setString(3, leave.getDescription());
			stat.setInt(4, leave.getIdLeave());
			int affected = stat.executeUpdate();
			//to test if the delete is successful
			if(affected == 1){
				return true;
			}
			else{
				System.err.println("Update operation failed");
				return false;
			}
			
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
	}
}
