package com.grh.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.grh.tables.Vacation;
import com.grh.utilities.DBUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class VacationManager {
	public static Vacation getRow(int idVac) {
		String query ="SELECT * from `vacation`,`employee` where vacation.idEmp=employee.idEmp and idVac=?";
		ResultSet res = null;
		try (
				Connection conn = DBUtil.getConnection();
				PreparedStatement stat = conn.prepareStatement(query);
				){
			stat.setInt(1, idVac);
			res = stat.executeQuery();
			
			if (res.next()) {
				Vacation vacation = new Vacation();
				vacation.setIdVac(res.getInt("idVac"));
				vacation.setFirstName(res.getString("firstName"));
				vacation.setLastName(res.getString("lastName"));
				vacation.setStartDate(res.getString("startDate"));
				vacation.setEndDate(res.getString("endDate"));	
				vacation.setRemaining();
				vacation.setStatus();
				return vacation;
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
	
	public static boolean checkInvalidVacation(String firstName,String lastName) throws SQLException {
		int id = EmployeeManager.getEmployeeId(firstName, lastName);
		String query ="SELECT * from `vacation` where idEmp=?";
		ResultSet res = null;
		try (
				Connection conn = DBUtil.getConnection();
				PreparedStatement stat = conn.prepareStatement(query);
				){
			stat.setInt(1, id);
			res = stat.executeQuery();
			
			if (res.next()) {	
				Vacation vac = new Vacation();
				vac = getRow(res.getObject("idVac",Integer.class));
				vac.setRemaining();
				vac.setStatus();
				if(!vac.getStatus().equals("done"))
				return true;
			}
				return false;
			
		} catch (SQLException e) {
			System.err.println("check vacation");
			e.printStackTrace();
			return false;
		}
	}
	
	public static ObservableList<Vacation> getAllRows() throws SQLException {
		ObservableList<Vacation> list = FXCollections.observableArrayList();
		String sql = "SELECT * from `vacation`,`employee` where vacation.idEmp=employee.idEmp ORDER BY vacation.idVac ASC";
		try (
				Connection conn = DBUtil.getConnection();
				Statement stat = conn.createStatement();
				ResultSet res = stat.executeQuery(sql);
				){

			while (res.next()) {
				Vacation vacation = new Vacation();
				vacation.setIdVac(res.getInt("idVac"));
				vacation.setFirstName(res.getString("firstName"));
				vacation.setLastName(res.getString("lastName"));
				vacation.setStartDate(res.getString("startDate"));
				vacation.setEndDate(res.getString("endDate"));	
				vacation.setRemaining();
				vacation.setStatus();
				list.add(vacation);
			}
			return list;
		} catch (SQLException e) {
			System.err.println("load failed");
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static boolean insert(Vacation vacation) throws SQLException {
		
		String query = "INSERT INTO `vacation` (idEmp,startDate,endDate) VALUES(?,?,?)";
		try (
				Connection conn = DBUtil.getConnection();
				PreparedStatement stat = conn.prepareStatement(query);
				) {			
			stat.setInt(1, vacation.getIdEmp());
			stat.setString(2, vacation.getStartDate());
			stat.setString(3, vacation.getEndDate());
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
	
	public static boolean delete(int idVac) throws Exception {

		String query = "DELETE FROM `vacation` WHERE idvac=?";
		try (
				Connection conn = DBUtil.getConnection();
				PreparedStatement stat = conn.prepareStatement(query);
				) {
			stat.setInt(1,idVac);
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
	public static boolean update(Vacation vacation) throws Exception {
		String query = "UPDATE `vacation` SET startDate=?,endDate=? WHERE idvac=?";
		try (
				Connection conn = DBUtil.getConnection();
				PreparedStatement stat = conn.prepareStatement(query);
			) {
			stat.setString(1, vacation.getStartDate());
			stat.setString(2, vacation.getEndDate());
			stat.setInt(3, vacation.getIdVac());
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
