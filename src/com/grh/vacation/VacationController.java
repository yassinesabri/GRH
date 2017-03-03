package com.grh.vacation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.grh.DAO.VacationManager;
import com.grh.tables.Vacation;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class VacationController implements Initializable {
	@FXML private TableView<Vacation> vacationTable;
	@FXML private TableColumn<Vacation, Integer> idVacCol;
	@FXML private TableColumn<Vacation, String> firstNameCol;
	@FXML private TableColumn<Vacation, String> lastNameCol;
	@FXML private TableColumn<Vacation, String> statusCol;
	@FXML private TableColumn<Vacation, String> startDateCol;
	@FXML private TableColumn<Vacation, String> endDateCol;
	@FXML private TableColumn<Vacation, Long> remainingCol;

	private ObservableList<Vacation> vacationList;
	@FXML
	public void buttonPressed(KeyEvent event) throws Exception
	{
	    if(event.getCode().toString().equals("ENTER"))
	    {
	    	ActionEvent actionEvent = new ActionEvent(event.getSource(),event.getTarget());
	    	updateVacationBtn(actionEvent);
	    }
	    if(event.getCode().toString().equals("DELETE"))
	    {
	    	ActionEvent actionEvent = new ActionEvent(event.getSource(),event.getTarget());
	    	deleteVacationBtn(actionEvent);
	    }
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//promoTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		idVacCol.setCellValueFactory(new PropertyValueFactory<>("idVac"));
		firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
		startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
		endDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
		remainingCol.setCellValueFactory(new PropertyValueFactory<>("remaining"));
		try {
			vacationList = VacationManager.getAllRows();
			vacationTable.setItems(vacationList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void addVacationBtn(ActionEvent event) throws IOException{
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
                    	if(!vacationList.isEmpty()){
                    		vacationList=null;
                    		vacationList = FXCollections.observableArrayList();
                		}
                		try {
                			vacationList = VacationManager.getAllRows();
						} catch (SQLException e) {
							e.printStackTrace();
						}
                		if(vacationList != null)
                			vacationTable.setItems(vacationList);
                    }
                });
            }
        });
	}
	public void updateVacationBtn(ActionEvent event) throws IOException{
		Vacation vacation = vacationTable.getSelectionModel().getSelectedItem();
		if(vacation != null){
			if(vacation.getStatus().equals("pending")){
				Stage stage = new Stage();
				stage.initOwner(((Node)event.getSource()).getScene().getWindow());
				stage.initModality(Modality.WINDOW_MODAL);
				FXMLLoader loader = new FXMLLoader(getClass().getResource("updateVacation.fxml"));
				loader.setControllerFactory(new Callback<Class<?>, Object>() {
					
					@Override
					public Object call(Class<?> param) {
						UpdateVacationController controller = new UpdateVacationController();
							controller.setId(vacation.getIdVac());
				            return controller ;
					}
				});
				Parent root = loader.load();
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.getIcons().add(new Image("/assets/icon.png"));
				stage.setResizable(false);
				stage.setTitle("GRH - Termination : Update File");
				stage.show();
				//wait the updateEmployee stage closing event to refresh table
				stage.setOnHiding(new EventHandler<WindowEvent>() {

		            @Override
		            public void handle(WindowEvent event) {
		                Platform.runLater(new Runnable() {

		                	 @Override
		                     public void run() {
		                		 if(!vacationList.isEmpty()){
		                			 vacationList=null;
		                			 vacationList = FXCollections.observableArrayList();
		                 		}
		                 		try {
		                 			vacationList = VacationManager.getAllRows();
		     					} catch (SQLException e) {
		     						e.printStackTrace();
		     					}
		                 		if(vacationList != null)
		                 			vacationTable.setItems(vacationList);
		                     }
		                });
		            }
		        });
				
			}
			else{
				Alert dialog = new Alert(AlertType.WARNING);
				dialog.setTitle("Error");
				dialog.setHeaderText(null);
				dialog.setContentText("Only pending leaves can be modified");
				Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image("/assets/icon.png"));
				dialog.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
				dialog.showAndWait();
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
	public void deleteVacationBtn(ActionEvent event) throws Exception{
		Vacation vacation = vacationTable.getSelectionModel().getSelectedItem();
		if(vacation != null){
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to delete this File ?");
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/assets/icon.png"));
			alert.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				if(VacationManager.delete(vacation.getIdVac())){
					if(!vacationList.isEmpty()){
						vacationList=null;
						vacationList = FXCollections.observableArrayList();
            		}
            		try {
            			vacationList = VacationManager.getAllRows();
					} catch (SQLException e) {
						e.printStackTrace();
					}
            		if(vacationList != null)
            			vacationTable.setItems(vacationList);
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
