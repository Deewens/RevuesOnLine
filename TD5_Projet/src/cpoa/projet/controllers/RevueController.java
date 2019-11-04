package cpoa.projet.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import cpoa.projet.Persistance;
import cpoa.projet.factory.DAOFactory;
import cpoa.projet.pojo.Abonnement;
import cpoa.projet.pojo.Periodicite;
import cpoa.projet.pojo.Revue;
import cpoa.projet.pojo.RevueBuffer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class RevueController implements Initializable {
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
	
	@FXML private TableView<RevueBuffer> revuesTable;
	@FXML private TableColumn<RevueBuffer, Revue> titleCol = new TableColumn<>("Titre");
	@FXML private TableColumn<RevueBuffer, Revue> descCol = new TableColumn<>("Description");
	@FXML private TableColumn<RevueBuffer, Revue> priceCol = new TableColumn<>("Tarif numéro");
	@FXML private TableColumn<RevueBuffer, Revue> visualCol = new TableColumn<>("Visuel");
	@FXML private TableColumn<RevueBuffer, Periodicite> periodCol = new TableColumn<>("Périodicité");
	@FXML private TableColumn<RevueBuffer, Integer> aboCol = new TableColumn<>("Abonnements en cours");
	
	@FXML private ChoiceBox<String> searchModCB;
	
	FilteredList<RevueBuffer> fListRevue;
	
	@FXML private Stage stage;

	private DAOFactory dao;
	
	public void setDAOFactory(DAOFactory dao) {
		this.dao = dao;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.revuesTable.setPlaceholder(new Label("Il n'existe aucune revue."));
		Platform.runLater(() -> {
			ObservableList<RevueBuffer> oListRevue = FXCollections.observableArrayList();
			try {
				oListRevue = FXCollections.observableArrayList(dao.getRevueBufferDAO().getClientAndPeriodLibelleAndAboNb());
			} catch (Exception e) {
				System.out.println("Erreur de récupération des données de revue : " + e.getMessage());
				e.printStackTrace();
			}
			
			this.titleCol.setCellValueFactory(new PropertyValueFactory<RevueBuffer, Revue>("revue"));
			this.titleCol.setCellFactory(col -> new TableCell<RevueBuffer, Revue>() {
			    @Override
			    protected void updateItem(Revue item, boolean empty) {

			        super.updateItem(item, empty);
			        if (empty)
			            setText(null);
			        else
			            setText(item.getTitre());
			    }
			});
			this.descCol.setCellValueFactory(new PropertyValueFactory<RevueBuffer, Revue>("revue"));
			this.descCol.setCellFactory(col -> new TableCell<RevueBuffer, Revue>() {
			    @Override
			    protected void updateItem(Revue item, boolean empty) {

			        super.updateItem(item, empty);
			        if (empty)
			            setText(null);
			        else
			            setText(item.getDescription());
			    }
			});
			this.priceCol.setCellValueFactory(new PropertyValueFactory<RevueBuffer, Revue>("revue"));
			this.priceCol.setCellFactory(col -> new TableCell<RevueBuffer, Revue>() {
			    @Override
			    protected void updateItem(Revue item, boolean empty) {

			        super.updateItem(item, empty);
			        if (empty)
			            setText(null);
			        else
			            setText(String.valueOf(item.getTarif_numero()));
			    }
			});
			this.visualCol.setCellValueFactory(new PropertyValueFactory<RevueBuffer, Revue>("revue"));
			this.visualCol.setCellFactory(col -> new TableCell<RevueBuffer, Revue>() {
			    @Override
			    protected void updateItem(Revue item, boolean empty) {

			        super.updateItem(item, empty);
			        if (empty)
			            setText(null);
			        else
			            setText(item.getVisuel());
			    }
			});
			this.periodCol.setCellValueFactory(new PropertyValueFactory<RevueBuffer, Periodicite>("period"));
			this.periodCol.setCellFactory(col -> new TableCell<RevueBuffer, Periodicite>() {
			    @Override
			    protected void updateItem(Periodicite item, boolean empty) {

			        super.updateItem(item, empty);
			        if (empty)
			            setText(null);
			        else
			            setText(item.getLibelle());
			    }
			});
			this.aboCol.setCellValueFactory(new PropertyValueFactory<RevueBuffer, Integer>("nbCurrentAbo"));
			
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
		});
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	@FXML
	public void searchBarKeyReleased() {
		if(searchModCB.getValue() == "Par titre") {
			fListRevue.predicateProperty();
			String search = searchBar.getText();
			
			if(search.isBlank()) {
				this.refreshTableView();
			}
			else {
				ObservableList<RevueBuffer> oListRevue = FXCollections.observableArrayList();
				try {
					oListRevue = FXCollections.observableArrayList(dao.getRevueBufferDAO().getClientAndPeriodLibelleAndAboNbByTitre(search));
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
					
					ObservableList<RevueBuffer> oListRevue = FXCollections.observableArrayList();
					try {
						oListRevue = FXCollections.observableArrayList(dao.getRevueBufferDAO().getClientAndPeriodLibelleAndAboNbLessThanTarif_numero(tarif));
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
			controller.setDAOFactory(dao);
			
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
		RevueBuffer selectedRevue = revuesTable.getSelectionModel().getSelectedItem();
		if(selectedRevue != null) {
			this.revueUpdate(selectedRevue);
		}
	}
	
	@FXML
	public void revueUpdate(RevueBuffer revue) {
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
			controller.setDAOFactory(dao);
			
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
		RevueBuffer selectedRevue = revuesTable.getSelectionModel().getSelectedItem();	
		try {
			List<Abonnement> aboList = dao.getAbonnementDAO().getByIdRevue(selectedRevue.getRevue().getId_revue());
			if(aboList.isEmpty()) {
				Revue revue = new Revue(selectedRevue.getRevue().getId_revue(), selectedRevue.getRevue().getTitre(), selectedRevue.getRevue().getDescription(), selectedRevue.getRevue().getTarif_numero(), selectedRevue.getRevue().getVisuel(), selectedRevue.getRevue().getId_periodicite());
				dao.getRevueDAO().delete(revue);
				this.refreshTableView();
			}
			else {
				Alert alert=new Alert(Alert.AlertType.ERROR);
				alert.initOwner(this.stage);
				alert.setTitle("Erreur de suppresion");
				alert.setHeaderText("Vous tentez de supprimer une donnée utilisé ailleurs");
				alert.setContentText("Cette revue est lié à un abonnement, vous ne pouvez pas la supprimer");
				alert.showAndWait();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void refreshTableView() {
		ObservableList<RevueBuffer> oListRevue = FXCollections.observableArrayList();
		try {
			oListRevue = FXCollections.observableArrayList(dao.getRevueBufferDAO().getClientAndPeriodLibelleAndAboNb());
		} catch (Exception e) {
			System.out.println("Erreur de récupération des données de revue : " + e.getMessage());
			e.printStackTrace();
		}
		revuesTable.getSelectionModel().clearSelection();
		revuesTable.getItems().clear();
		fListRevue = new FilteredList<>(oListRevue);
		this.revuesTable.getItems().addAll(fListRevue);
	} 
}
