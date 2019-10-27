package cpoa.projet.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import cpoa.projet.Persistance;
import cpoa.projet.factory.DAOFactory;
import cpoa.projet.pojo.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ControllerClient implements Initializable {
	@FXML private Button backButton;
	
	@FXML private TableView<Client> clTable;
	@FXML private TableColumn<Client, Integer> idColumn;
	@FXML private TableColumn<Client, String> firstnameColumn;
	@FXML private TableColumn<Client, String> surnameColumn;
	@FXML private TableColumn<Client, String> streetNbColumn;
	@FXML private TableColumn<Client, String> streetColumn;
	@FXML private TableColumn<Client, String> zipColumn;
	@FXML private TableColumn<Client, String> cityColumn;
	@FXML private TableColumn<Client, String> countryColumn;
	
	@FXML private TextField idField;
	@FXML private TextField firstnameField;
	@FXML private TextField surnameField;
	@FXML private TextField streetNbField;
	@FXML private TextField streetField;
	@FXML private TextField zipField;
	@FXML private TextField cityField;
	@FXML private TextField countryField;
	@FXML private TextField searchField;
	
	DAOFactory dao;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		DAOFactory.getDAOFactory(Persistance.ListeMemoire);
		this.idColumn.setCellValueFactory(new PropertyValueFactory<Client, Integer>("id_client"));
		this.firstnameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("nom"));
		this.surnameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("prenom"));
		this.streetNbColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("no_rue"));
		this.streetColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("voie"));
		this.zipColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("code_postal"));
		this.cityColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("ville"));
		this.countryColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("pays"));

		try {
			this.clTable.getItems().addAll(dao.getClientDAO().getAll());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void backButton() {
	    // get a handle to the stage
	    Stage stage = (Stage) backButton.getScene().getWindow();
	    // do what you have to do
	    stage.close();
	} 
}
