package cpoa.projet.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import cpoa.projet.Persistance;
import cpoa.projet.factory.DAOFactory;
import cpoa.projet.pojo.Client;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ClientController implements Initializable, ChangeListener<Client> {
	@FXML private Button backButton;
	@FXML private Button deleteButton;
	@FXML private Button addButton;
	@FXML private Button updateButton;

	@FXML private TextField searchBar;
	@FXML private Label searchErrorLabel;
	
	@FXML private TableView<Client> clTable;
	@FXML private TableColumn<Client, String> firstnameColumn = new TableColumn<>("Nom");
	@FXML private TableColumn<Client, String> surnameColumn = new TableColumn<>("Pr�nom");
	@FXML private TableColumn<Client, String> streetNbColumn = new TableColumn<>("Num�ro de rue");
	@FXML private TableColumn<Client, String> streetColumn = new TableColumn<>("Voie");
	@FXML private TableColumn<Client, String> zipColumn = new TableColumn<>("Code postal");
	@FXML private TableColumn<Client, String> cityColumn = new TableColumn<>("Ville");
	@FXML private TableColumn<Client, String> countryColumn = new TableColumn<>("Pays");
	
	@FXML private ChoiceBox<String> searchModCB;
	
	@FXML private Stage stage;
	
	FilteredList<Client> fListClient;

	private DAOFactory daos = DAOFactory.getDAOFactory(Persistance.ListeMemoire);
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<Client> oListClient = FXCollections.observableArrayList();
		try {
			oListClient = FXCollections.observableArrayList(daos.getClientDAO().getAll());
		} catch (Exception e) {
			System.out.println("Erreur de r�cup�ration des donn�es de client : " + e.getMessage());
			e.printStackTrace();
		}
		this.firstnameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("nom"));
		this.surnameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("prenom"));
		this.streetNbColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("no_rue"));
		this.streetColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("voie"));
		this.zipColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("code_postal"));
		this.cityColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("ville"));
		this.countryColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("pays"));
		
		this.clTable.getColumns().setAll(firstnameColumn, surnameColumn, streetNbColumn, streetColumn, zipColumn, cityColumn, countryColumn);
		
		this.fListClient = new FilteredList<>(oListClient);
		this.clTable.getItems().addAll(fListClient);
		
		this.clTable.getSelectionModel().selectedItemProperty().addListener(
			(observable, oldValue, newValue) -> {
				this.deleteButton.setDisable(newValue == null);
				this.updateButton.setDisable(newValue == null);
			}
		);
		
		this.searchModCB.getItems().addAll("Par nom", "Par nom/pr�nom");
		this.searchModCB.setValue("Par nom");
		
		searchModCB.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
        {//reset table and textfield when new choice is selected
            if (newVal != null)
            {
            	this.searchErrorLabel.setText("");
            	this.refreshTableView();
                searchBar.setText("");
            }
        });
	     
	}
	
	@FXML
	public void backButton() {
		this.stage.close();
	}
	
	@FXML
	public void searchBarKeyReleased() {
		System.out.println("test");
		
		if(searchModCB.getValue() == "Par nom") {
			this.searchErrorLabel.setText("");
			fListClient.predicateProperty();
			String search = searchBar.getText();
			
			if(search.isBlank()) {
				this.refreshTableView();
			}
			else {
				ObservableList<Client> oListClient = FXCollections.observableArrayList();
				try {
					oListClient = FXCollections.observableArrayList(daos.getClientDAO().getByNom(search));
				} catch (Exception e) {
					System.out.println("Erreur de r�cup�ration des donn�es de client : " + e.getMessage());
					e.printStackTrace();
				}
				
				clTable.getSelectionModel().clearSelection();
				clTable.getItems().clear();
				this.fListClient = new FilteredList<>(oListClient);
				this.clTable.getItems().addAll(fListClient);
			}
		}
		else if(searchModCB.getValue() == "Par nom/pr�nom") {
			this.searchErrorLabel.setText("Vous devez rechercher sous la forme \"John Doe\"");
			String search = searchBar.getText();
			String[] searchSplit = search.split(" ");
			if(searchSplit.length == 2) {
				if(search.isBlank()) {
					this.refreshTableView();
				}
				else {
					ObservableList<Client> oListClient = FXCollections.observableArrayList();
					try {
						oListClient = FXCollections.observableArrayList(daos.getClientDAO().getByNomPrenom(searchSplit[0], searchSplit[1]));
					} catch (Exception e) {
						System.out.println("Erreur de r�cup�ration des donn�es de client : " + e.getMessage());
						e.printStackTrace();
					}

					clTable.getSelectionModel().clearSelection();
					clTable.getItems().clear();
					this.fListClient = new FilteredList<>(oListClient);
					this.clTable.getItems().addAll(fListClient);
				}
			}
		}
		
	}
	
	@FXML
	public void addButton() {
		try {
			URL fxmlURL = getClass().getResource("/cpoa/projet/views/clientDetailsView.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			final AnchorPane node = (AnchorPane)fxmlLoader.load();
			Scene scene = new Scene(node);
			
			ControllerDetailsClient controller = fxmlLoader.getController();
			
			Stage stageDetailsClient = new Stage();
			stageDetailsClient.setScene(scene);
			stageDetailsClient.setTitle("Ajouter");
			
			controller.setStage(stageDetailsClient);
			
			stageDetailsClient.showAndWait();
			
			if(controller.isSucces()) {
				this.refreshTableView();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void deleteButton() {
		Client selectedClient = clTable.getSelectionModel().getSelectedItem();
		try {
			daos.getClientDAO().delete(selectedClient);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.refreshTableView();
	}
	
	@FXML
	public void updateButton() {
		Client selectedClient = clTable.getSelectionModel().getSelectedItem();
		if(selectedClient != null) {
			this.clientUpdate(selectedClient);
		}
	}
	
	public void clientUpdate(Client client) {
		try {
			URL fxmlURL = getClass().getResource("/cpoa/projet/views/clientDetailsView.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			final AnchorPane node = (AnchorPane)fxmlLoader.load();
			Scene scene = new Scene(node);
			
			ControllerDetailsClient controller = fxmlLoader.getController();
			controller.initData(client);
			
			Stage stageDetailsClient = new Stage();
			stageDetailsClient.setScene(scene);
			stageDetailsClient.setTitle("Ajouter");
			
			controller.setStage(stageDetailsClient);
			stageDetailsClient.showAndWait();
			
			if(controller.isSucces()) {
				this.refreshTableView();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void refreshTableView() {
		ObservableList<Client> oListClient = FXCollections.observableArrayList();
		try {
			oListClient = FXCollections.observableArrayList(daos.getClientDAO().getAll());
		} catch (Exception e) {
			System.out.println("Erreur de r�cup�ration des donn�es de client : " + e.getMessage());
			e.printStackTrace();
		}
		clTable.getSelectionModel().clearSelection();
		clTable.getItems().clear();
		this.fListClient = new FilteredList<>(oListClient);
		this.clTable.getItems().addAll(fListClient);
	}

	@Override
	public void changed(ObservableValue<? extends Client> arg0, Client arg1, Client arg2) {
		// TODO Auto-generated method stub

	}
}
