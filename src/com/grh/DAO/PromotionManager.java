package com.grh.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.grh.utilities.DBUtil;
import com.grh.tables.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PromotionManager {
	public static Promotion getRow(int idProm) {
		String query ="SELECT * from promotion,employee where promotion.idEmp=employee.idEmp and idProm=?";
		ResultSet res = null;
		try (
				Connection conn = DBUtil.getConnection();
				PreparedStatement stat = conn.prepareStatement(query);
				){
			stat.setInt(1, idProm);
			res = stat.executeQuery();
			
			if (res.next()) {
				Promotion promotion = new Promotion();
				promotion.setIdProm(res.getInt("idProm"));
				promotion.setFirstName(res.getString("firstName"));
				promotion.setLastName(res.getString("lastName"));
				promotion.setDateProm(res.getString("dateProm"));
				promotion.setStatus(res.getString("status"));
				promotion.setDescription(res.getString("description"));		
				return promotion;
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
		String query ="SELECT * from employee where firstName=? and lastName=?";
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
	
	public static ObservableList<Promotion> getAllRows() throws SQLException {
		ObservableList<Promotion> list = FXCollections.observableArrayList();
		String sql = "SELECT * from promotion,employee where promotion.idEmp=employee.idEmp ORDER BY promotion.idProm ASC";
		try (
				Connection conn = DBUtil.getConnection();
				Statement stat = conn.createStatement();
				ResultSet res = stat.executeQuery(sql);
				){

			while (res.next()) {
				Promotion promotion = new Promotion();
				promotion.setIdProm(res.getInt("idProm"));
				promotion.setFirstName(res.getString("firstName"));
				promotion.setLastName(res.getString("lastName"));
				promotion.setDateProm(res.getString("dateProm"));
				promotion.setStatus(res.getString("status"));
				promotion.setDescription(res.getString("description"));	
				list.add(promotion);
			}
			return list;
		} catch (SQLException e) {
			System.err.println("load failed");
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static boolean insert(Promotion promotion) throws SQLException {
		
		String query = "INSERT INTO promotion (idEmp,status,dateprom,description) VALUES (?,?,?,?)";
		try (
				Connection conn = DBUtil.getConnection();
				PreparedStatement stat = conn.prepareStatement(query);
				) {			
			stat.setInt(1, promotion.getIdEmp());
			stat.setString(2, promotion.getStatus());
			stat.setString(3, promotion.getDateProm());
			stat.setString(4, promotion.getDescription());
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
	
	public static boolean delete(int idProm) throws Exception {

		String query = "DELETE FROM promotion WHERE idProm=?";
		try (
				Connection conn = DBUtil.getConnection();
				PreparedStatement stat = conn.prepareStatement(query);
				) {
			stat.setInt(1,idProm);
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
	public static boolean update(Promotion promotion) throws Exception {
		String query = "UPDATE promotion SET status=?,dateprom=?,description=? where idProm=?";
		try (
				Connection conn = DBUtil.getConnection();
				PreparedStatement stat = conn.prepareStatement(query);
			) {
			stat.setString(1, promotion.getStatus());
			stat.setString(2, promotion.getDateProm());
			stat.setString(3, promotion.getDescription());
			stat.setInt(4, promotion.getIdProm());
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
