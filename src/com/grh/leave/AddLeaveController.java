package com.grh.leave;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.grh.DAO.EmployeeManager;
import com.grh.DAO.LeaveManager;
import com.grh.DAO.PromotionManager;
import com.grh.tables.Leave;
import com.grh.tables.Promotion;
import com.grh.utilities.Checks;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AddLeaveController implements Initializable{
	@FXML private JFXTextField firstName;
	@FXML private JFXTextField lastName;
	@FXML private JFXComboBox<String> status;
	@FXML private JFXDatePicker leaveDate;
	@FXML private JFXTextArea description;
	private ObservableList<String> statusList;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		statusList = FXCollections.observableArrayList("pending");
		status.setItems(statusList);
		status.getSelectionModel().select("pending");
		
	}
	public void addBtn(ActionEvent event) throws SQLException{
		if(firstName.getText().equals("") || lastName.getText().equals("") || description.getText().equals("") 
				|| leaveDate.getValue()==null){
			Alert dialog = new Alert(AlertType.WARNING);
			dialog.setTitle("Error");
			dialog.setHeaderText(null);
			dialog.setContentText("All Fields Are Required");
			dialog.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
			dialog.showAndWait();
			return;
		}
		if(!Checks.isLessThenCurrentDate(leaveDate.getValue().toString())){
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
		if(LeaveManager.checkRow(firstName.getText(), lastName.getText())){
			Leave leave = new Leave();
			leave.setIdEmp(EmployeeManager.getEmployeeId(firstName.getText(), lastName.getText()));
			leave.setFirstName(firstName.getText());
			leave.setLastName(lastName.getText());
			leave.setStatus(status.getSelectionModel().getSelectedItem().toString());
			leave.setDescription(description.getText());
			leave.setLeaveDate(leaveDate.getValue().toString());
			if(LeaveManager.insert(leave)){
				System.out.println("Insert Done !");
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
