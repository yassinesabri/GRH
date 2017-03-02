package com.grh.promotion;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.grh.DAO.EmployeeManager;
import com.grh.tables.Employee;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class SelectEmployeeController implements Initializable {
	@FXML private TableView<Employee> employeeTable;
	@FXML private TableColumn<Employee, Integer> idEmpCol;
	@FXML private TableColumn<Employee, String> firstNameCol;
	@FXML private TableColumn<Employee, String> lastNameCol;
	@FXML private TableColumn<Employee, String> jobNameCol;
	@FXML private TableColumn<Employee, String> departementNameCol;
	@FXML private TableColumn<Employee, Integer> salaryCol;
	private ObservableList<Employee> employeeList;
	private ObservableList<String> searchItems;
	@FXML private JFXTextField searchField;
	@FXML private JFXComboBox<String> searchBy;
	// Event Listener on Pane.onKeyPressed
	@FXML
	public void buttonPressed(KeyEvent event) throws SQLException, IOException
	{
	    if(event.getCode().toString().equals("ENTER"))
	    {
	    	ActionEvent actionEvent = new ActionEvent(event.getSource(),event.getTarget());
	    	selectBtn(actionEvent);
	    }
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		idEmpCol.setCellValueFactory(new PropertyValueFactory<>("idEmp"));
		firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		jobNameCol.setCellValueFactory(new PropertyValueFactory<>("jobName"));
		departementNameCol.setCellValueFactory(new PropertyValueFactory<>("departementName"));
		salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
		
		try {
			employeeList = EmployeeManager.getAllRows();
			employeeTable.setItems(employeeList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		searchItems= FXCollections.observableArrayList("First Name","Last Name","Job","Departement");
		searchBy.setItems(searchItems);
		
	}
	public void selectBtn(ActionEvent event) throws IOException{
		Employee employee = employeeTable.getSelectionModel().getSelectedItem();
		Stage stage = new Stage();
		stage.initOwner(((Node)event.getSource()).getScene().getWindow());
		stage.initModality(Modality.WINDOW_MODAL);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("addPromo.fxml"));
		loader.setControllerFactory(new Callback<Class<?>, Object>() {
			
			@Override
			public Object call(Class<?> param) {
				AddPromoController controller = new AddPromoController();
					controller.setIdEmp(employee.getIdEmp());
		            return controller ;
			}
		});
		Parent root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
		stage.setScene(scene);
		stage.getIcons().add(new Image("/assets/icon.png"));
		stage.setResizable(false);
		stage.setTitle("GRH - Promotion : Add File");
		stage.show();
		Stage stage2 = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage2.setOpacity(0);
		//wait the addEmployee stage closing event to refresh table
		stage.setOnHiding(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent e) {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                    	//Stage stage2 = (Stage)((Node)event.getSource()).getScene().getWindow();
                    	stage2.close();
               
                    }
                });
            }
        });
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
	
}
