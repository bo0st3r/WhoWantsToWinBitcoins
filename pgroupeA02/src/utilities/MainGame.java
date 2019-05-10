package utilities;

import exceptions.DuplicateUserEmailException;
import exceptions.DuplicateUserException;
import exceptions.DuplicateUserPseudoException;
import exceptions.InputSyntaxException;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import view.ProjSP;

public class MainGame extends Application {
	public static void main(String[] args) throws DuplicateUserPseudoException, DuplicateUserEmailException,
			DuplicateUserException, InputSyntaxException {
//		UserManagerSingleton.getInstance().clearUsers();
//		UserManagerSingleton.getInstance().addUser(new User("admin", "helha", "admin@helha.be", true));
//		UserManagerSingleton.getInstance().addUser(new User("user", "helha", "user@helha.be"));
		launch(args);
	}

	// GUI
	@Override

	public void start(Stage primaryStage) {
		try {
			// Loads fonts
			Font.loadFont(getClass().getResource("/fonts/IBMPlexSans-Regular.ttf").toExternalForm(), 10);
			Font.loadFont(getClass().getResource("/fonts/IBMPlexSans-Bold.ttf").toExternalForm(), 10);
			Font.loadFont(getClass().getResource("/fonts/IBMPlexSans-SemiBold.ttf").toExternalForm(), 10);

			ProjSP root = new ProjSP();
			root.setId("projStackPane");

			Scene scene = new Scene(root);
			scene.widthProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					if (newValue.doubleValue() <= 1680) {
						root.setStyle("-fx-font-size: 10.0pt;");
					} else {
						root.setStyle("-fx-font-size: 12.0pt;");
					}
				}
			});

			// CSS
			scene.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
			primaryStage.setTitle("Who wants to win Bitcoins?");
			primaryStage.setMaxWidth(1920);
			primaryStage.setMinWidth(1440);
			primaryStage.setMaximized(true);

			// Allows the stage to always keep the 16/9 aspect ratio
			primaryStage.minHeightProperty().bind(primaryStage.widthProperty().multiply(0.5625));
			primaryStage.maxHeightProperty().bind(primaryStage.widthProperty().multiply(0.5625));

			primaryStage.centerOnScreen();
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {

		}
	}

}
