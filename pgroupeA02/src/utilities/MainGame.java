package utilities;

<<<<<<< HEAD
=======
import java.io.File;

import exceptions.AnswerAlreadyPresentException;
import exceptions.NeedRightAnswerException;
import exceptions.NotAllAnswersException;
import exceptions.QuestionAlreadyPresentException;
import exceptions.RightAnswerAlreadyPresentException;
import exceptions.TooMuchAnswersException;
>>>>>>> branch 'master' of https://booster99x@bitbucket.org/booster99x/groupea02.git
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

<<<<<<< HEAD
=======
//			Scene scene = new Scene(lgp, 570, 355);
////			primaryStage.setResizable(false);
//			primaryStage.setTitle("Add a question");

			// PlayingGridPane
//			BorderPane root = new BorderPane();
//			PlayingGridPane pgp = new PlayingGridPane();
//			
//			Scene scene = new Scene(root,400,400); // 400,400 --> Taille de la fen�tre
//			
//			scene = new Scene(pgp);

//			
//			primaryStage.setTitle("Who want to win Bitcoins");
//			primaryStage.setMaximized(true);
//TEST STACKPANE

//
			// test Home
//			HomeGridPane home = new HomeGridPane();
//			home.setId("homePane");
//			Scene scene = new Scene(home);
//			primaryStage.setTitle("Accueil");
//			primaryStage.setMaximized(true);
//			primaryStage.setMinHeight(600);
//			primaryStage.setMinWidth(1000);

>>>>>>> branch 'master' of https://booster99x@bitbucket.org/booster99x/groupea02.git
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
