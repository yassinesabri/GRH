package com.grh.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.grh.tables.Employee;
import com.grh.utilities.DBUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EmployeeRestoreManager {
	public static ObservableList<Employee> getAllRows() throws SQLException {
		ObservableList<Employee> list = FXCollections.observableArrayList();
		String sql = "SELECT * from employee_restore,job,departement where employee_restore.idJob=job.idJob and employee_restore.idDepart=departement.idDepart "
				+ "ORDER BY employee_restore.idEmp ASC";
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
public static boolean insert(Employee employee) throws SQLException {
		
		employee.setIdJob(JobManager.getJobId(employee.getJobName()));
		employee.setIdDepart(DepartementManager.getDepartId(employee.getDepartementName()));;
		String query = "INSERT INTO employee_restore (firstName,lastName,idJob,idDepart,email,phone,address,hiredDate)"
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

		String query = "DELETE FROM employee_restore WHERE idEmp=?";
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
	public static boolean deleteAll() throws Exception {

		String query = "DELETE FROM employee_restore";
		try (
				Connection conn = DBUtil.getConnection();
				Statement stat = conn.createStatement();
				) {
			int affected = stat.executeUpdate(query);
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
	public static boolean restore(Employee employee) throws Exception{
		return EmployeeRestoreManager.delete(employee.getIdEmp()) && EmployeeManager.insert(employee);
	}
}
