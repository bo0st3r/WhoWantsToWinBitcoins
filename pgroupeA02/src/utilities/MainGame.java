package utilities;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
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
			ProjStackPane psp = new ProjStackPane();
			psp.setId("projStackPane");
			Scene scene = new Scene(psp, 1350, 750);
			primaryStage.setTitle("Jeu");
//			primaryStage.setMaximized(true);
			primaryStage.setMinHeight(750);
			primaryStage.setMinWidth(1350);

			// CSS
			File f = new File("style/style.css");
			scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));

			primaryStage.setScene(scene);
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
