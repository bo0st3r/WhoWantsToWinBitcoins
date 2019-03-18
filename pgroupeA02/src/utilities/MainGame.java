package utilities;

import exceptions.AnswerAlreadyPresentException;
import exceptions.NeedRightAnswerException;
import exceptions.NotAllAnswersException;
import exceptions.QuestionAlreadyPresentException;
import exceptions.RightAnswerAlreadyPresentException;
import exceptions.TooMuchAnswersException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Deck;
import model.Question;
import view.ProjStackPane;

public class MainGame extends Application {
	public static void addChoiceToAQuestion(Question q, String answer, boolean value) {
		try {
			q.addChoice(answer, value);
		} catch (TooMuchAnswersException | AnswerAlreadyPresentException | RightAnswerAlreadyPresentException
				| NeedRightAnswerException e) {
			e.printStackTrace();
		}
	}

	public static void addQuestionToADeck(Deck d, Question q) {
		try {
			d.addQuestion(q);
		} catch (QuestionAlreadyPresentException | NotAllAnswersException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
//		Question q = null;
//		try {
//			q = new Question("Bastien", "BITCOINZ BITCOINZ BITCOINZ BITCOINZ", Round.FIRST_ROUND);
//		} catch (StatementTooShortException | NotARoundException e1) {
//			e1.printStackTrace();
//		}
//
//		System.out.println("Adding the first choice");
//		addChoiceToAQuestion(q, "A", false);
//		System.out.println("Nb answers : " + q.getNbAnswers());
//
//		System.out.println("\n-----------------------------------------------------");
//		System.out.println("2e answer with the same statement");
//		addChoiceToAQuestion(q, "A", false);
//		System.out.println("\nNb answers : " + q.getNbAnswers());
//		try {
//			System.out.println("Shouldn't return the right answer");
//			System.out.println(q.getRightAnswer());
//		} catch (NoRightAnswerException e) {
//			e.printStackTrace();
//		}
//
//		System.out.println("\n-----------------------------------------------------");
//		System.out.println("Adding the 2e choice");
//		addChoiceToAQuestion(q, "B", false);
//		System.out.println("Nb answers : " + q.getNbAnswers());
//
//		System.out.println("\n-----------------------------------------------------");
//		System.out.println("Adding the 3e choice");
//		addChoiceToAQuestion(q, "C", false);
//		System.out.println("Nb answers : " + q.getNbAnswers());
//
//		System.out.println("\n-----------------------------------------------------");
//		System.out.println("Fourth answer false and the 3 others are too");
//		addChoiceToAQuestion(q, "D", false);
//		System.out.println("Nb answers : " + q.getNbAnswers());
//
//		System.out.println("\n-----------------------------------------------------");
//		System.out.println("Adding the fourth answer (right one)");
//		System.out.println("Must return the right answer");
//		addChoiceToAQuestion(q, "D", true);
//		System.out.println("Nb answers : " + q.getNbAnswers());
//		try {
//			System.out.println(q.getRightAnswer());
//		} catch (NoRightAnswerException e) {
//			e.printStackTrace();
//		}
//
//		System.out.println("\n-----------------------------------------------------");
//		System.out.println("Fifth answer");
//		addChoiceToAQuestion(q, "E", false);
//		System.out.println("Nb answers : " + q.getNbAnswers());
//
//		System.out.println("\n-----------------------------------------------------");
//		System.out.println("Deleting an answer to try adding 2 true answers ");
//		q.removeChoice("A");
//		System.out.println("Fourth answer but also true (add a answer delete statement before it)");
//		addChoiceToAQuestion(q, "E", true);
//		System.out.println("Nb answers : " + q.getNbAnswers());
//
//		System.out.println("\n-----------------------------------------------------");
//		System.out.println("Re-adding the A choice");
//		addChoiceToAQuestion(q, "A", false);
//		System.out.println("Nb answers : " + q.getNbAnswers());
//
//		System.out.println("\n-----------------------------------------------------");
//		System.out.println("\tFinal result :");
//		System.out.println(q);
//
//		System.out.println("\n-----------------------------------------------------");
//		System.out.println("\tCreating a deck and add questions to it :");
//		Deck d = new Deck();
//
//		Question q2 = q.clone();
//		try {
//			q2.setStatement("ETHEREUMZ ETHEREUMZ ETHEREUMZ ETHEREUMZ");
//		} catch (StatementTooShortException e) {
//			e.printStackTrace();
//		}
//		addQuestionToADeck(d, q);
//		addQuestionToADeck(d, q2);
//
//		Question q3 = q2.clone();
//		addQuestionToADeck(d, q3);
//
//		System.out.println(d);

//		System.out.println("\n-----------------------------------------------------");
//		// Transform a Deck to a JSON file
//		Serialization.deckToJson(d, "test");
//
//		 Transform a JSON file to a Deck
//		Deck fromJson = Serialization.jsonToDeck("test");
//		System.out.println("\t\tRetrieved from JSON file :");
//		System.out.println(fromJson);
//		System.out.println(fromJson);

//		try {
//			Question q4 = new Question("", "TEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEST", Round.LAST_ROUND);
//			q4.addChoice("A", false);
//			q4.addChoice("A2", false);
//			q4.addChoice("A3", false);
//			q4.addChoice("A4", true);
//			addQuestionToADeck(d, q4);
//		} catch (StatementTooShortException | NotARoundException | TooMuchAnswersException | AnswerAlreadyPresentException | RightAnswerAlreadyPresentException | NeedRightAnswerException e1) {
//			e1.printStackTrace();
//		}
//		
//		try {
//			q3.setRound(Round.SECOND_ROUND);
//			q3.setStatement("TEST TEST TEST TEST TEST TEST TEST TEST ");
//		} catch (StatementTooShortException e) {
//			e.printStackTrace();
//		}
//		addQuestionToADeck(d, q3);
//		
//
//		System.out.println("\n----------------------------------------------------");
//		Party p = new Party(d.getQuestions());
//		System.out.println(p);
		// Run GUI
		launch(args);
	}

	// GUI
	@Override
	public void start(Stage primaryStage) {
		try {
//			AddQuestionGridPane lgp = AddQuestionGridPane.getSingleton();
//			lgp.setId("pane");

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

			ProjStackPane psp = new ProjStackPane();
			psp.setId("projStackPane");
			Scene scene = new Scene(psp);
			primaryStage.setTitle("Jeu");
//			primaryStage.setMaximized(true);
			primaryStage.setMinHeight(600);
			primaryStage.setMinWidth(1000);
//
			// test Home
//			HomeGridPane home = new HomeGridPane();
//			home.setId("homePane");
//			Scene scene = new Scene(home);
//			primaryStage.setTitle("Accueil");
//			primaryStage.setMaximized(true);
//			primaryStage.setMinHeight(600);
//			primaryStage.setMinWidth(1000);
			
			scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {

		}
	}
}
