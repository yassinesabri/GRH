package com.grh.vacation;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.grh.DAO.VacationManager;
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

public class UpdateVacationController implements Initializable{
	@FXML private JFXTextField firstName;
	@FXML private JFXTextField lastName;
	@FXML private JFXDatePicker startDate;
	@FXML private JFXDatePicker endDate;
	private int idVac;
	@FXML
	public void buttonPressed(KeyEvent event) throws Exception
	{
	    if(event.getCode().toString().equals("ENTER"))
	    {
	    	ActionEvent actionEvent = new ActionEvent(event.getSource(),event.getTarget());
	        updateBtn(actionEvent);
	    }
	    if(event.getCode().toString().equals("ESCAPE"))
	    {
	    	ActionEvent actionEvent = new ActionEvent(event.getSource(),event.getTarget());
	        cancelBtn(actionEvent);
	    }
	}
	public void initialize(URL location, ResourceBundle resources) {
			Vacation vacation = VacationManager.getRow(idVac);
			if(vacation == null)
				return;
			firstName.setText(vacation.getFirstName());
			lastName.setText(vacation.getLastName());
			//string to date
			LocalDate date = LocalDate.parse(vacation.getStartDate());
			startDate.setValue(date);
			LocalDate date1 = LocalDate.parse(vacation.getEndDate());
			endDate.setValue(date1);
	}
	public void setId(int idVac) {
		this.idVac = idVac;
	}
	public void updateBtn(ActionEvent event) throws Exception{
		if(firstName.getText().equals("") || lastName.getText().equals("") || endDate.getValue()==null 
				|| startDate.getValue()==null){
			Alert dialog = new Alert(AlertType.WARNING);
			dialog.setTitle("Error");
			dialog.setHeaderText(null);
			dialog.setContentText("All Fields Are Required");
			Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/assets/icon.png"));
			dialog.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
			dialog.showAndWait();
			return;
		}
		if(Checks.isLessThanCurrentDate(startDate.getValue().toString())){
			Alert dialog = new Alert(AlertType.WARNING);
			dialog.setTitle("Error");
			dialog.setHeaderText(null);
			dialog.setContentText("Invalid Date Format");
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
			dialog.setContentText("Invalid Date Format");
			Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/assets/icon.png"));
			dialog.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
			dialog.showAndWait();
			return;
		}
		Vacation vacation = new Vacation();
		vacation.setIdVac(idVac);
		vacation.setEndDate(endDate.getValue().toString());
		vacation.setStartDate(startDate.getValue().toString());
		vacation.setStatus();
		vacation.setRemaining();
		if(VacationManager.checkRow(firstName.getText(), lastName.getText())){
			if(VacationManager.update(vacation)){
				System.out.println("Update Done !");
				Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				stage.close();
			}
		}
		else{
			Alert dialog = new Alert(AlertType.WARNING);
			dialog.setTitle("Error");
			dialog.setHeaderText(null);
			dialog.setContentText("No Employee with this name");
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
