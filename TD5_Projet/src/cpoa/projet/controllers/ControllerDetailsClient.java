package cpoa.projet.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import cpoa.projet.Persistance;
import cpoa.projet.factory.DAOFactory;
import cpoa.projet.pojo.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

	public class ControllerDetailsClient implements Initializable {
		@FXML private TextField firstnameField;
		@FXML private TextField surnameField;
		@FXML private TextField streetNbField;
		@FXML private TextField streetField;
		@FXML private TextField zipField;
		@FXML private TextField cityField;
		@FXML private TextField countryField;
		
		@FXML private Button addButton;
		@FXML private Button updateButton;
		
		@FXML private Stage stage;
		
		private int idClient;
		
		private boolean isSucces;
		
		DAOFactory daos = DAOFactory.getDAOFactory(Persistance.ListeMemoire);
		
		public void setStage(Stage stage) {
			this.stage = stage;
		}
		
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			
		}
	
		public void initData(Client client) {
			idClient = client.getId_client();
			this.firstnameField.setText(client.getNom());
			this.surnameField.setText(client.getPrenom());
			this.streetNbField.setText(client.getNo_rue());
			this.streetField.setText(client.getVoie());
			this.zipField.setText(client.getCode_postal());
			this.cityField.setText(client.getVille());
			this.countryField.setText(client.getPays());
			this.addButton.setVisible(false);
			this.updateButton.setVisible(true);
		}
		
		public boolean isSucces() {
			return isSucces;
		}
		
		@FXML
		public void addButton() {
			if(fieldCheckingFill()) {
				try {
					daos.getClientDAO().create(this.getField());
				} catch (Exception e) {
					e.printStackTrace();
				}
				this.isSucces = true;
				this.stage.close();
			}
			else {
				this.isSucces = false;
			}
		}
		
		@FXML
		public void updateButton() {
			if(this.fieldCheckingFill()) {
				try {
					daos.getClientDAO().update(this.getField());
				} catch (Exception e) {
					e.printStackTrace();
				}
				this.isSucces = true;
				this.stage.close();
			}
			else {
				this.isSucces = false;
			}
		}
		
		@FXML
		public void cancelButton() {
			this.stage.close();
		}
		
		public boolean fieldCheckingFill() {
			String firstname = firstnameField.getText();
			String surname = surnameField.getText();
			String streetNb = streetNbField.getText();
			String street = streetField.getText();
			String zip = zipField.getText();
			String city = cityField.getText();
			String country = countryField.getText();
			boolean error = false;
			String errorMessage = "";
			
			if(firstname.isBlank()) {
				error = true;
				errorMessage = errorMessage + "Le nom ne doit pas être vide.\n";
			}
			
			if(surname.isBlank()) {
				error = true;
				errorMessage = errorMessage + "Le prénom ne doit pas être vide.\n";
			}
			
			if(streetNb.isBlank()) {
				error = true;
				errorMessage = errorMessage + "Le numéro de rue ne doit pas être vide.\n";
			}
			
			if(street.isBlank()) {
				error = true;
				errorMessage = errorMessage + "La voie ne doit pas être vide.\n";
			}
			
			if(zip.isBlank()) {
				error = true;
				errorMessage = errorMessage + "Le code postal ne doit pas être vide.\n";
			}
			
			if(city.isBlank()) {
				error = true;
				errorMessage = errorMessage + "La ville ne doit pas être vide.\n";
			}
			
			if(country.isBlank()) {
				error = true;
				errorMessage = errorMessage + "Le pays ne doit pas être vide.\n";
			}
			
			if(error) {
				Alert alert=new Alert(Alert.AlertType.ERROR);
				alert.initOwner(this.stage);
				alert.setTitle("Erreur lors de la saisie");
				alert.setHeaderText("Tous les champs ne sont pas remplit");
				alert.setContentText(errorMessage);
				alert.showAndWait();
				return false;
			}
			
			return true;
		}
		
		public Client getField() {
			String firstname = firstnameField.getText();
			String surname = surnameField.getText();
			String streetNb = streetNbField.getText();
			String street = streetField.getText();
			String zip = zipField.getText();
			String city = cityField.getText();
			String country = countryField.getText();
			
			return new Client(idClient, firstname, surname, streetNb, street, zip, city, country);
		}
	}
