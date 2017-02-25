package com.grh;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class DashboardController implements Initializable{
	private String connectedUsername;
	@FXML private Label connectedUser;
	@FXML private SplitPane splitPane;
	@FXML private AnchorPane rightAnchor;
	@FXML private AnchorPane leftAnchor;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Node node=null;
		try {
			node = (Node)FXMLLoader.load(getClass().getResource("employee/employees.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rightAnchor.getChildren().setAll(node);
		/*fix divider position*/
		splitPane.setDividerPositions(0.135);
		SplitPane.Divider divider = splitPane.getDividers().get(0);
		divider.positionProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed( ObservableValue<? extends Number> observable, Number oldValue, Number newValue )
            {
                divider.setPosition(0.135);
            }
        });

		

	}
	public void getUsername(String username){
		connectedUsername = username;
		connectedUser.setText("Welcome, "+username);
	}
	public void signout(ActionEvent event){
		
		((Node)event.getSource()).getScene().getWindow().hide();		
		try {
			TrayNotification notif = new TrayNotification();
			notif.setTray("Notification", "Logout Successful", NotificationType.SUCCESS);
			notif.setAnimationType(AnimationType.POPUP);
			notif.setRectangleFill(Paint.valueOf("#2A9A84"));
			notif.showAndDismiss(Duration.seconds(2));
			Stage stage = new Stage();
			Parent root;
			root = FXMLLoader.load(getClass().getResource("login.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.getIcons().add(new Image("/assets/icon.png"));
			stage.setTitle("GRH - Login");
			stage.setResizable(false);
			stage.sizeToScene();
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void employeesBtn(ActionEvent event){
		Node node=null;
		try {
			node = (Node)FXMLLoader.load(getClass().getResource("employee/employees.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rightAnchor.getChildren().setAll(node);
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setTitle("GRH - Dashboard : Employees");
	}
	public void recruitmentBtn(ActionEvent event) throws IOException{
		Node node=null;
		try {
			node = (Node)FXMLLoader.load(getClass().getResource("recruit/recruitment.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rightAnchor.getChildren().setAll(node);
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setTitle("GRH - Dashboard : Recruitment");
	}
	public void leaveBtn(ActionEvent event){
		Node node=null;
		try {
			node = (Node)FXMLLoader.load(getClass().getResource("leave/leave.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rightAnchor.getChildren().setAll(node);
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setTitle("GRH - Dashboard : Leave");
	}
	public void promotionsBtn(ActionEvent event) throws IOException{
		Node node=null;
		try {
			node = (Node)FXMLLoader.load(getClass().getResource("promotion/promotion.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rightAnchor.getChildren().setAll(node);
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setTitle("GRH - Dashboard : Promotion");
	}
	public void settingsBtn(ActionEvent event) throws IOException{
		Stage stage = new Stage();
		stage.initOwner(((Node)event.getSource()).getScene().getWindow());
		stage.initModality(Modality.WINDOW_MODAL);
		/*Send Parameters between Stages*/
		FXMLLoader loader = new FXMLLoader(getClass().getResource("settings.fxml"));
		loader.setControllerFactory(new Callback<Class<?>, Object>() {
			
			@Override
			public Object call(Class<?> param) {
					SettingsController controller = new SettingsController();
					controller.setUser(connectedUsername);
		            return controller ;
			}
		});
		Parent root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("GRH - Settings");
		stage.getIcons().add(new Image("/assets/icon.png"));
		stage.show();
	}
}
