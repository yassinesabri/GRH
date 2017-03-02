package com.grh.leave;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.grh.DAO.LeaveManager;
import com.grh.tables.Leave;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class LeaveController implements Initializable {
	@FXML private TableView<Leave> leaveTable;
	@FXML private TableColumn<Leave, Integer> idLeaveCol;
	@FXML private TableColumn<Leave, String> firstNameCol;
	@FXML private TableColumn<Leave, String> lastNameCol;
	@FXML private TableColumn<Leave, String> statusCol;
	@FXML private TableColumn<Leave, String> leaveDateCol;
	@FXML private TableColumn<Leave, String> descriptionCol;

	private ObservableList<Leave> leaveList;
	@FXML
	public void buttonPressed(KeyEvent event) throws Exception
	{
	    if(event.getCode().toString().equals("ENTER"))
	    {
	    	ActionEvent actionEvent = new ActionEvent(event.getSource(),event.getTarget());
	    	updateLeaveBtn(actionEvent);
	    }
	    if(event.getCode().toString().equals("DELETE"))
	    {
	    	ActionEvent actionEvent = new ActionEvent(event.getSource(),event.getTarget());
	    	deleteLeaveBtn(actionEvent);
	    }
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//promoTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		idLeaveCol.setCellValueFactory(new PropertyValueFactory<>("idLeave"));
		firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
		leaveDateCol.setCellValueFactory(new PropertyValueFactory<>("leaveDate"));
		descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));		
		try {
			leaveList = LeaveManager.getAllRows();
			leaveTable.setItems(leaveList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void addLeaveBtn(ActionEvent event) throws IOException{
		Stage stage = new Stage();
		stage.initOwner(((Node)event.getSource()).getScene().getWindow());
		stage.initModality(Modality.WINDOW_MODAL);
		Parent root = FXMLLoader.load(getClass().getResource("selectEmployee.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
		stage.setScene(scene);
		stage.getIcons().add(new Image("/assets/icon.png"));
		stage.setResizable(false);
		stage.setTitle("GRH - Termination : Select Employee");
		stage.show();
		//wait the addEmployee stage closing event to refresh table
		stage.setOnHiding(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent event) {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                    	if(!leaveList.isEmpty()){
                    		leaveList=null;
                    		leaveList = FXCollections.observableArrayList();
                		}
                		try {
                			leaveList = LeaveManager.getAllRows();
						} catch (SQLException e) {
							e.printStackTrace();
						}
                		if(leaveList != null)
                			leaveTable.setItems(leaveList);
                    }
                });
            }
        });
	}
	public void updateLeaveBtn(ActionEvent event) throws IOException{
		Leave leave = leaveTable.getSelectionModel().getSelectedItem();
		if(leave != null){
			Stage stage = new Stage();
			stage.initOwner(((Node)event.getSource()).getScene().getWindow());
			stage.initModality(Modality.WINDOW_MODAL);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("updateLeave.fxml"));
			loader.setControllerFactory(new Callback<Class<?>, Object>() {
				
				@Override
				public Object call(Class<?> param) {
					UpdateLeaveController controller = new UpdateLeaveController();
						controller.setId(leave.getIdLeave());
			            return controller ;
				}
			});
			Parent root = loader.load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.getIcons().add(new Image("/assets/icon.png"));
			stage.setResizable(false);
			stage.setTitle("GRH - Dismissal : Update File");
			stage.show();
			//wait the updateEmployee stage closing event to refresh table
			stage.setOnHiding(new EventHandler<WindowEvent>() {

	            @Override
	            public void handle(WindowEvent event) {
	                Platform.runLater(new Runnable() {

	                	 @Override
	                     public void run() {
	                		 if(!leaveList.isEmpty()){
	                			 leaveList=null;
	                			 leaveList = FXCollections.observableArrayList();
	                 		}
	                 		try {
	                 			leaveList = LeaveManager.getAllRows();
	     					} catch (SQLException e) {
	     						e.printStackTrace();
	     					}
	                 		if(leaveList != null)
	                 			leaveTable.setItems(leaveList);
	                     }
	                });
	            }
	        });
		}
		else{
			Alert dialog = new Alert(AlertType.WARNING);
			dialog.setTitle("Error");
			dialog.setHeaderText(null);
			dialog.setContentText("No row is selected");
			Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/assets/icon.png"));
			dialog.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
			dialog.showAndWait();
		}
	}
	public void deleteLeaveBtn(ActionEvent event) throws Exception{
		Leave leave = leaveTable.getSelectionModel().getSelectedItem();
		if(leave != null){
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to delete this File ?");
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/assets/icon.png"));
			alert.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				if(LeaveManager.delete(leave.getIdLeave())){
					if(!leaveList.isEmpty()){
						leaveList=null;
						leaveList = FXCollections.observableArrayList();
            		}
            		try {
            			leaveList = LeaveManager.getAllRows();
					} catch (SQLException e) {
						e.printStackTrace();
					}
            		if(leaveList != null)
            			leaveTable.setItems(leaveList);
//					TrayNotification notif = new TrayNotification();
//					notif.setTray("User Panel", "User was Deleted Successfully", NotificationType.INFORMATION);
//					notif.setAnimationType(AnimationType.POPUP);
//					notif.setRectangleFill(Paint.valueOf("#2A9A84"));
//					notif.showAndDismiss(Duration.seconds(2));
				}
			} else {
			    // ... user chose CANCEL or closed the dialog
			}
		}
		else{
			Alert dialog = new Alert(AlertType.WARNING);
			dialog.setTitle("Error");
			dialog.setHeaderText(null);
			dialog.setContentText("No row is selected");
			Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/assets/icon.png"));
			dialog.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
			dialog.showAndWait();
		}
	}

}
