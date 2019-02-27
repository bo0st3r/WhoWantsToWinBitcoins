package utilities;

import enumerations.Round;
import exceptions.AnswerAlreadyPresentException;
import exceptions.NeedRightAnswerException;
import exceptions.NoRightAnswerException;
import exceptions.NotARoundException;
import exceptions.NotAllAnswersException;
import exceptions.QuestionAlreadyPresentException;
import exceptions.RightAnswerAlreadyPresentException;
import exceptions.StatementTooShortException;
import exceptions.TooMuchAnswersException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Deck;
import model.Question;
import view.AddQuestionGridPane;

public class MainJeu extends Application {
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
//
//		System.out.println("\n-----------------------------------------------------");
//		// Transform a Deck to a JSON file
//		Serialization.deckToJson(d, "test");
//
//		// Transform a JSON file to a Deck
//		Deck fromJson = Serialization.jsonToDeck("test");
//		System.out.println("\t\tRetrieved from JSON file :");
//		System.out.println(fromJson);

		// Run GUI
		launch(args);
	}

	// GUI
	@Override
	public void start(Stage primaryStage) {
		try {
			AddQuestionGridPane lgp = new AddQuestionGridPane();
			lgp.setId("pane");
			
			Scene scene = new Scene(lgp, 570, 350);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Add a question");

			scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {

		}
	}
}
