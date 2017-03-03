package com.grh;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.grh.utilities.DBUtil;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class SettingsController implements Initializable{
	private String username;
	@FXML private JFXTextField user;
	@FXML private JFXPasswordField oldPassword;
	@FXML private JFXPasswordField newPassword;
	@FXML private JFXPasswordField reNewPassword;
	
	@FXML
	public void buttonPressed(KeyEvent event) throws SQLException
	{
	    if(event.getCode().toString().equals("ENTER"))
	    {
	    	ActionEvent actionEvent = new ActionEvent(event.getSource(),event.getTarget());
	        okBtn(actionEvent);
	    }
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		user.setText(username);
		user.setEditable(false);
	}
	public void setUser(String name){
		this.username=name;
	}
	public void okBtn(ActionEvent e) throws SQLException{
		if(DBUtil.authentification(user.getText(), oldPassword.getText())){
			if(newPassword.getText().equals(reNewPassword.getText())){
				DBUtil.chanePassword(username, newPassword.getText());
				TrayNotification notif = new TrayNotification();
				notif.setTray("User Panel", username+"'password was successfully changed", NotificationType.INFORMATION);
				notif.setAnimationType(AnimationType.POPUP);
				notif.setRectangleFill(Paint.valueOf("#2A9A84"));
				notif.showAndDismiss(Duration.seconds(2));
				Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
				stage.close();
			}
			else{
				Alert dialog = new Alert(AlertType.WARNING);
				dialog.setTitle("Error");
				dialog.setHeaderText(null);
				dialog.setContentText("Passwords don't match");
				Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image("/assets/icon.png"));
				dialog.initOwner((Stage)((Node)e.getSource()).getScene().getWindow());
				dialog.showAndWait();
			}
				
		}
		else{
			Alert dialog = new Alert(AlertType.WARNING);
			dialog.setTitle("Error");
			dialog.setHeaderText(null);
			dialog.setContentText("Old Password is incorrect");
			Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/assets/icon.png"));
			dialog.initOwner((Stage)((Node)e.getSource()).getScene().getWindow());
			dialog.showAndWait();
		}
	}
	
	
}
