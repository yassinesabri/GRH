package com.grh.recruit;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.grh.DAO.RecruitManager;
import com.grh.employee.UpdateEmployeeController;
import com.grh.tables.Recruit;

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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class RecruitmentController implements Initializable{
	private String connectedUsername;
	@FXML private Label connectedUser;
	@FXML private TableView<Recruit> recruitTable;
	@FXML private TableColumn<Recruit, Integer> idRecruitCol;
	@FXML private TableColumn<Recruit, String> firstNameCol;
	@FXML private TableColumn<Recruit, String> lastNameCol;
	@FXML private TableColumn<Recruit, String> jobNameCol;
	@FXML private TableColumn<Recruit, String> emailCol;
	@FXML private TableColumn<Recruit, String> applicationDateCol;
	@FXML private TableColumn<Recruit, String> statusCol;
	@FXML private TableColumn<Recruit, String> closingDateCol;
	private ObservableList<Recruit> recruitList;
	
	@FXML
	public void buttonPressed(KeyEvent event) throws Exception
	{
	    if(event.getCode().toString().equals("ENTER"))
	    {
	    	ActionEvent actionEvent = new ActionEvent(event.getSource(),event.getTarget());
	    	updateRecruitBtn(actionEvent);
	    }
	    if(event.getCode().toString().equals("DELETE"))
	    {
	    	ActionEvent actionEvent = new ActionEvent(event.getSource(),event.getTarget());
	    	deleteRecruitBtn(actionEvent);
	    }
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		idRecruitCol.setCellValueFactory(new PropertyValueFactory<>("idRecruit"));
		firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		jobNameCol.setCellValueFactory(new PropertyValueFactory<>("jobName"));
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		applicationDateCol.setCellValueFactory(new PropertyValueFactory<>("applicationDate"));
		statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
		closingDateCol.setCellValueFactory(new PropertyValueFactory<>("closingDate"));
		
		try {
			recruitList = RecruitManager.getAllRows();
			recruitTable.setItems(recruitList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void addRecruitBtn(ActionEvent event) throws IOException{
		Stage stage = new Stage();
		stage.initOwner(((Node)event.getSource()).getScene().getWindow());
		stage.initModality(Modality.WINDOW_MODAL);
		Parent root = FXMLLoader.load(getClass().getResource("addRecruit.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
		stage.setScene(scene);
		stage.getIcons().add(new Image("/assets/icon.png"));
		stage.setResizable(false);
		stage.setTitle("GRH - Recruitment : Add File");
		stage.show();
		//wait the addEmployee stage closing event to refresh table
		stage.setOnHiding(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent event) {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                    	if(!recruitList.isEmpty()){
                    		recruitList=null;
                    		recruitList = FXCollections.observableArrayList();
                		}
                		try {
                			recruitList = RecruitManager.getAllRows();
						} catch (SQLException e) {
							e.printStackTrace();
						}
                		if(recruitList != null)
                			recruitTable.setItems(recruitList);
                    }
                });
            }
        });
	}
	public void updateRecruitBtn(ActionEvent event) throws IOException{
		Recruit recruit = recruitTable.getSelectionModel().getSelectedItem();
		if(recruit != null){
			Stage stage = new Stage();
			stage.initOwner(((Node)event.getSource()).getScene().getWindow());
			stage.initModality(Modality.WINDOW_MODAL);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("updateRecruit.fxml"));
			loader.setControllerFactory(new Callback<Class<?>, Object>() {
				
				@Override
				public Object call(Class<?> param) {
					UpdateRecruitController controller = new UpdateRecruitController();
						controller.setId(recruit.getIdRecruit());
			            return controller ;
				}
			});
			Parent root = loader.load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.getIcons().add(new Image("/assets/icon.png"));
			stage.setResizable(false);
			stage.setTitle("GRH - Recruitment : Update File");
			stage.show();
			//wait the updateEmployee stage closing event to refresh table
			stage.setOnHiding(new EventHandler<WindowEvent>() {

	            @Override
	            public void handle(WindowEvent event) {
	                Platform.runLater(new Runnable() {

	                	 @Override
	                     public void run() {
	                     	if(!recruitList.isEmpty()){
	                     		recruitList=null;
	                     		recruitList = FXCollections.observableArrayList();
	                 		}
	                 		try {
	                 			recruitList = RecruitManager.getAllRows();
	 						} catch (SQLException e) {
	 							e.printStackTrace();
	 						}
	                 		if(recruitList != null)
	                 			recruitTable.setItems(recruitList);
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
	public void deleteRecruitBtn(ActionEvent event) throws SQLException, Exception{
		Recruit recruit = recruitTable.getSelectionModel().getSelectedItem();
		if(recruit != null){
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to delete this File ?");
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/assets/icon.png"));
			alert.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				if(RecruitManager.delete(recruit.getIdRecruit())){
					if(!recruitList.isEmpty()){
						recruitList=null;
						recruitList = FXCollections.observableArrayList();
					}
					recruitList = RecruitManager.getAllRows();
					if(recruitList != null)
						recruitTable.setItems(recruitList);
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