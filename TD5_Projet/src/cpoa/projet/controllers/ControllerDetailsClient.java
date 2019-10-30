package cpoa.projet.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import cpoa.projet.pojo.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

	public class ControllerDetailsClient implements Initializable {
		@FXML private TextField idField;
		
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			
		}
	
		public void initData(Client client) {
			System.out.println(client.getNom());
			
		}
		
	}
