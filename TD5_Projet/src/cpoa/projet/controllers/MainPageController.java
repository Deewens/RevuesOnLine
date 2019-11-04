package cpoa.projet.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import cpoa.projet.Persistance;
import cpoa.projet.factory.DAOFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainPageController implements Initializable {
	@FXML RadioMenuItem lmPersistanceMenu;
	@FXML RadioMenuItem mysqlPersistanceMenu;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.lmPersistanceMenu.setSelected(true);
	}
	
	public void aboButton() {
		try {
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        
	        URL fxmlURL = getClass().getResource("/cpoa/projet/views/aboView.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			final VBox node = (VBox)fxmlLoader.load();
	        Scene scene = new Scene(node);
	        stage.setScene(scene);
	        
			AbonnementController controller = fxmlLoader.getController();
			controller.setStage(stage);
			controller.setDAOFactory(getPersistanceDAO());
			
	        stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void clButton() {
		try {
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        
	        URL fxmlURL = getClass().getResource("/cpoa/projet/views/clientView.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			final VBox node = (VBox)fxmlLoader.load();
	        Scene scene = new Scene(node);
	        stage.setScene(scene);
	        
			ClientController controller = fxmlLoader.getController();
			controller.setStage(stage);
			controller.setDAOFactory(getPersistanceDAO());
			
	        stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void periodButton() {
		try {
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        
	        URL fxmlURL = getClass().getResource("/cpoa/projet/views/periodiciteView.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			final VBox node = (VBox)fxmlLoader.load();
	        Scene scene = new Scene(node);
	        stage.setScene(scene);
	        
			PeriodiciteController controller = fxmlLoader.getController();
			controller.setStage(stage);
			controller.setDAOFactory(getPersistanceDAO());
			
	        stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void revButton() {
		try {
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        
	        URL fxmlURL = getClass().getResource("/cpoa/projet/views/revuesView.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			final VBox node = (VBox)fxmlLoader.load();
	        Scene scene = new Scene(node);
	        stage.setScene(scene);
	        
			RevueController controller = fxmlLoader.getController();
			controller.setStage(stage);
			controller.setDAOFactory(getPersistanceDAO());
			
	        stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private DAOFactory getPersistanceDAO() {
		if(this.lmPersistanceMenu.isSelected()) {
			return DAOFactory.getDAOFactory(Persistance.ListeMemoire);
		}
		else if(this.mysqlPersistanceMenu.isSelected()) {
			return DAOFactory.getDAOFactory(Persistance.MySQL);
		}
		
		throw new NullPointerException("Persistance cannot be null");
	}

}
