package cpoa.projet.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import cpoa.projet.factory.DAOFactory;
import cpoa.projet.pojo.Abonnement;
import cpoa.projet.pojo.AbonnementBuffer;
import cpoa.projet.pojo.Client;
import cpoa.projet.pojo.Revue;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class AbonnementDetailsController implements Initializable {
	@FXML private ComboBox<Client> clientComboBox;
	@FXML private ComboBox<Revue> revueComboBox;
	@FXML private DatePicker dateDebutDP;
	@FXML private DatePicker dateFinDP;
	
	private int idClient;
	private int idRevue;
	
	@FXML private Button addButton;
	@FXML private Button updateButton;
	
	@FXML private Stage stage;
	
	private boolean isSucces;
	
	private DAOFactory dao;
	
	public void setDAOFactory(DAOFactory dao) {
		this.dao = dao;
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(() -> {
			try {
				this.clientComboBox.setItems(FXCollections.observableArrayList(dao.getClientDAO().getAll()));
				this.revueComboBox.setItems(FXCollections.observableArrayList(dao.getRevueDAO().getAll()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	public void initData(AbonnementBuffer abo) {
		idClient = abo.getAbo().getId_client();
		idRevue = abo.getAbo().getId_revue();
		
		this.addButton.setVisible(false);
		this.updateButton.setVisible(true);
	}
	
	public boolean isSucces() {
		return isSucces;
	}
	
	@FXML
	public void addButton() {
		if(fieldChecking()) {
			try {
				dao.getAbonnementDAO().create(this.getField());
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
		if(this.fieldChecking()) {
			try {
				dao.getAbonnementDAO().update(this.getField());
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
	
	public boolean fieldChecking() {
		boolean error = false;
		String errorMessage = "";
		
		if(this.clientComboBox.getSelectionModel().isEmpty()) {
			errorMessage = errorMessage + "Vous devez choisir un client.\n";
			error = true;
		}
			
		if(this.revueComboBox.getSelectionModel().isEmpty()) {
			errorMessage = errorMessage + "Vous devez choisir une revue.\n";
			error = true;
		}
		
		if(!this.revueComboBox.getSelectionModel().isEmpty() || !this.clientComboBox.getSelectionModel().isEmpty()) {
			int idClientAbo = this.clientComboBox.getValue().getId_client();
			int idRevueAbo = this.revueComboBox.getValue().getId_revue();
			
			try {
				Abonnement abo = dao.getAbonnementDAO().getByIds(idClientAbo, idRevueAbo);
				if(abo != null) {
					errorMessage = errorMessage + "Un abonnement existe déjà pour ce client et cette revue.";
					error = true;
				}
			} catch (Exception e) {
				System.out.println("ERREUR : " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		if(this.dateDebutDP.getValue() == null) {
			errorMessage = errorMessage + "Vous devez indiquer une date de début d'abonnement.\n";
			error = true;
		}
		
		if(this.dateFinDP.getValue() == null) {
			errorMessage = errorMessage + "Vous devez indiquer une date de fin d'abonnement.\n";
			error = true;
		}
		
		if(error) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.initOwner(this.stage);
			alert.setTitle("Erreur lors de la saisie");
			alert.setHeaderText("Vous n'avez pas remplit correctement tout les champs");
			alert.setContentText(errorMessage);
			alert.showAndWait();
			return false;
		}
		return true;
	}
	
	public Abonnement getField() {
		Client client = this.clientComboBox.getValue();
		Revue revue = this.revueComboBox.getValue();
		LocalDate dateDebut = this.dateDebutDP.getValue();
		LocalDate dateFin = this.dateFinDP.getValue();
		
		return new Abonnement(client.getId_client(), revue.getId_revue(), dateDebut, dateFin);
	}
}
