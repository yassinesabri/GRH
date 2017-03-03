package com.grh.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.grh.tables.*;
import com.grh.utilities.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RecruitManager {
	public static Recruit getRow(int idRecruit) {
		String query ="SELECT * from recruit,job where recruit.idJob=job.idJob and idRecruit=?";
		ResultSet res = null;
		try (
				Connection conn = DBUtil.getConnection();
				PreparedStatement stat = conn.prepareStatement(query);
				){
			stat.setInt(1, idRecruit);
			res = stat.executeQuery();
			
			if (res.next()) {
				Recruit recruit = new Recruit();
				recruit.setIdRecruit(res.getInt("idRecruit"));
				recruit.setFirstName(res.getString("firstName"));
				recruit.setLastName(res.getString("lastName"));
				recruit.setJobName(res.getString("jobName"));
				recruit.setEmail(res.getString("email"));
				recruit.setApplicationDate(res.getString("applicationDate"));
				recruit.setStatus(res.getString("status"));
				recruit.setClosingDate(res.getString("closingDate"));		
				return recruit;
			}
			else 
				return null;
			
		} catch (SQLException e) {
			System.err.println("load row failed");
			e.printStackTrace();
			return null;
		}
	}
	
	public static ObservableList<Recruit> getAllRows() throws SQLException {
		ObservableList<Recruit> list = FXCollections.observableArrayList();
		String sql = "SELECT * from recruit,job where recruit.idJob=job.idJob ORDER BY recruit.idRecruit ASC";
		try (
				Connection conn = DBUtil.getConnection();
				Statement stat = conn.createStatement();
				ResultSet res = stat.executeQuery(sql);
				){

			while (res.next()) {
				Recruit recruit = new Recruit();
				recruit.setIdRecruit(res.getInt("idRecruit"));
				recruit.setFirstName(res.getString("firstName"));
				recruit.setLastName(res.getString("lastName"));
				recruit.setJobName(res.getString("jobName"));
				recruit.setEmail(res.getString("email"));
				recruit.setApplicationDate(res.getString("applicationDate"));
				recruit.setStatus(res.getString("status"));
				recruit.setClosingDate(res.getString("closingDate"));
				list.add(recruit);
			}
			return list;
		} catch (SQLException e) {
			System.err.println("load failed");
			e.printStackTrace();
			return null;
		}
		
	}
	public static boolean checkRecord(String firstName,String lastName) {
		String query ="SELECT * from `recruit` where firstName=? and lastName=?";
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
	public static boolean insert(Recruit recruit) throws SQLException {
		
		recruit.setIdJob(JobManager.getJobId(recruit.getJobName()));
		String query = "INSERT INTO recruit (firstName,lastName,email,idJob,applicationDate,status,closingDate,cvPath)"
				+ " VALUES (?,?,?,?,?,?,?,?)";
		try (
				Connection conn = DBUtil.getConnection();
				PreparedStatement stat = conn.prepareStatement(query);
				) {			
			stat.setString(1, recruit.getFirstName());
			stat.setString(2, recruit.getLastName());
			stat.setString(3, recruit.getEmail());
			stat.setInt(4, recruit.getIdJob());
			stat.setString(5, recruit.getApplicationDate());
			stat.setString(6, recruit.getStatus());
			stat.setString(7, recruit.getClosingDate());
			stat.setString(8, recruit.getCvPath());
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
	public static boolean delete(int idRecruit) throws Exception {

		String query = "DELETE FROM recruit WHERE idRecruit=?";
		try (
				Connection conn = DBUtil.getConnection();
				PreparedStatement stat = conn.prepareStatement(query);
				) {
			stat.setInt(1,idRecruit);
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
	
	public static boolean update(Recruit recruit) throws Exception {
		recruit.setIdJob(JobManager.getJobId(recruit.getJobName()));
		String query = "UPDATE recruit SET firstName=?,lastName=?,email=?,idJob=?,applicationDate=?,status=?,closingDate=?"
				+ " where idRecruit=?";
		try (
				Connection conn = DBUtil.getConnection();
				PreparedStatement stat = conn.prepareStatement(query);
			) {
			stat.setString(1, recruit.getFirstName());
			stat.setString(2, recruit.getLastName());
			stat.setString(3, recruit.getEmail());
			stat.setInt(4, recruit.getIdJob());
			stat.setString(5, recruit.getApplicationDate());
			stat.setString(6, recruit.getStatus());
			stat.setString(7, recruit.getClosingDate());
			stat.setInt(8, recruit.getIdRecruit());
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
	public static String getCvPath(int idRecruit) {
		String query ="SELECT * from recruit where idRecruit=?";
		ResultSet res = null;
		try (
				Connection conn = DBUtil.getConnection();
				PreparedStatement stat = conn.prepareStatement(query);
				){
			stat.setInt(1, idRecruit);
			res = stat.executeQuery();
			
			if (res.next()) {
				String path = res.getString("cvPath");		
				return path;
			}
			else 
				return null;
			
		} catch (SQLException e) {
			System.err.println("load row failed");
			e.printStackTrace();
			return null;
		}
	}
}
