package cpoa.projet.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import cpoa.projet.Persistance;
import cpoa.projet.factory.DAOFactory;
import cpoa.projet.pojo.Client;
import cpoa.projet.pojo.Revue;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RevuesController implements Initializable, ChangeListener<Revue> {
	private static final Pattern DOUBLE_PATTERN = Pattern.compile(
		    "[\\x00-\\x20]*[+-]?(NaN|Infinity|((((\\p{Digit}+)(\\.)?((\\p{Digit}+)?)" +
		    "([eE][+-]?(\\p{Digit}+))?)|(\\.((\\p{Digit}+))([eE][+-]?(\\p{Digit}+))?)|" +
		    "(((0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+)))" +
		    "[pP][+-]?(\\p{Digit}+)))[fFdD]?))[\\x00-\\x20]*");
	
	@FXML private Button backButton;
	@FXML private Button deleteButton;
	@FXML private Button addButton;
	@FXML private Button updateButton;

	@FXML private TextField searchBar;
	
	@FXML private TableView<Revue> revuesTable;
	@FXML private TableColumn<Revue, String> titleCol = new TableColumn<>("Titre");
	@FXML private TableColumn<Revue, String> descCol = new TableColumn<>("Description");
	@FXML private TableColumn<Revue, Double> priceCol = new TableColumn<>("Tarif numéro");
	@FXML private TableColumn<Revue, String> visualCol = new TableColumn<>("Visuel");
	@FXML private TableColumn<Revue, Integer> periodCol = new TableColumn<>("Périodicité");
	@FXML private TableColumn<Revue, String> aboCol = new TableColumn<>("Abonnements en cours");
	
	@FXML private ChoiceBox<String> searchModCB;
	
	FilteredList<Revue>	fListRevue;
	
	@FXML private Stage stage;

	private DAOFactory daos = DAOFactory.getDAOFactory(Persistance.ListeMemoire);
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<Revue> oListRevue = FXCollections.observableArrayList();
		try {
			oListRevue = FXCollections.observableArrayList(daos.getRevueDAO().getAll());
		} catch (Exception e) {
			System.out.println("Erreur de récupération des données de revue : " + e.getMessage());
			e.printStackTrace();
		}
		
		this.titleCol.setCellValueFactory(new PropertyValueFactory<Revue, String>("titre"));
		this.descCol.setCellValueFactory(new PropertyValueFactory<Revue, String>("description"));
		this.priceCol.setCellValueFactory(new PropertyValueFactory<Revue, Double>("tarif_numero"));
		this.visualCol.setCellValueFactory(new PropertyValueFactory<Revue, String>("visuel"));
		this.periodCol.setCellValueFactory(new PropertyValueFactory<Revue, Integer>("id_periodicite"));
		
		this.revuesTable.getColumns().setAll(titleCol, descCol, priceCol, visualCol, periodCol, aboCol);
		
		fListRevue = new FilteredList<>(oListRevue);
		this.revuesTable.getItems().addAll(fListRevue);
		
		this.revuesTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> {
					this.deleteButton.setDisable(newValue == null);
					this.updateButton.setDisable(newValue == null);
				}
			);
		
		this.searchModCB.getItems().addAll("Par titre", "Inférieur au tarif");
		this.searchModCB.setValue("Par titre");
		

		this.searchModCB.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
        {//reset table and textfield when new choice is selected
            if (newVal != null)
            {
            	this.refreshTableView();
                searchBar.setText("");
            }
        });
		
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	@FXML
	public void searchBarKeyReleased() {
		System.out.println("test");
		
		if(searchModCB.getValue() == "Par titre") {
			fListRevue.predicateProperty();
			String search = searchBar.getText();
			
			if(search.isBlank()) {
				this.refreshTableView();
			}
			else {
				ObservableList<Revue> oListRevue = FXCollections.observableArrayList();
				try {
					oListRevue = FXCollections.observableArrayList(daos.getRevueDAO().getByTitre(search));
				} catch (Exception e) {
					System.out.println("Erreur de récupération des données de revue : " + e.getMessage());
					e.printStackTrace();
				}
				
				this.revuesTable.getSelectionModel().clearSelection();
				this.revuesTable.getItems().clear();
				this.fListRevue = new FilteredList<>(oListRevue);
				this.revuesTable.getItems().addAll(fListRevue);
			}
		}
		else if(searchModCB.getValue() == "Inférieur au tarif") {
			fListRevue.predicateProperty();
			String search = searchBar.getText();
			
			if(search.isBlank()) {
				this.refreshTableView();
			}
			else {
				if(DOUBLE_PATTERN.matcher(search).matches()) {
					Double tarif = Double.parseDouble(search);
					
					ObservableList<Revue> oListRevue = FXCollections.observableArrayList();
					try {
						oListRevue = FXCollections.observableArrayList(daos.getRevueDAO().getLessThanTarif_numero(tarif));
					} catch (Exception e) {
						System.out.println("Erreur de récupération des données de revue : " + e.getMessage());
						e.printStackTrace();
					}
					
					this.revuesTable.getSelectionModel().clearSelection();
					this.revuesTable.getItems().clear();
					this.fListRevue = new FilteredList<>(oListRevue);
					this.revuesTable.getItems().addAll(fListRevue);
				}
			}
		}
		
	}
	
	@FXML
	public void backButton() {
		this.stage.close();
	}
	
	@FXML
	public void addButton() {
		try {
			URL fxmlURL = getClass().getResource("/cpoa/projet/views/revueDetailsView.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			final AnchorPane node = (AnchorPane)fxmlLoader.load();
			Scene scene = new Scene(node);
			
			RevueDetailsController controller = fxmlLoader.getController();
			
			Stage revueDetailsStage = new Stage();
			revueDetailsStage.setScene(scene);
			revueDetailsStage.setTitle("Ajouter");
			
			controller.setStage(revueDetailsStage);
			
			revueDetailsStage.showAndWait();
			
			if(controller.isSucces()) {
				this.refreshTableView();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void updateButton() {
		Revue selectedRevue = revuesTable.getSelectionModel().getSelectedItem();
		if(selectedRevue != null) {
			this.revueUpdate(selectedRevue);
		}
	}
	
	@FXML
	public void revueUpdate(Revue revue) {
		try {
			URL fxmlURL = getClass().getResource("/cpoa/projet/views/revueDetailsView.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			final AnchorPane node = (AnchorPane)fxmlLoader.load();
			Scene scene = new Scene(node);
			
			RevueDetailsController controller = fxmlLoader.getController();
			
			Stage revueDetailsStage = new Stage();
			revueDetailsStage.setScene(scene);
			revueDetailsStage.setTitle("Ajouter");
			
			controller.setStage(revueDetailsStage);
			controller.initData(revue);
			
			revueDetailsStage.showAndWait();
			
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
		Revue selectedRevue = revuesTable.getSelectionModel().getSelectedItem();
		try {
			daos.getRevueDAO().delete(selectedRevue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.refreshTableView();
	}
	
	public void refreshTableView() {
		ObservableList<Revue> oListRevue = FXCollections.observableArrayList();
		try {
			oListRevue = FXCollections.observableArrayList(daos.getRevueDAO().getAll());
		} catch (Exception e) {
			System.out.println("Erreur de récupération des données de revue : " + e.getMessage());
			e.printStackTrace();
		}
		revuesTable.getSelectionModel().clearSelection();
		revuesTable.getItems().clear();
		fListRevue = new FilteredList<>(oListRevue);
		this.revuesTable.getItems().addAll(fListRevue);
	}
	
	
	
	
	
	
	@Override
	public void changed(ObservableValue<? extends Revue> arg0, Revue arg1, Revue arg2) {
		// TODO Auto-generated method stub
		
	}
}
