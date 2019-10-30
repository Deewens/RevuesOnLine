package cpoa.projet.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainPageController implements Initializable {
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void aboButton() {
		Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        
        URL fxmlURL = getClass().getResource("/cpoa/projet/views/aboView.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
		Node root = null;
		try {
			root = fxmlLoader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        Scene scene = new Scene((VBox)root);
        stage.setScene(scene);
        stage.show();
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
	        stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void periodButton() {
		
	}
	
	public void revButton() {
		
	}


}
