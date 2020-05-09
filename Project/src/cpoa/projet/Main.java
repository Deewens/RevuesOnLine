package cpoa.projet;
	
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			URL fxmlURL = getClass().getResource("/cpoa/projet/views/RevuesOnLine_Accueil.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
			
			
			Scene scene = new Scene((VBox) root);
			
			// primaryStage.getIcons().add(new Image("/icon.png"));
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("RevuesOnLine");
			primaryStage.show();
			
		} catch(Exception e) {
			System.out.println("Erreur : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
