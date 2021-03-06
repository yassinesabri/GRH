package com.grh.vacation;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.grh.DAO.EmployeeManager;
import com.grh.DAO.VacationManager;
import com.grh.tables.Employee;
import com.grh.tables.Vacation;
import com.grh.utilities.Checks;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class AddVacationController implements Initializable{
	@FXML private JFXTextField firstName;
	@FXML private JFXTextField lastName;
	@FXML private JFXDatePicker startDate;
	@FXML private JFXDatePicker endDate;
	private int idEmp;
	@FXML
	public void buttonPressed(KeyEvent event) throws SQLException
	{
	    if(event.getCode().toString().equals("ENTER"))
	    {
	    	ActionEvent actionEvent = new ActionEvent(event.getSource(),event.getTarget());
	        addBtn(actionEvent);
	    }
	    if(event.getCode().toString().equals("ESCAPE"))
	    {
	    	ActionEvent actionEvent = new ActionEvent(event.getSource(),event.getTarget());
	        cancelBtn(actionEvent);
	    }
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Employee employee = EmployeeManager.getRow(idEmp);
		firstName.setText(employee.getFirstName());
		lastName.setText(employee.getLastName());		
	}
	public void setIdEmp(int idEmp){
		this.idEmp = idEmp;
	}
	public void addBtn(ActionEvent event) throws SQLException{
		if(firstName.getText().equals("") || lastName.getText().equals("") || startDate.getValue()==null 
				|| startDate.getValue()==null ){
			Alert dialog = new Alert(AlertType.WARNING);
			dialog.setTitle("Error");
			dialog.setHeaderText(null);
			dialog.setContentText("All Fields Are Required");
			dialog.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
			dialog.showAndWait();
			return;
		}
		if(Checks.isLessThanCurrentDate(startDate.getValue().toString())){
			Alert dialog = new Alert(AlertType.WARNING);
			dialog.setTitle("Error");
			dialog.setHeaderText(null);
			dialog.setContentText("Invalid start Date");
			Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/assets/icon.png"));
			dialog.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
			dialog.showAndWait();
			return;
		}
		
		if(!Checks.isLessThanStartDate(endDate.getValue().toString(),startDate.getValue().toString())){
			Alert dialog = new Alert(AlertType.WARNING);
			dialog.setTitle("Error");
			dialog.setHeaderText(null);
			dialog.setContentText("Invalid Date chronology");
			Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/assets/icon.png"));
			dialog.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
			dialog.showAndWait();
			return;
		}
		
		if(!VacationManager.checkRow(firstName.getText(), lastName.getText())){
			Alert dialog = new Alert(AlertType.WARNING);
			dialog.setTitle("Error");
			dialog.setHeaderText(null);
			dialog.setContentText("No employee with this name");
			Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/assets/icon.png"));
			dialog.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
			dialog.showAndWait();
			return;
		}
		if(!VacationManager.checkInvalidVacation(firstName.getText(), lastName.getText())){	
			Vacation vacation = new Vacation();
			vacation.setIdEmp(EmployeeManager.getEmployeeId(firstName.getText(), lastName.getText()));
			vacation.setFirstName(firstName.getText());
			vacation.setLastName(lastName.getText());
			vacation.setEndDate(endDate.getValue().toString());
			vacation.setStartDate(startDate.getValue().toString());
			vacation.setStatus();
			vacation.setRemaining();
			if(VacationManager.insert(vacation)){
				System.out.println("Insert Done !");
				Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				stage.close();
			}
		}
		else{
			Alert dialog = new Alert(AlertType.WARNING);
			dialog.setTitle("Error");
			dialog.setHeaderText(null);
			dialog.setContentText("This employee is on leave or has vacation pending!");
			Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/assets/icon.png"));
			dialog.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
			dialog.showAndWait();
		}

	}
	
	public void cancelBtn(ActionEvent event){
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.close();
	}
	
}
