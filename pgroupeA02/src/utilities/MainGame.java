package utilities;

import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import view.ProjStackPane;

public class MainGame extends Application {

	public static void main(String[] args) {
		// Run GUI
		launch(args);
	}

	// GUI
	@Override
	public void start(Stage primaryStage) {
		try {
			ProjStackPane root = new ProjStackPane();
			root.setId("projStackPane");
			Scene scene = new Scene(root);
			
//			Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
//			double width = resolution.getWidth();
//			double height = resolution.getHeight();
//			double w = width/1920;
//			double h = height/1080;
//			Scale scale = new Scale(w,h,0,0);
//			root.getTransforms().add(scale);

			// CSS
			scene.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
			
			primaryStage.setTitle("Qui veut gagner des Bitcoins");
			primaryStage.setMaximized(true);
			primaryStage.setMinHeight(750);
			primaryStage.setMinWidth(1350);
			primaryStage.setScene(scene);
			primaryStage.centerOnScreen();
			primaryStage.show();
		} catch (Exception e) {

		}
	}

//	public static void addChoiceToAQuestion(Question q, String answer, boolean value) {
//		try {
//			q.addChoice(answer, value);
//		} catch (TooMuchAnswersException | AnswerAlreadyPresentException | RightAnswerAlreadyPresentException
//				| NeedRightAnswerException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static void addQuestionToADeck(Deck d, Question q) {
//		try {
//			d.addQuestion(q);
//		} catch (QuestionAlreadyPresentException | NotAllAnswersException e) {
//			e.printStackTrace();
//		}
//	}
}
