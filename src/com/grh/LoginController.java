package com.grh;
import java.sql.SQLException;

import com.grh.utilities.DBUtil;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class LoginController {
	@FXML JFXTextField usernameField;
	@FXML JFXPasswordField passwordField;
	@FXML Label label;
	
	@FXML
	public void buttonPressed(KeyEvent event) throws SQLException
	{
	    if(event.getCode().toString().equals("ENTER"))
	    {
	    	ActionEvent actionEvent = new ActionEvent(event.getSource(),event.getTarget());
	        login(actionEvent);
	    }
	}
	
	public void login(ActionEvent event) throws SQLException{
		
		if(DBUtil.authentification(usernameField.getText(), passwordField.getText())){
			label.setText("Login Successful");
			try {
				//Notification
				TrayNotification notif = new TrayNotification();
				notif.setTray("Notification", "Login Successful", NotificationType.SUCCESS);
				notif.setAnimationType(AnimationType.POPUP);
				notif.setRectangleFill(Paint.valueOf("#2A9A84"));
				notif.showAndDismiss(Duration.seconds(2));
				//hide main stage
				((Node)event.getSource()).getScene().getWindow().hide();
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Parent root = loader.load(getClass().getResource("dashboard.fxml").openStream());
				//initialize the userController
				DashboardController dashboardcontroller = (DashboardController)loader.getController();
				dashboardcontroller.getUsername(usernameField.getText());
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				stage.getIcons().add(new Image("/assets/icon.png"));
				stage.setTitle("GRH - Dashboard : Employees");
				stage.setResizable(false);
				stage.sizeToScene();
				stage.setScene(scene);
				stage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			
		}
		else
			label.setText("Login Not Successful");		
	}
}
