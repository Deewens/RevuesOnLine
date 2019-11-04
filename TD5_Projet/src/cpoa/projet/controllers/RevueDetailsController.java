package cpoa.projet.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import cpoa.projet.Persistance;
import cpoa.projet.factory.DAOFactory;
import cpoa.projet.pojo.Periodicite;
import cpoa.projet.pojo.Revue;
import cpoa.projet.pojo.RevueBuffer;
import javafx.application.Platform;
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

public class RevueDetailsController implements Initializable {
	private static final Pattern DOUBLE_PATTERN = Pattern.compile(
		    "[\\x00-\\x20]*[+-]?(NaN|Infinity|((((\\p{Digit}+)(\\.)?((\\p{Digit}+)?)" +
		    "([eE][+-]?(\\p{Digit}+))?)|(\\.((\\p{Digit}+))([eE][+-]?(\\p{Digit}+))?)|" +
		    "(((0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+)))" +
		    "[pP][+-]?(\\p{Digit}+)))[fFdD]?))[\\x00-\\x20]*");
	
	@FXML private Label message;
	@FXML private ComboBox<Periodicite> periodComboBox;
	@FXML private TextField titleField;
	@FXML private TextArea descField;
	@FXML private TextField priceField;
	
	@FXML private Button addButton;
	@FXML private Button updateButton;
	
	@FXML private Stage stage;
	
	private int idRevue;
	private int idPeriod;
	
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
				this.periodComboBox.setItems(FXCollections.observableArrayList(dao.getPeriodiciteDAO().getAll()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public void initData(RevueBuffer revue) {
		idRevue = revue.getRevue().getId_revue();
		this.titleField.setText(revue.getRevue().getTitre());
		this.descField.setText(revue.getRevue().getDescription());
		this.priceField.setText(String.valueOf(revue.getRevue().getTarif_numero()));
		idPeriod = revue.getRevue().getId_periodicite();
		
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
				dao.getRevueDAO().create(this.getField());
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
				dao.getRevueDAO().update(this.getField());
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
		String title = this.titleField.getText();
		String desc = this.descField.getText();
		String priceString = this.priceField.getText();
		Double price = 0.0;
		Periodicite period = this.periodComboBox.getValue();
		DAOFactory dao = DAOFactory.getDAOFactory(Persistance.MySQL);
		boolean error = false;
		String errorMessage = "";
		
		if(title.isBlank()) {
			errorMessage = errorMessage + "Vous devez mettre un titre.\n";
			error = true;
		}
			
		if(desc.isBlank()) {
			errorMessage = errorMessage + "Vous devez remplir la description.\n";
			error = true;
		}
		
		if(priceString.isBlank()) {
			errorMessage = errorMessage + "Vous devez indiquer un prix.\n";
			error = true;
		}
		else {
			if(!DOUBLE_PATTERN.matcher(priceString).matches()) {
				errorMessage = errorMessage + "Le prix doit être un nombre à virgule.\n";
				error = true;
			}
		}
		
		if(this.periodComboBox.getSelectionModel().isEmpty()) {
			errorMessage = errorMessage + "Vous n'avez pas choisi de périodicité.\n";
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
	
	public Revue getField() {
		String title = this.titleField.getText();
		String desc = this.descField.getText();
		String priceString = this.priceField.getText();
		Double price = Double.parseDouble(priceString);
		Periodicite period = this.periodComboBox.getValue();
		
		return new Revue(idRevue, title, desc, price, title+".png", period.getId());
	}
}
