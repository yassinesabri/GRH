package com.grh.recruit;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.grh.DAO.JobManager;
import com.grh.DAO.RecruitManager;
import com.grh.tables.Recruit;
import com.grh.utilities.Checks;
import com.jfoenix.controls.JFXButton;
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
import javafx.stage.Stage;

public class UpdateRecruitController implements Initializable {
	@FXML private JFXTextField firstName;
	@FXML private JFXTextField lastName;
	@FXML private JFXTextField email;
	@FXML private JFXComboBox<String> job;
	@FXML private JFXComboBox<String> status;
	@FXML private JFXDatePicker applicationDate;
	@FXML private JFXDatePicker closingDate;
	@FXML private JFXButton updateButton;
	private ObservableList<String> statusList;
	private int idRecruit;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			job.setItems(JobManager.getAllJobs());
			closingDate.setDisable(true);
			updateButton.setDisable(true);
			statusList = FXCollections.observableArrayList("pending","accepted","rejected");
			status.setItems(statusList);
			status.setOnAction((e) -> {
	             if(status.getSelectionModel().getSelectedItem().toString().equals("accepted") || status.getSelectionModel().getSelectedItem().toString().equals("rejected"))
	            	 closingDate.setDisable(false);
	             else
	            	 closingDate.setDisable(true);
	        });
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int idRecruit) {
		this.idRecruit = idRecruit;
	}
	public void loadData(ActionEvent event) throws ParseException{
		updateButton.setDisable(false);
		Recruit recruit = RecruitManager.getRow(idRecruit);
		if(recruit == null)
			return;
		firstName.setText(recruit.getFirstName());
		lastName.setText(recruit.getLastName());
		job.getSelectionModel().select(recruit.getJobName());
		email.setText(recruit.getEmail());
		status.getSelectionModel().select(recruit.getStatus());
		//format string date
		LocalDate date = LocalDate.parse(recruit.getApplicationDate());
		applicationDate.setValue(date);
		if(recruit.getClosingDate() != null){
			LocalDate date2 = LocalDate.parse(recruit.getClosingDate());
			closingDate.setValue(date2);
		}		
		
	}
	public void updateBtn(ActionEvent event) throws Exception{
		if(firstName.getText().equals("") || lastName.getText().equals("") || job.getSelectionModel().getSelectedItem()==null
				|| email.getText().equals("") || applicationDate.getValue()==null
				|| (!status.getSelectionModel().getSelectedItem().toString().equals("pending") && closingDate.getValue()==null)){
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
		//check closing date only if status != pending
		if(!status.getSelectionModel().getSelectedItem().toString().equals("pending")){
			if(!Checks.isLessThenCurrentDate(closingDate.getValue().toString())){
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
		}
		Recruit recruit = new Recruit();
		recruit.setIdRecruit(idRecruit);
		recruit.setFirstName(firstName.getText());
		recruit.setLastName(lastName.getText());
		recruit.setJobName(job.getSelectionModel().getSelectedItem().toString());
		recruit.setStatus(status.getSelectionModel().getSelectedItem().toString());
		recruit.setEmail(email.getText());
		recruit.setApplicationDate(applicationDate.getValue().toString());
		if(closingDate.getValue() != null){
			recruit.setClosingDate(closingDate.getValue().toString());
		}

		if(RecruitManager.update(recruit)){
			System.out.println("Update Done !");
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.close();
		}
	}
	
	public void cancelBtn(ActionEvent event){
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.close();
	}
}
