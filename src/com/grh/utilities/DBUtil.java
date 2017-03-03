package com.grh.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {
	private static final String USERNAME = "dbuser";
	private static final String PASSWORD = "dbpassword";
	private static final String CONN_STRING = "jdbc:mysql://localhost/grh";
	
	public static Connection getConnection(){
		try {
			return DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
		} catch (SQLException e) {
			return null;
		}
	}
	public static boolean authentification(String username,String password) throws SQLException{
		PreparedStatement stat=null;
		ResultSet res=null;
		Connection con=null;
		String sql = "SELECT * from login WHERE username= ? and password = ?";
		try {
			con = DBUtil.getConnection();
			stat = con.prepareStatement(sql);
			stat.setString(1, username);
			stat.setString(2, password);
			res = stat.executeQuery();
			if(res.next())
				return res.getString("username").equals(username) && res.getString("password").equals(password);
			else
				return false;
		} catch (Exception e) {
			return false;
		}
		finally{
			res.close();		
			stat.close();
			con.close();
		}
	}
	public static void chanePassword(String username,String password) throws SQLException{
		PreparedStatement stat=null;
		Connection con=null;
		String sql = "UPDATE login SET password=? WHERE username= ?";
		try {
			con = DBUtil.getConnection();
			stat = con.prepareStatement(sql);
			stat.setString(1, password);
			stat.setString(2, username);
			int affected = stat.executeUpdate();
			if(affected==1)
				System.out.println("password changed");
			else
				System.err.println("password changing failed");
		} catch (Exception e) {
			System.err.println("password changing failed");
		}
		finally{
			stat.close();
			con.close();
		}
	}
}
