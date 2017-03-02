package com.grh.promotion;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.grh.DAO.PromotionManager;
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

public class UpdatePromoController implements Initializable{
	@FXML private JFXTextField firstName;
	@FXML private JFXTextField lastName;
	@FXML private JFXComboBox<String> status;
	@FXML private JFXDatePicker promoDate;
	@FXML private JFXTextArea description;
	private ObservableList<String> statusList;
	private int idProm;
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

			statusList = FXCollections.observableArrayList("pending","completed");
			status.setItems(statusList);
			Promotion promotion = PromotionManager.getRow(idProm);
			if(promotion == null)
				return;
			firstName.setText(promotion.getFirstName());
			lastName.setText(promotion.getLastName());
			status.getSelectionModel().select(promotion.getStatus());
			description.setText(promotion.getDescription());
			//string to date
			LocalDate date = LocalDate.parse(promotion.getDateProm());
			promoDate.setValue(date);
		
	}
	public void setId(int idProm) {
		this.idProm = idProm;
	}
	public void updateBtn(ActionEvent event) throws Exception{
		if(firstName.getText().equals("") || lastName.getText().equals("") || description.getText().equals("") 
				|| promoDate.getValue()==null){
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
		Promotion promotion = new Promotion();
		promotion.setIdProm(idProm);
		promotion.setStatus(status.getSelectionModel().getSelectedItem().toString());
		promotion.setDescription(description.getText());
		promotion.setDateProm(promoDate.getValue().toString());
		if(PromotionManager.checkRow(firstName.getText(), lastName.getText())){
			if(PromotionManager.update(promotion)){
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
