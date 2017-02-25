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

public class EmployeeManager {
	
	public static Employee getRow(int idEmp) {
		String query ="SELECT * from employee,job,departement where employee.idJob=job.idJob and employee.idDepart=departement.idDepart and employee.idEmp=?";
		ResultSet res = null;
		try (
				Connection conn = DBUtil.getConnection();
				PreparedStatement stat = conn.prepareStatement(query);
				){
			stat.setInt(1, idEmp);
			res = stat.executeQuery();
			
			if (res.next()) {
				Employee employee = new Employee();
				employee.setIdEmp(res.getInt("idEmp"));
				employee.setFirstName(res.getString("firstName"));
				employee.setLastName(res.getString("lastName"));
				employee.setJobName(res.getString("jobName"));
				employee.setSalary(res.getInt("salary"));
				employee.setDepartementName(res.getString("departementName"));
				employee.setEmail(res.getString("email"));
				employee.setPhone(res.getString("phone"));
				employee.setAddress(res.getString("address"));
				employee.setHiredDate(res.getString("hiredDate"));
				employee.setBonus(res.getInt("bonus"));
				return employee;
			}
			else 
				return null;
			
		} catch (SQLException e) {
			System.err.println("load row failed");
			e.printStackTrace();
			return null;
		}
	}
	public static int getEmployeeId(String firstName,String lastName) throws SQLException{
		PreparedStatement stat=null;
		ResultSet res=null;
		Connection con=null;
		String sql = "SELECT idEmp from employee WHERE firstName=? and lastName=?";
		try {
			con = DBUtil.getConnection();
			stat = con.prepareStatement(sql);
			stat.setString(1, firstName);
			stat.setString(2, lastName);
			res = stat.executeQuery();
			if(res.next()){
				return res.getInt("idEmp");
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
	public static ObservableList<Employee> getAllRows() throws SQLException {
		ObservableList<Employee> list = FXCollections.observableArrayList();
		String sql = "SELECT * from employee,job,departement where employee.idJob=job.idJob and employee.idDepart=departement.idDepart "
				+ "ORDER BY employee.idEmp ASC";
		try (
				Connection conn = DBUtil.getConnection();
				Statement stat = conn.createStatement();
				ResultSet res = stat.executeQuery(sql);
				){

			while (res.next()) {
				Employee employee = new Employee();
				employee.setIdEmp(res.getInt("idEmp"));
				employee.setFirstName(res.getString("firstName"));
				employee.setLastName(res.getString("lastName"));
				employee.setJobName(res.getString("jobName"));
				employee.setSalary(res.getInt("salary"));
				employee.setDepartementName(res.getString("departementName"));
				employee.setEmail(res.getString("email"));
				employee.setPhone(res.getString("phone"));
				employee.setAddress(res.getString("address"));
				employee.setHiredDate(res.getString("hiredDate"));
				employee.setBonus(res.getInt("bonus"));
				list.add(employee);
			}
			return list;
		} catch (SQLException e) {
			System.err.println("load failed");
			e.printStackTrace();
			return null;
		}
		
	}
	public static ObservableList<Employee> searchByFirstName(String name) throws SQLException {
		ObservableList<Employee> list = FXCollections.observableArrayList();
		String sql = "SELECT * from employee,job,departement where employee.idJob=job.idJob and employee.idDepart=departement.idDepart "
				+ "and employee.firstName=? ORDER BY employee.idEmp ASC";
		try (
				Connection conn = DBUtil.getConnection();
				PreparedStatement stat = conn.prepareStatement(sql);
				){
			stat.setString(1, name);
			ResultSet res = stat.executeQuery();
			while (res.next()) {
				Employee employee = new Employee();
				employee.setIdEmp(res.getInt("idEmp"));
				employee.setFirstName(res.getString("firstName"));
				employee.setLastName(res.getString("lastName"));
				employee.setJobName(res.getString("jobName"));
				employee.setSalary(res.getInt("salary"));
				employee.setDepartementName(res.getString("departementName"));
				employee.setEmail(res.getString("email"));
				employee.setPhone(res.getString("phone"));
				employee.setAddress(res.getString("address"));
				employee.setHiredDate(res.getString("hiredDate"));
				employee.setBonus(res.getInt("bonus"));
				list.add(employee);
			}
			return list;
		} catch (SQLException e) {
			System.err.println("load failed");
			e.printStackTrace();
			return null;
		}
		
	}
	public static ObservableList<Employee> searchByLastName(String name) throws SQLException {
		ObservableList<Employee> list = FXCollections.observableArrayList();
		String sql = "SELECT * from employee,job,departement where employee.idJob=job.idJob and employee.idDepart=departement.idDepart "
				+ "and employee.lastName=? ORDER BY employee.idEmp ASC";
		try (
				Connection conn = DBUtil.getConnection();
				PreparedStatement stat = conn.prepareStatement(sql);
				){
			stat.setString(1, name);
			ResultSet res = stat.executeQuery();
			while (res.next()) {
				Employee employee = new Employee();
				employee.setIdEmp(res.getInt("idEmp"));
				employee.setFirstName(res.getString("firstName"));
				employee.setLastName(res.getString("lastName"));
				employee.setJobName(res.getString("jobName"));
				employee.setSalary(res.getInt("salary"));
				employee.setDepartementName(res.getString("departementName"));
				employee.setEmail(res.getString("email"));
				employee.setPhone(res.getString("phone"));
				employee.setAddress(res.getString("address"));
				employee.setHiredDate(res.getString("hiredDate"));
				employee.setBonus(res.getInt("bonus"));
				list.add(employee);
			}
			return list;
		} catch (SQLException e) {
			System.err.println("load failed");
			e.printStackTrace();
			return null;
		}
		
	}
	public static ObservableList<Employee> searchByJob(String job) throws SQLException {
		ObservableList<Employee> list = FXCollections.observableArrayList();
		String sql = "SELECT * from employee,job,departement where employee.idJob=job.idJob and employee.idDepart=departement.idDepart "
				+ "and job.jobName=? ORDER BY employee.idEmp ASC";
		try (
				Connection conn = DBUtil.getConnection();
				PreparedStatement stat = conn.prepareStatement(sql);
				){
			stat.setString(1, job);
			ResultSet res = stat.executeQuery();
			while (res.next()) {
				Employee employee = new Employee();
				employee.setIdEmp(res.getInt("idEmp"));
				employee.setFirstName(res.getString("firstName"));
				employee.setLastName(res.getString("lastName"));
				employee.setJobName(res.getString("jobName"));
				employee.setSalary(res.getInt("salary"));
				employee.setDepartementName(res.getString("departementName"));
				employee.setEmail(res.getString("email"));
				employee.setPhone(res.getString("phone"));
				employee.setAddress(res.getString("address"));
				employee.setHiredDate(res.getString("hiredDate"));
				employee.setBonus(res.getInt("bonus"));
				list.add(employee);
			}
			return list;
		} catch (SQLException e) {
			System.err.println("load failed");
			e.printStackTrace();
			return null;
		}
		
	}
	public static ObservableList<Employee> searchByDepart(String depart) throws SQLException {
		ObservableList<Employee> list = FXCollections.observableArrayList();
		String sql = "SELECT * from employee,job,departement where employee.idJob=job.idJob and employee.idDepart=departement.idDepart "
				+ "and departement.departementName=? ORDER BY employee.idEmp ASC";
		try (
				Connection conn = DBUtil.getConnection();
				PreparedStatement stat = conn.prepareStatement(sql);
				){
			stat.setString(1, depart);
			ResultSet res = stat.executeQuery();
			while (res.next()) {
				Employee employee = new Employee();
				employee.setIdEmp(res.getInt("idEmp"));
				employee.setFirstName(res.getString("firstName"));
				employee.setLastName(res.getString("lastName"));
				employee.setJobName(res.getString("jobName"));
				employee.setSalary(res.getInt("salary"));
				employee.setDepartementName(res.getString("departementName"));
				employee.setEmail(res.getString("email"));
				employee.setPhone(res.getString("phone"));
				employee.setAddress(res.getString("address"));
				employee.setHiredDate(res.getString("hiredDate"));
				employee.setBonus(res.getInt("bonus"));
				list.add(employee);
			}
			return list;
		} catch (SQLException e) {
			System.err.println("load failed");
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	public static boolean insert(Employee employee) throws SQLException {
		
		employee.setIdJob(JobManager.getJobId(employee.getJobName()));
		employee.setIdDepart(DepartementManager.getDepartId(employee.getDepartementName()));;
		String query = "INSERT INTO employee (firstName,lastName,idJob,idDepart,email,phone,address,hiredDate)"
				+ " VALUES (?,?,?,?,?,?,?,?)";
		try (
				Connection conn = DBUtil.getConnection();
				PreparedStatement stat = conn.prepareStatement(query);
				) {			
			stat.setString(1, employee.getFirstName());
			stat.setString(2, employee.getLastName());
			stat.setInt(3, employee.getIdJob());
			stat.setInt(4, employee.getIdDepart());
			stat.setString(5, employee.getEmail());
			stat.setString(6, employee.getPhone());
			stat.setString(7, employee.getAddress());
			stat.setString(8, employee.getHiredDate());
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
	
	public static boolean delete(int idEmp) throws Exception {

		String query = "DELETE FROM employee WHERE idEmp=?";
		try (
				Connection conn = DBUtil.getConnection();
				PreparedStatement stat = conn.prepareStatement(query);
				) {
			stat.setInt(1,idEmp);
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
	
	public static boolean update(Employee employee) throws Exception {
		employee.setIdJob(JobManager.getJobId(employee.getJobName()));
		employee.setIdDepart(DepartementManager.getDepartId(employee.getDepartementName()));;
		String query = "UPDATE employee SET firstName=?,lastName=?,idJob=?,idDepart=?,email=?,phone=?,address=?,hiredDate=?,bonus=?"
				+ " where idEmp=?";
		try (
				Connection conn = DBUtil.getConnection();
				PreparedStatement stat = conn.prepareStatement(query);
			) {
			stat.setString(1, employee.getFirstName());
			stat.setString(2, employee.getLastName());
			stat.setInt(3, employee.getIdJob());
			stat.setInt(4, employee.getIdDepart());
			stat.setString(5, employee.getEmail());
			stat.setString(6, employee.getPhone());
			stat.setString(7, employee.getAddress());
			stat.setString(8, employee.getHiredDate());
			stat.setInt(9, employee.getBonus());
			stat.setInt(10, employee.getIdEmp());
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
