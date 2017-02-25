package com.grh.recruit;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.grh.DAO.JobManager;
import com.grh.DAO.RecruitManager;
import com.grh.tables.Recruit;
import com.grh.utilities.Checks;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
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

public class AddRecruitController implements Initializable{

	@FXML private JFXTextField firstName;
	@FXML private JFXTextField lastName;
	@FXML private JFXTextField email;
	@FXML private JFXComboBox<String> job;
	@FXML private JFXComboBox<String> status;
	@FXML private JFXDatePicker applicationDate;
	private ObservableList<String> statusList;
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
		try {
			job.setItems(JobManager.getAllJobs());
			statusList = FXCollections.observableArrayList("pending");
			status.setItems(statusList);
			status.getSelectionModel().select("pending");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void addBtn(ActionEvent event) throws SQLException{
		if(firstName.getText().equals("") || lastName.getText().equals("") || job.getSelectionModel().getSelectedItem()==null
				|| email.getText().equals("") || applicationDate.getValue()==null){
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
		if(!Checks.isValidEmail(email.getText())){
			Alert dialog = new Alert(AlertType.WARNING);
			dialog.setTitle("Error");
			dialog.setHeaderText(null);
			dialog.setContentText("Invalid Email Format");
			Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/assets/icon.png"));
			dialog.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
			dialog.showAndWait();
			return;
		}
		if(!Checks.isLessThenCurrentDate(applicationDate.getValue().toString())){
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
		Recruit recruit = new Recruit();
		recruit.setFirstName(firstName.getText());
		recruit.setLastName(lastName.getText());
		recruit.setJobName(job.getSelectionModel().getSelectedItem().toString());
		recruit.setStatus(status.getSelectionModel().getSelectedItem().toString());
		recruit.setEmail(email.getText());
		recruit.setApplicationDate(applicationDate.getValue().toString());
		if(RecruitManager.insert(recruit)){
			System.out.println("Insert Done !");
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.close();
		}
	}
	
	public void cancelBtn(ActionEvent event){
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.close();
	}

}
