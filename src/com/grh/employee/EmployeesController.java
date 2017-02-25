package com.grh.employee;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.grh.DashboardController;
import com.grh.DAO.EmployeeManager;
import com.grh.DAO.EmployeeRestoreManager;
import com.grh.tables.Employee;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;

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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class EmployeesController implements Initializable{
	@FXML private TableView<Employee> employeeTable;
	@FXML private TableColumn<Employee, Integer> idEmpCol;
	@FXML private TableColumn<Employee, String> firstNameCol;
	@FXML private TableColumn<Employee, String> lastNameCol;
	@FXML private TableColumn<Employee, String> jobNameCol;
	@FXML private TableColumn<Employee, String> departementNameCol;
	@FXML private TableColumn<Employee, Integer> salaryCol;
	@FXML private TableColumn<Employee, Integer> bonusCol;
	@FXML private TableColumn<Employee, String> emailCol;
	@FXML private TableColumn<Employee, String> phoneCol;
	@FXML private TableColumn<Employee, String> addressCol;
	@FXML private TableColumn<Employee, String> hiredDateCol;
	private ObservableList<Employee> employeeList;
	private ObservableList<String> searchItems;
	@FXML private JFXTextField searchField;
	@FXML private JFXComboBox<String> searchBy;
	@FXML private JFXButton addBtn;
	@FXML private JFXButton updateBtn;
	@FXML private JFXButton deleteBtn;
	@FXML private JFXButton searchBtn;
	@FXML private JFXButton restoreBtn; /*restore button to switch tables*/
	@FXML private JFXButton restore2Btn; /*the restore button from archive*/
	@FXML private JFXButton backBtn;
	@FXML private JFXButton trashBtn;
	@FXML private ImageView backImage;
	@FXML private ImageView trashImage;
	
	@FXML
	public void buttonPressed(KeyEvent event) throws Exception
	{
	    if(event.getCode().toString().equals("ENTER"))
	    {
	    	ActionEvent actionEvent = new ActionEvent(event.getSource(),event.getTarget());
	    	updateEmployeeBtn(actionEvent);
	    }
	    if(event.getCode().toString().equals("DELETE"))
	    {
	    	ActionEvent actionEvent = new ActionEvent(event.getSource(),event.getTarget());
	    	deleteEmployeeBtn(actionEvent);
	    }
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//employeeTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		idEmpCol.setCellValueFactory(new PropertyValueFactory<>("idEmp"));
		firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		jobNameCol.setCellValueFactory(new PropertyValueFactory<>("jobName"));
		departementNameCol.setCellValueFactory(new PropertyValueFactory<>("departementName"));
		salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
		bonusCol.setCellValueFactory(new PropertyValueFactory<>("bonus"));
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
		addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
		hiredDateCol.setCellValueFactory(new PropertyValueFactory<>("hiredDate"));
		
		try {
			employeeList = EmployeeManager.getAllRows();
			employeeTable.setItems(employeeList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		searchItems= FXCollections.observableArrayList("First Name","Last Name","Job","Departement");
		searchBy.setItems(searchItems);
		restore2Btn.setVisible(false);
		backBtn.setVisible(false);
		backImage.setVisible(false);
		trashBtn.setVisible(false);
		trashImage.setVisible(false);

	}
	public void addEmployeeBtn(ActionEvent event) throws IOException{
		
		Stage stage = new Stage();
		stage.initOwner(((Node)event.getSource()).getScene().getWindow());
		stage.initModality(Modality.WINDOW_MODAL);
		Parent root = FXMLLoader.load(getClass().getResource("addEmployee.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("GRH - Employees : Add Employee");
		stage.getIcons().add(new Image("/assets/icon.png"));
		stage.show();
		//wait the addEmployee stage closing event to refresh table
		stage.setOnHiding(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent event) {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                    	if(!employeeList.isEmpty()){
                    		employeeList=null;
                			employeeList = FXCollections.observableArrayList();
                		}
                		try {
                			employeeList = EmployeeManager.getAllRows();
						} catch (SQLException e) {
							e.printStackTrace();
						}
                		if(employeeList != null)
                			employeeTable.setItems(employeeList);
                    }
                });
            }
        });
	}
	
	public void deleteEmployeeBtn(ActionEvent event) throws SQLException,Exception{
		Employee employee = employeeTable.getSelectionModel().getSelectedItem();
		if(employee != null){
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to delete this employee ?");
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/assets/icon.png"));
			alert.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				if(EmployeeManager.delete(employee.getIdEmp())){
					EmployeeRestoreManager.insert(employee);
					if(!employeeList.isEmpty()){
						employeeList=null;
						employeeList = FXCollections.observableArrayList();
					}
					employeeList = EmployeeManager.getAllRows();
					if(employeeList != null)
						employeeTable.setItems(employeeList);
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
	
	public void updateEmployeeBtn(ActionEvent event) throws IOException{
		Employee employee = employeeTable.getSelectionModel().getSelectedItem();
		if(employee != null){
			Stage stage = new Stage();
			stage.initOwner(((Node)event.getSource()).getScene().getWindow());
			stage.initModality(Modality.WINDOW_MODAL);
			FXMLLoader loader = new FXMLLoader();
			Parent root = loader.load(getClass().getResource("updateEmployee.fxml").openStream());
			UpdateEmployeeController updateemployeecontroller = (UpdateEmployeeController)loader.getController();
			updateemployeecontroller.setId(employee.getIdEmp());
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
			stage.setScene(scene);
			stage.getIcons().add(new Image("/assets/icon.png"));
			stage.setResizable(false);
			stage.setTitle("GRH - Employees : Update Employee");
			stage.show();
			//wait the updateEmployee stage closing event to refresh table
			stage.setOnHiding(new EventHandler<WindowEvent>() {

	            @Override
	            public void handle(WindowEvent event) {
	                Platform.runLater(new Runnable() {

	                    @Override
	                    public void run() {
	                    	if(!employeeList.isEmpty()){
	                    		employeeList=null;
	                			employeeList = FXCollections.observableArrayList();
	                		}
	                		try {
	                			employeeList = EmployeeManager.getAllRows();
							} catch (SQLException e) {
								e.printStackTrace();
							}
	                		if(employeeList != null)
	                			employeeTable.setItems(employeeList);
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
	public void search(ActionEvent event){
		if(!searchField.getText().equals("") && searchBy.getSelectionModel().getSelectedItem() != null){
			String query = searchField.getText();
			String choice = searchBy.getSelectionModel().getSelectedItem();
			if(!employeeList.isEmpty()){
	    		employeeList=null;
				employeeList = FXCollections.observableArrayList();
			}
			try {
				switch(choice){
				case "First Name":{employeeList=EmployeeManager.searchByFirstName(query);break;}
				case "Last Name":{employeeList=EmployeeManager.searchByLastName(query);break;}
				case "Job":{employeeList=EmployeeManager.searchByJob(query);break;}
				case "Departement":{employeeList=EmployeeManager.searchByDepart(query);break;}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(employeeList != null)
				employeeTable.setItems(employeeList);
		}
	}
	public void restoreBtn(ActionEvent event){
		searchBtn.setVisible(false);
		searchField.setVisible(false);
		searchBy.setVisible(false);
		addBtn.setVisible(false);
		updateBtn.setVisible(false);
		deleteBtn.setVisible(false);
		restoreBtn.setVisible(false);
		restore2Btn.setVisible(true);
		backBtn.setVisible(true);
		backImage.setVisible(true);
		trashBtn.setVisible(true);
		trashImage.setVisible(true);
		try {
			employeeList = EmployeeRestoreManager.getAllRows();
			employeeTable.setItems(employeeList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void restore2Btn(ActionEvent event) throws SQLException, Exception{
		Employee employee = employeeTable.getSelectionModel().getSelectedItem();
		if(employee != null){
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to restore this employee ?");
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/assets/icon.png"));
			alert.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				if(EmployeeRestoreManager.restore(employee)){
					if(!employeeList.isEmpty()){
						employeeList=null;
						employeeList = FXCollections.observableArrayList();
					}
					employeeList = EmployeeRestoreManager.getAllRows();
					if(employeeList != null)
						employeeTable.setItems(employeeList);
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
	public void backBtn(ActionEvent event){
		searchBtn.setVisible(true);
		searchField.setVisible(true);
		searchBy.setVisible(true);
		addBtn.setVisible(true);
		updateBtn.setVisible(true);
		deleteBtn.setVisible(true);
		restoreBtn.setVisible(true);
		restore2Btn.setVisible(false);
		backBtn.setVisible(false);
		backImage.setVisible(false);
		trashBtn.setVisible(false);
		trashImage.setVisible(false);
		try {
			employeeList = EmployeeManager.getAllRows();
			employeeTable.setItems(employeeList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void trashBtn(ActionEvent event) throws Exception{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to delete ALL ?");
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/assets/icon.png"));
			alert.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
					EmployeeRestoreManager.deleteAll();
					if(!employeeList.isEmpty()){
						employeeList=null;
						employeeList = FXCollections.observableArrayList();
					}
					employeeList = EmployeeRestoreManager.getAllRows();
					if(employeeList != null)
						employeeTable.setItems(employeeList);
//					TrayNotification notif = new TrayNotification();
//					notif.setTray("User Panel", "User was Deleted Successfully", NotificationType.INFORMATION);
//					notif.setAnimationType(AnimationType.POPUP);
//					notif.setRectangleFill(Paint.valueOf("#2A9A84"));
//					notif.showAndDismiss(Duration.seconds(2));
			} else {
			    // ... user chose CANCEL or closed the dialog
			}
		}
}
