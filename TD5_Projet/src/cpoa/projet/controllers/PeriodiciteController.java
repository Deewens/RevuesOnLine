package cpoa.projet.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import cpoa.projet.factory.DAOFactory;
import cpoa.projet.pojo.Periodicite;
import cpoa.projet.pojo.Revue;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public final class PeriodiciteController implements Initializable {
	@FXML private Button backButton;
	@FXML private Button deleteButton;
	@FXML private Button addButton;
	@FXML private Button updateButton;

	@FXML private TableView<Periodicite> periodTable;
	@FXML private TableColumn<Periodicite, String> libCol = new TableColumn<>("Libellé");
	
	FilteredList<Periodicite> fListPeriodicite;
	
	@FXML private Stage stage;

	private DAOFactory dao;
	
	public void setDAOFactory(DAOFactory dao) {
		this.dao = dao;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.periodTable.setPlaceholder(new Label("Il n'existe aucune périodicité."));
		Platform.runLater(() -> {
			ObservableList<Periodicite> oListPeriodicite = FXCollections.observableArrayList();
			try {
				oListPeriodicite = FXCollections.observableArrayList(dao.getPeriodiciteDAO().getAll());
			} catch (Exception e) {
				System.out.println("Erreur de récupération des données de périodicité : " + e.getMessage());
				e.printStackTrace();
			}
			
			this.libCol.setCellValueFactory(new PropertyValueFactory<Periodicite, String>("libelle"));
			
			this.periodTable.getColumns().setAll(libCol);
			
			fListPeriodicite = new FilteredList<>(oListPeriodicite);
			this.periodTable.getItems().addAll(fListPeriodicite);
			
			this.periodTable.getSelectionModel().selectedItemProperty().addListener(
					(observable, oldValue, newValue) -> {
						this.deleteButton.setDisable(newValue == null);
						this.updateButton.setDisable(newValue == null);
					}
				);
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
			URL fxmlURL = getClass().getResource("/cpoa/projet/views/PeriodiciteDetailsView.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			final AnchorPane node = (AnchorPane)fxmlLoader.load();
			Scene scene = new Scene(node);
			
			PeriodiciteDetailsController controller = fxmlLoader.getController();
			controller.setDAOFactory(dao);
			
			Stage periodiciteDetailsStage = new Stage();
			periodiciteDetailsStage.setScene(scene);
			periodiciteDetailsStage.setTitle("Ajouter");
			
			controller.setStage(periodiciteDetailsStage);
			
			periodiciteDetailsStage.showAndWait();
			
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
		Periodicite selectedPeriod = periodTable.getSelectionModel().getSelectedItem();
		if(selectedPeriod != null) {
			this.periodUpdate(selectedPeriod);
		}
	}

	public void periodUpdate(Periodicite period) {
		try {
			URL fxmlURL = getClass().getResource("/cpoa/projet/views/PeriodiciteDetailsView.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			final AnchorPane node = (AnchorPane)fxmlLoader.load();
			Scene scene = new Scene(node);
			
			PeriodiciteDetailsController controller = fxmlLoader.getController();
			controller.setDAOFactory(dao);
			
			Stage periodDetailsStage = new Stage();
			periodDetailsStage.setScene(scene);
			periodDetailsStage.setTitle("Modifier des données");
			
			controller.setStage(periodDetailsStage);
			controller.initData(period);
			
			periodDetailsStage.showAndWait();
			
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
		Periodicite selectedPeriod = periodTable.getSelectionModel().getSelectedItem();
		try {
			ArrayList<Revue> revueList = dao.getRevueDAO().getByIdPeriodicite(selectedPeriod.getId());
			if(revueList.isEmpty()) {
				dao.getPeriodiciteDAO().delete(selectedPeriod);
				this.refreshTableView();
			}
			else {
				Alert alert=new Alert(Alert.AlertType.ERROR);
				alert.initOwner(this.stage);
				alert.setTitle("Erreur de suppresion");
				alert.setHeaderText("Vous tentez de supprimer une donnée utilisé ailleurs");
				alert.setContentText("Cette périodicité est lié à une revue, vous ne pouvez pas la supprimer");
				alert.showAndWait();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void refreshTableView() {
		ObservableList<Periodicite> oListPeriodicite = FXCollections.observableArrayList();
		try {
			oListPeriodicite = FXCollections.observableArrayList(dao.getPeriodiciteDAO().getAll());
		} catch (Exception e) {
			System.out.println("Erreur de récupération des données de périodicité : " + e.getMessage());
			e.printStackTrace();
		}
		periodTable.getSelectionModel().clearSelection();
		periodTable.getItems().clear();
		fListPeriodicite = new FilteredList<>(oListPeriodicite);
		this.periodTable.getItems().addAll(fListPeriodicite);
	}

}
