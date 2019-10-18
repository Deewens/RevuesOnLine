package fr.iutmetz.td4.app;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import fr.iutmetz.td4.dao.Persistance;
import fr.iutmetz.td4.factory.DAOFactory;
import fr.iutmetz.td4.pojo.Periodicite;
import fr.iutmetz.td4.pojo.Revue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class Controller implements Initializable {
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
	
	public void initialize(URL location, ResourceBundle resources) {

        DAOFactory dao = DAOFactory.getDAOFactory(Persistance.MySQL);
        try {
			this.periodComboBox.setItems(FXCollections.observableArrayList(dao.getPeriodiciteDAO().getAll()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void create() {
		String title = this.titleField.getText();
		String desc = this.descField.getText();
		String priceString = this.priceField.getText();
		Double price = 0.0;
		Periodicite period = this.periodComboBox.getValue();
		DAOFactory dao = DAOFactory.getDAOFactory(Persistance.MySQL);
		boolean error = false;
		
		this.message.setText("");
		this.message.setTextFill(Color.web("#FF0000"));
		
		if(title.isBlank()) {
			this.message.setText(this.message.getText() + "Vous devez mettre un titre.\n");
			error = true;
		}
			
		if(desc.isBlank()) {
			this.message.setText(this.message.getText() + "Vous devez remplir la description.\n");
			error = true;
		}
		
		if(priceString.isBlank()) {
			this.message.setText(this.message.getText() + "Vous devez indiquer un prix.\n");
			error = true;
		}
		else {
			if(DOUBLE_PATTERN.matcher(priceString).matches()) {
				price = Double.parseDouble(priceString);
			}
			else {
				this.message.setText(this.message.getText() + "Le prix doit être un nombre à virgule.\n");
				error = true;
			}
		}
		
		if(this.periodComboBox.getSelectionModel().isEmpty()) {
			this.message.setText(this.message.getText() + "Vous n'avez pas choisi de périodicité.\n");
			error = true;
		}
		
		if(!error) {
			Revue revue = new Revue(title, desc, price, title + ".png", period.getId());
			try {
				dao.getRevueDAO().create(revue);
			} catch (Exception e) {
				this.message.setText(this.message.getText() + e + "\n");
				e.printStackTrace();
			}

			this.message.setTextFill(Color.BLACK);
			this.message.setText("La revue a été ajouté avec succès ! Récapitulatif :\n");
			this.message.setText(this.message.getText() + revue.toString());
		}
	}

}
