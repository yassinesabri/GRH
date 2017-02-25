package com.grh.leave;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.grh.DAO.LeaveManager;
import com.grh.tables.Leave;
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
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class UpdateLeaveController implements Initializable{
	@FXML private JFXTextField firstName;
	@FXML private JFXTextField lastName;
	@FXML private JFXComboBox<String> status;
	@FXML private JFXDatePicker leaveDate;
	@FXML private JFXTextArea description;
	private ObservableList<String> statusList;
	private int idLeave;
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

			statusList = FXCollections.observableArrayList("pending","accepted","rejected");
			status.setItems(statusList);
			Leave leave = LeaveManager.getRow(idLeave);
			if(leave == null)
				return;
			firstName.setText(leave.getFirstName());
			lastName.setText(leave.getLastName());
			status.getSelectionModel().select(leave.getStatus());
			description.setText(leave.getDescription());
			//string to date
			LocalDate date = LocalDate.parse(leave.getLeaveDate());
			leaveDate.setValue(date);
		
	}
	public void setId(int idLeave) {
		this.idLeave = idLeave;
	}
	public void updateBtn(ActionEvent event) throws Exception{
		if(firstName.getText().equals("") || lastName.getText().equals("") || description.getText().equals("") 
				|| leaveDate.getValue()==null){
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
		Leave leave = new Leave();
		leave.setIdLeave(idLeave);
		leave.setStatus(status.getSelectionModel().getSelectedItem().toString());
		leave.setDescription(description.getText());
		leave.setLeaveDate(leaveDate.getValue().toString());
		if(LeaveManager.checkRow(firstName.getText(), lastName.getText())){
			if(LeaveManager.update(leave)){
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
