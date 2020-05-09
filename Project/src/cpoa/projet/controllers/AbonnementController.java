package cpoa.projet.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import cpoa.projet.factory.DAOFactory;
import cpoa.projet.pojo.Abonnement;
import cpoa.projet.pojo.AbonnementBuffer;
import cpoa.projet.pojo.Client;
import cpoa.projet.pojo.Revue;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AbonnementController implements Initializable {
	@FXML private Button backButton;
	@FXML private Button deleteButton;
	@FXML private Button addButton;
	@FXML private Button updateButton;
	
	@FXML private TableView<AbonnementBuffer> aboTable;
	@FXML private TableColumn<AbonnementBuffer, Client> clientCol = new TableColumn<>("Client");
	@FXML private TableColumn<AbonnementBuffer, Client> nomClientCol = new TableColumn<>("Nom");
	@FXML private TableColumn<AbonnementBuffer, Client> prenomClientCol = new TableColumn<>("Prénom");
	@FXML private TableColumn<AbonnementBuffer, Revue> revueCol = new TableColumn<>("Revue");
	@FXML private TableColumn<AbonnementBuffer, LocalDate> dateDebutCol = new TableColumn<>("Date début");
	@FXML private TableColumn<AbonnementBuffer, LocalDate> dateFinCol = new TableColumn<>("Date fin");
	
	@FXML private CheckBox filterCurrentAbo;
	
	FilteredList<AbonnementBuffer> fListAbonnement;
	
	@FXML private Stage stage;
	
	DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private DAOFactory dao;
	
	public void setDAOFactory(DAOFactory dao) {
		this.dao = dao;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.aboTable.setPlaceholder(new Label("Il n'existe aucun abonnement."));
	    Platform.runLater(() -> {
	    	ObservableList<AbonnementBuffer> oListAbonnement = FXCollections.observableArrayList();
			try {
				oListAbonnement = FXCollections.observableArrayList(dao.getAbonnementBufferDAO().getAboWithNameAndTitle());
			} catch (Exception e) {
				System.out.println("Erreur de récupération des données de revue : " + e.getMessage());
				e.printStackTrace();
			}
			
			this.nomClientCol.setCellValueFactory(new PropertyValueFactory<AbonnementBuffer, Client>("client"));
			this.nomClientCol.setCellFactory(col -> new TableCell<AbonnementBuffer, Client>() {
			    @Override
			    protected void updateItem(Client item, boolean empty) {

			        super.updateItem(item, empty);
			        if (empty)
			            setText(null);
			        else
			            setText(item.getNom());
			    }
			});
			this.prenomClientCol.setCellValueFactory(new PropertyValueFactory<AbonnementBuffer, Client>("client"));
			this.prenomClientCol.setCellFactory(col -> new TableCell<AbonnementBuffer, Client>() {
			    @Override
			    protected void updateItem(Client item, boolean empty) {

			        super.updateItem(item, empty);
			        if (empty)
			            setText(null);
			        else
			            setText(item.getPrenom());
			    }
			});
			this.revueCol.setCellValueFactory(new PropertyValueFactory<AbonnementBuffer, Revue>("revue"));
			this.revueCol.setCellFactory(col -> new TableCell<AbonnementBuffer, Revue>() {
			    @Override
			    protected void updateItem(Revue item, boolean empty) {

			        super.updateItem(item, empty);
			        if (empty)
			            setText(null);
			        else
			            setText(item.getTitre());
			    }
			});
			this.dateDebutCol.setCellValueFactory(new PropertyValueFactory<AbonnementBuffer, LocalDate>("dateDebut"));
			this.dateDebutCol.setCellFactory(col -> new TableCell<AbonnementBuffer, LocalDate>() {
			    @Override
			    protected void updateItem(LocalDate item, boolean empty) {

			        super.updateItem(item, empty);
			        if (empty)
			            setText(null);
			        else
			            setText(String.format(item.format(format)));
			    }
			});
			this.dateFinCol.setCellValueFactory(new PropertyValueFactory<AbonnementBuffer, LocalDate>("dateFin"));
			this.dateFinCol.setCellFactory(col -> new TableCell<AbonnementBuffer, LocalDate>() {
			    @Override
			    protected void updateItem(LocalDate item, boolean empty) {

			        super.updateItem(item, empty);
			        if (empty)
			            setText(null);
			        else
			            setText(String.format(item.format(format)));
			    }
			});
			
			this.clientCol.getColumns().setAll(nomClientCol, prenomClientCol);
			this.aboTable.getColumns().setAll(clientCol, revueCol, dateDebutCol, dateFinCol);
			
			fListAbonnement = new FilteredList<>(oListAbonnement);
			this.aboTable.getItems().addAll(fListAbonnement);
			
			this.aboTable.getSelectionModel().selectedItemProperty().addListener(
					(observable, oldValue, newValue) -> {
						this.deleteButton.setDisable(newValue == null);
						this.updateButton.setDisable(newValue == null);
					}
				);
			
			this.filterCurrentAbo.selectedProperty().addListener(
					(observable, oldValue, newValue) -> {
						if(newValue != null) {
							if(this.filterCurrentAbo.isSelected()) {
								List<AbonnementBuffer> listCurrentAbonnement = new ArrayList<>();
								try {
									listCurrentAbonnement = dao.getAbonnementBufferDAO().getAboWithNameAndTitle();
								} catch (Exception e) {
									System.out.println("Erreur de récupération des données de revue : " + e.getMessage());
									e.printStackTrace();
								}
								Iterator<AbonnementBuffer> dataIterator = listCurrentAbonnement.iterator();
								ObservableList<AbonnementBuffer> oListCurrentAbonnement = FXCollections.observableArrayList();
								
								while(dataIterator.hasNext()) {
									AbonnementBuffer datum = dataIterator.next();
									if(datum.getAbo().getDate_fin().isAfter(LocalDate.now())){
										oListCurrentAbonnement.add(datum);
									}
								}
								aboTable.getSelectionModel().clearSelection();
								aboTable.getItems().clear();
								fListAbonnement = new FilteredList<>(oListCurrentAbonnement);
								this.aboTable.getItems().addAll(fListAbonnement);
							}
							else {
								this.refreshTableView();
							}
						}
					});
	    });
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	@FXML
	public void backButton() {
		this.stage.close();
	}
	
	@FXML
	public void addButton() {
		try {
			URL fxmlURL = getClass().getResource("/cpoa/projet/views/aboDetailsView.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			final AnchorPane node = (AnchorPane)fxmlLoader.load();
			Scene scene = new Scene(node);
			
			AbonnementDetailsController controller = fxmlLoader.getController();
			controller.setDAOFactory(dao);
			
			Stage abonnementDetailsStage = new Stage();
			abonnementDetailsStage.setScene(scene);
			abonnementDetailsStage.setTitle("Ajouter");
			
			controller.setStage(abonnementDetailsStage);
			
			abonnementDetailsStage.showAndWait();
			
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
		AbonnementBuffer selectedAbonnement = aboTable.getSelectionModel().getSelectedItem();
		if(selectedAbonnement != null) {
			this.abonnementUpdate(selectedAbonnement);
		}
	}
	
	public void abonnementUpdate(AbonnementBuffer abo) {
		try {
			URL fxmlURL = getClass().getResource("/cpoa/projet/views/aboDetailsView.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			final AnchorPane node = (AnchorPane)fxmlLoader.load();
			Scene scene = new Scene(node);
			
			AbonnementDetailsController controller = fxmlLoader.getController();
			controller.setDAOFactory(dao);
			
			Stage abonnementDetailsStage = new Stage();
			abonnementDetailsStage.setScene(scene);
			abonnementDetailsStage.setTitle("Modifier");
			
			controller.setStage(abonnementDetailsStage);
			controller.initData(abo);
			
			abonnementDetailsStage.showAndWait();
			
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
		AbonnementBuffer selectedAbonnement = aboTable.getSelectionModel().getSelectedItem();
		Abonnement abo = new Abonnement(selectedAbonnement.getAbo().getId_client(), selectedAbonnement.getAbo().getId_revue(), selectedAbonnement.getAbo().getDate_debut(), selectedAbonnement.getAbo().getDate_fin());
		
		try {
			dao.getAbonnementDAO().delete(abo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.refreshTableView();
	}
	
	public void refreshTableView() {
		ObservableList<AbonnementBuffer> oListAbonnement = FXCollections.observableArrayList();
		try {
			oListAbonnement = FXCollections.observableArrayList(dao.getAbonnementBufferDAO().getAboWithNameAndTitle());
		} catch (Exception e) {
			System.out.println("Erreur de récupération des données de revue : " + e.getMessage());
			e.printStackTrace();
		}
		aboTable.getSelectionModel().clearSelection();
		aboTable.getItems().clear();
		fListAbonnement = new FilteredList<>(oListAbonnement);
		this.aboTable.getItems().addAll(fListAbonnement);
	}
}
