package com.grh.promotion;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.grh.DAO.EmployeeManager;
import com.grh.DAO.PromotionManager;
import com.grh.tables.Employee;
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
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class AddPromoController implements Initializable{
	@FXML private JFXTextField firstName;
	@FXML private JFXTextField lastName;
	@FXML private JFXComboBox<String> status;
	@FXML private JFXDatePicker promoDate;
	@FXML private JFXTextArea description;
	private ObservableList<String> statusList;
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
	public void setIdEmp(int idEmp){
		this.idEmp = idEmp;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		statusList = FXCollections.observableArrayList("pending");
		status.setItems(statusList);
		status.getSelectionModel().select("pending");
		Employee employee = EmployeeManager.getRow(idEmp);
		firstName.setText(employee.getFirstName());
		lastName.setText(employee.getLastName());
		
	}
	public void addBtn(ActionEvent event) throws SQLException{
		if(firstName.getText().equals("") || lastName.getText().equals("") || description.getText().equals("") 
				|| promoDate.getValue()==null){
			Alert dialog = new Alert(AlertType.WARNING);
			dialog.setTitle("Error");
			dialog.setHeaderText(null);
			dialog.setContentText("All Fields Are Required");
			dialog.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
			dialog.showAndWait();
			return;
		}
		if(Checks.isLessThanCurrentDate(promoDate.getValue().toString())){
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
		if(PromotionManager.checkRow(firstName.getText(), lastName.getText())){
			Promotion promotion = new Promotion();
			promotion.setIdEmp(EmployeeManager.getEmployeeId(firstName.getText(), lastName.getText()));
			promotion.setFirstName(firstName.getText());
			promotion.setLastName(lastName.getText());
			promotion.setStatus(status.getSelectionModel().getSelectedItem().toString());
			promotion.setDescription(description.getText());
			promotion.setDateProm(promoDate.getValue().toString());
			if(PromotionManager.insert(promotion)){
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
