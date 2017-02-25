package com.grh.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.grh.utilities.DBUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class JobManager {
	
	public static int getJobId(String jobName) throws SQLException{
		PreparedStatement stat=null;
		ResultSet res=null;
		Connection con=null;
		String sql = "SELECT idJob from job WHERE jobName= ?";
		try {
			con = DBUtil.getConnection();
			stat = con.prepareStatement(sql);
			stat.setString(1, jobName);
			res = stat.executeQuery();
			if(res.next()){
				return res.getInt("idJob");
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
	
	public static ObservableList<String> getAllJobs() throws SQLException {
		ObservableList<String> list = FXCollections.observableArrayList();
		String sql = "SELECT jobName from job";
		try (
				Connection conn = DBUtil.getConnection();
				Statement stat = conn.createStatement();
				ResultSet res = stat.executeQuery(sql);
				){

			while (res.next()) {
				list.add(res.getString("jobName"));
			}
			return list;
		} catch (SQLException e) {
			System.err.println("jobName load failed");
			e.printStackTrace();
			return null;
		}
		
	}

}
