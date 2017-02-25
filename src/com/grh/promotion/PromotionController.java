package com.grh.promotion;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.grh.DAO.PromotionManager;
import com.grh.employee.UpdateEmployeeController;
import com.grh.tables.*;

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

public class PromotionController implements Initializable{
	@FXML private TableView<Promotion> promoTable;
	@FXML private TableColumn<Promotion, Integer> idPromCol;
	@FXML private TableColumn<Promotion, String> firstNameCol;
	@FXML private TableColumn<Promotion, String> lastNameCol;
	@FXML private TableColumn<Promotion, String> statusCol;
	@FXML private TableColumn<Promotion, String> datePromCol;
	@FXML private TableColumn<Promotion, String> descriptionCol;

	private ObservableList<Promotion> promotionList;
	@FXML
	public void buttonPressed(KeyEvent event) throws Exception
	{
	    if(event.getCode().toString().equals("ENTER"))
	    {
	    	ActionEvent actionEvent = new ActionEvent(event.getSource(),event.getTarget());
	    	updatePromBtn(actionEvent);
	    }
	    if(event.getCode().toString().equals("DELETE"))
	    {
	    	ActionEvent actionEvent = new ActionEvent(event.getSource(),event.getTarget());
	    	deletePromBtn(actionEvent);
	    }
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//promoTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		idPromCol.setCellValueFactory(new PropertyValueFactory<>("idProm"));
		firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
		datePromCol.setCellValueFactory(new PropertyValueFactory<>("dateProm"));
		descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));		
		try {
			promotionList = PromotionManager.getAllRows();
			promoTable.setItems(promotionList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void addPromBtn(ActionEvent event) throws IOException{
		Stage stage = new Stage();
		stage.initOwner(((Node)event.getSource()).getScene().getWindow());
		stage.initModality(Modality.WINDOW_MODAL);
		Parent root = FXMLLoader.load(getClass().getResource("addPromo.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
		stage.setScene(scene);
		stage.getIcons().add(new Image("/assets/icon.png"));
		stage.setResizable(false);
		stage.setTitle("GRH - Promotion : Add File");
		stage.show();
		//wait the addEmployee stage closing event to refresh table
		stage.setOnHiding(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent event) {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                    	if(!promotionList.isEmpty()){
                    		promotionList=null;
                    		promotionList = FXCollections.observableArrayList();
                		}
                		try {
                			promotionList = PromotionManager.getAllRows();
						} catch (SQLException e) {
							e.printStackTrace();
						}
                		if(promotionList != null)
                			promoTable.setItems(promotionList);
                    }
                });
            }
        });
	}
	public void updatePromBtn(ActionEvent event) throws IOException{
		Promotion promotion = promoTable.getSelectionModel().getSelectedItem();
		if(promotion != null){
			Stage stage = new Stage();
			stage.initOwner(((Node)event.getSource()).getScene().getWindow());
			stage.initModality(Modality.WINDOW_MODAL);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("updatePromo.fxml"));
			loader.setControllerFactory(new Callback<Class<?>, Object>() {
				
				@Override
				public Object call(Class<?> param) {
					UpdatePromoController controller = new UpdatePromoController();
						controller.setId(promotion.getIdProm());
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
	                		 if(!promotionList.isEmpty()){
	                     		promotionList=null;
	                     		promotionList = FXCollections.observableArrayList();
	                 		}
	                 		try {
	                 			promotionList = PromotionManager.getAllRows();
	     					} catch (SQLException e) {
	     						e.printStackTrace();
	     					}
	                 		if(promotionList != null)
	                 			promoTable.setItems(promotionList);
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
	public void deletePromBtn(ActionEvent event) throws Exception{
		Promotion promotion = promoTable.getSelectionModel().getSelectedItem();
		if(promotion != null){
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to delete this File ?");
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/assets/icon.png"));
			alert.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				if(PromotionManager.delete(promotion.getIdProm())){
					if(!promotionList.isEmpty()){
                		promotionList=null;
                		promotionList = FXCollections.observableArrayList();
            		}
            		try {
            			promotionList = PromotionManager.getAllRows();
					} catch (SQLException e) {
						e.printStackTrace();
					}
            		if(promotionList != null)
            			promoTable.setItems(promotionList);
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
