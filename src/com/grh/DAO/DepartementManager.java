package com.grh.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.grh.utilities.DBUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DepartementManager {

	public static int getDepartId(String DepartName) throws SQLException{
		PreparedStatement stat=null;
		ResultSet res=null;
		Connection con=null;
		String sql = "SELECT idDepart from departement WHERE DepartementName= ?";
		try {
			con = DBUtil.getConnection();
			stat = con.prepareStatement(sql);
			stat.setString(1, DepartName);
			res = stat.executeQuery();
			if(res.next()){
				return res.getInt("idDepart");
			}
			else
				return 0;
		} catch (Exception e) {
			return 0;
		}
		finally{
			res.close();		
			stat.close();
			con.close();
		}
	}
	public static ObservableList<String> getAllDeparts() throws SQLException {
		ObservableList<String> list = FXCollections.observableArrayList();
		String sql = "SELECT departementName from departement";
		try (
				Connection conn = DBUtil.getConnection();
				Statement stat = conn.createStatement();
				ResultSet res = stat.executeQuery(sql);
				){

			while (res.next()) {
				list.add(res.getString("departementName"));
			}
			return list;
		} catch (SQLException e) {
			System.err.println("departementName load failed");
			e.printStackTrace();
			return null;
		}
		
	}
}
