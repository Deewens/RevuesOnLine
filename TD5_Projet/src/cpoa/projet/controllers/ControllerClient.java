package cpoa.projet.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import cpoa.projet.Persistance;
import cpoa.projet.factory.DAOFactory;
import cpoa.projet.pojo.Client;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControllerClient implements Initializable, ChangeListener<Client> {
	@FXML private Button backButton;
	@FXML private Button deleteButton;
	@FXML private Button addButton;

	@FXML private TableView<Client> clTable;
	@FXML private TableColumn<Client, Integer> idColumn = new TableColumn<>("ID Client");
	@FXML private TableColumn<Client, String> firstnameColumn = new TableColumn<>("Nom");
	@FXML private TableColumn<Client, String> surnameColumn = new TableColumn<>("Prénom");
	@FXML private TableColumn<Client, String> streetNbColumn = new TableColumn<>("Numéro de rue");
	@FXML private TableColumn<Client, String> streetColumn = new TableColumn<>("Voie");
	@FXML private TableColumn<Client, String> zipColumn = new TableColumn<>("Code postal");
	@FXML private TableColumn<Client, String> cityColumn = new TableColumn<>("Ville");
	@FXML private TableColumn<Client, String> countryColumn = new TableColumn<>("Pays");

	@FXML private TextField idField;
	@FXML private TextField firstnameField;
	@FXML private TextField surnameField;
	@FXML private TextField streetNbField;
	@FXML private TextField streetField;
	@FXML private TextField zipField;
	@FXML private TextField cityField;
	@FXML private TextField countryField;
	@FXML private TextField searchField;

	DAOFactory daos = DAOFactory.getDAOFactory(Persistance.ListeMemoire);

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.idColumn.setCellValueFactory(new PropertyValueFactory<Client, Integer>("id_client"));
		this.firstnameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("nom"));
		this.surnameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("prenom"));
		this.streetNbColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("no_rue"));
		this.streetColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("voie"));
		this.zipColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("code_postal"));
		this.cityColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("ville"));
		this.countryColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("pays"));

		this.clTable.getColumns().setAll(idColumn, firstnameColumn, surnameColumn, streetNbColumn, streetColumn, zipColumn, cityColumn, countryColumn);

		try {
			this.clTable.getItems().addAll(daos.getClientDAO().getAll());
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.clTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> {
					this.deleteButton.setDisable(newValue == null);
				}
				);
	}

	public void backButton() {
		Stage stage = (Stage) backButton.getScene().getWindow();
		stage.close();
	}

	public void updateButton() {
		try {
			URL fxmlURL = getClass().getResource("/cpoa/projet/views/clientDetailsView.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			final AnchorPane node = (AnchorPane)fxmlLoader.load();
			Scene scene = new Scene(node);
			
			Client client = new Client(1, "Dudon", "Adrien", "37", "rue des cyprets", "57565", "Congo", "Afrique");
			
			ControllerDetailsClient controller = fxmlLoader.getController();
			controller.initData(client);
			
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Ajouter");

			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void changed(ObservableValue<? extends Client> arg0, Client arg1, Client arg2) {
		// TODO Auto-generated method stub

	}
}
