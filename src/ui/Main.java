package ui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

/**
 * Main Class for the MagicSquareGenerator app
 * @author Jhon Edward Mora - Universidad ICESI - A00355710
 * @version 1.1 - 2023
 */
public class Main extends Application {
	
	/**
	 * Inherited method from superclass Application
	 */
	public void start(Stage stage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("gui.fxml"));
		Scene scene = new Scene (root);
		stage.setTitle("Magic Square Generator");
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * Main method, starts the program.
	 * @param args arguments for program's initialization.
	 */
	public static void main (String[] args) {
		launch(args);
	}
}