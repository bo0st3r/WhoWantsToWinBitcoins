package utilities;

import exceptions.DuplicateUserEmailException;
import exceptions.DuplicateUserException;
import exceptions.DuplicateUserPseudoException;
import exceptions.InputSyntaxException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.ProjStackPane;

public class MainGame extends Application {
	public static void main(String[] args) throws DuplicateUserPseudoException, DuplicateUserEmailException,
			DuplicateUserException, InputSyntaxException {

		launch(args);
	}

	// GUI
	@Override

	public void start(Stage primaryStage) {
		try {
			ProjStackPane root = new ProjStackPane();
			root.setId("projStackPane");
			Scene scene = new Scene(root);

			// CSS
			scene.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
			primaryStage.setTitle("Who wants to win Bitcoins?");
			primaryStage.setMaximized(true);
			primaryStage.setMinHeight(750);
			primaryStage.setMinWidth(1350);
			primaryStage.setScene(scene);
			primaryStage.centerOnScreen();
			primaryStage.show();
		} catch (Exception e) {

		}
	}

}
