package md;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {



	public static Stage  primaryStage;

	/**
	 * A classe principal da aplicação em JavaFX
	 */
	/**
	 * Inicia o layout da aplicação
	 */
	@Override
	public void start(Stage primaryStage) {

		try {
			Parent root = FXMLLoader
					.load(getClass().getResource("Tela inicial.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("Programa para calcular raízes de equações polinomiais!");
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setResizable(false);
			Main.primaryStage = primaryStage;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
