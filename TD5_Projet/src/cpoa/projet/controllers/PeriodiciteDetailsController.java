package cpoa.projet.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import cpoa.projet.Persistance;
import cpoa.projet.factory.DAOFactory;
import cpoa.projet.pojo.Periodicite;
import cpoa.projet.pojo.Revue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PeriodiciteDetailsController {
	@FXML private TextField libField;
	
	@FXML private Button addButton;
	@FXML private Button updateButton;
	
	@FXML private Stage stage;
	
	private int idPeriod;
	
	private boolean isSucces;
	
	private DAOFactory dao;
	
	public void setDAOFactory(DAOFactory dao) {
		this.dao = dao;
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void initData(Periodicite period) {
		idPeriod = period.getId();
		this.libField.setText(period.getLibelle());
		
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
				dao.getPeriodiciteDAO().create(this.getField());
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
				dao.getPeriodiciteDAO().update(this.getField());
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
		String libelle = this.libField.getText();

		boolean error = false;
		String errorMessage = "";
		
		if(libelle.isBlank()) {
			errorMessage = errorMessage + "Vous devez indiquez le nom de la périodicité.\n";
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
	
	public Periodicite getField() {
		String libelle = this.libField.getText();
		
		return new Periodicite(idPeriod, libelle);
	}
}
