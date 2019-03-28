package view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

import exceptions.DeckUnderFilledException;
import exceptions.ExceedMaxStepsException;
import exceptions.NotEnoughQuestionsException;
import exceptions.QuestionsListIsEmptyException;
import exceptions.TooMuchQuestionsException;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Earning;
//import model.Joker;
//import model.JokerPublic;
import model.Party;
import model.Question;
import utilities.MainGame;
import utilities.Serialization;

public class PlayingGridPane extends GridPane {
	private static Earning earning;
	private Party party;

	private Label lblStatement;

	private Button btnAnswer[];
	private Button btnPrevious;
	private Button btnExitWithActualEarning;

	private int answerIndex;
	private String rightAnswer;

	// Jokers
	//private Joker joker;
	private Button btnJokerPublic;
	private Button btnJokerFriend;
	private Button btnJoker5050;

	// Earnings pyramid
	private PyramidVBox pyramidVbox;
	private Paint rgbGreen = Color.rgb(100, 255, 100);
	private Paint rgbActualStepColor = Color.rgb(255, 255, 100);
	private int pyramidActualStep;
	
	//Validation
	private ValidationGridPane validationGridPane;

	// Timer
	private TimerFlowPane timerFlowPane;

	public PlayingGridPane() {
		earning = new Earning();
		//joker = new Joker();
		pyramidActualStep = Party.NB_STEPS - 1;
		this.setGridLinesVisible(true);

		// Set columns
		ColumnConstraints c = new ColumnConstraints();
		c.setPercentWidth(10);
		this.getColumnConstraints().addAll(c, c, c, c, c, c, c, c, c, c, c);

		// Set rows
		RowConstraints r = new RowConstraints();
		r.setPercentHeight(10);
		this.getRowConstraints().addAll(r, r, r, r, r, r, r, r, r, r, r);

		// Spacings
		this.setPadding(new Insets(10));
		this.setHgap(5);
		this.setVgap(5);

		this.setGridLinesVisible(true);

		// Additions
		this.add(getBtnPrevious(), 0, 10);

		this.add(getLblStatement(), 1, 6, 8, 2);

		this.add(getBtnAnswer(0), 1, 8, 4, 1);
		this.add(getBtnAnswer(1), 1, 9, 4, 1);
		this.add(getBtnAnswer(2), 5, 8, 4, 1);
		this.add(getBtnAnswer(3), 5, 9, 4, 1);

		// Answers buttons ID
		for (int i = 0; i <= Question.NB_ANSWERS - 1; i++) {
			getBtnAnswer(i).setId("answers");
		}

		// Statement ID
		getLblStatement().setId("idQuestion");

		// Sizes
		getLblStatement().setPrefHeight(Integer.MAX_VALUE);
		getLblStatement().setPrefWidth(Integer.MAX_VALUE);

		for (int i = 0; i <= Question.NB_ANSWERS - 1; i++) {
			getBtnAnswer(i).setPrefWidth(Integer.MAX_VALUE);
			getBtnAnswer(i).setPrefHeight(Integer.MAX_VALUE);
		}

		// Joker
		this.add(getBtnJokerPublic(), 0, 1);

		// Timer
		this.add(getTimerFlowPane(), 4, 5, 2, 1);
		getTimerFlowPane().setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
		getTimerFlowPane().setAlignment(Pos.CENTER);
		
		//Validation
		this.add(getValidationGridPane(), 3, 1, 4, 4);
		getValidationGridPane().setVisible(false);
		
		//Exit button 
		this.add(getBtnExitWithActualEarning(), 7, 0, 2, 1);
		
	}

	public void runNewParty(String dest) throws QuestionsListIsEmptyException, DeckUnderFilledException,
			NotEnoughQuestionsException, TooMuchQuestionsException, ExceedMaxStepsException {
		party = new Party(Serialization.jsonToDeck(dest));
		getNextQuestion();

		pyramidActualStep = Party.NB_STEPS - 1;
		pyramidVbox = null;
		getPyramidVbox().getGain(Party.NB_STEPS - 1)
				.setBackground(new Background(new BackgroundFill(rgbActualStepColor, null, null)));
	}

	public void verifyAnswer() throws ExceedMaxStepsException {
		// Still playing
		if (getBtnAnswer(answerIndex).getText().equals(rightAnswer) && party.getActualStep() <= Party.NB_STEPS) {
			//green color when OK
			getBtnAnswer(answerIndex).setId("answerOk");
			//NEED PAUSE 1SEC BETWEEN 2 QUESTIONS
			
			getNextQuestion();
			// Reset the timer
			resetTimer();
			//reset color 
			getBtnAnswer(answerIndex).setId("answers");
			

			// pyramid METHODE A PART !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

			getPyramidVbox().getGain(pyramidActualStep)
					.setBackground(new Background(new BackgroundFill(rgbGreen, null, null)));
			getPyramidVbox().getGain(pyramidActualStep - 1)
					.setBackground(new Background(new BackgroundFill(rgbActualStepColor, null, null)));
			pyramidActualStep--;

			// Won the party
		} else if (party.getActualStep() > Party.NB_STEPS) {
			endParty();
			alertPop("CONGRATS");
			
			// Loosed
		} else {
			endParty();
			//button turn red if false
			getBtnAnswer(answerIndex).setId("answerNotOk");
			/* Mettre en vert la bonne réponse*/
			
			//alert with message
			alertPop("Sorry, you're a looser\n"
					+ "the right answer was\n\n "+rightAnswer
					+"\n\n you win : "+EarningWhenLost()+" Bitcoins");
		}
	}

	public Button getBtnJokerPublic() {
		if (btnJokerPublic == null) {
			btnJokerPublic = new Button("Call the public");

			btnJokerPublic.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					//joker.setStrategy(new JokerPublic());
					//joker.useJoker();
				}
			});
		}
		return btnJokerPublic;
	}

	public void getNextQuestion() throws ExceedMaxStepsException {
		// Gets the next question
		Question actualQuestion = party.getQuestionNextStep();
		// Sets new statement
		getLblStatement().setText(actualQuestion.getStatement());

		// Sets new answers
		List<Map.Entry<String, Boolean>> choices = new ArrayList<>(actualQuestion.getChoices().entrySet());
		Collections.shuffle(choices);

		int index = 0;
		for (Entry<String, Boolean> choice : choices) {
			String answer = choice.getKey();
			getBtnAnswer(index).setText(answer);

			if (choice.getValue())
				rightAnswer = answer;

			index++;
		}

	}

	public void endParty() {
		party = null;
	}

	public Label getLblStatement() {
		if (lblStatement == null) {
			lblStatement = new Label("");
		}
		return lblStatement;
	}

	public Button getBtnAnswer(int index) {
		if (btnAnswer == null)
			btnAnswer = new Button[Question.NB_ANSWERS];

		if (btnAnswer[index] == null) {
			btnAnswer[index] = new Button("");
			
				
			btnAnswer[index].setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					
					btnAnswer[index].setId("answerValue");
					answerIndex = index;					
					getValidationGridPane().setVisible(true);

				}
				
			});
						
		}
		return btnAnswer[index];
	}

	public Button getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new Button("Previous");
			btnPrevious.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					setVisible(false);
					((ProjStackPane) getParent().getParent()).getHomeGridPane().setVisible(true);

				}
			});
		}
		return btnPrevious;
	}

	public PyramidVBox getPyramidVbox() {
		if (pyramidVbox == null) {
			pyramidVbox = new PyramidVBox();
			this.add(getPyramidVbox(), 9, 1, 2, 9);
			
		}

		return pyramidVbox;
	}

	public static Earning getEarning() {
		return earning;
	}

	public void resetTimer() {
		getTimerFlowPane().resetNbSecond();
	}

	public TimerFlowPane getTimerFlowPane() {
		if (timerFlowPane == null) {
			timerFlowPane = new TimerFlowPane();
			
		}
		return timerFlowPane;
	}
	
	/**
	 * @param s is the text in the alert
	 * @return Alert 
	 */
	public Alert alertPop(String s) {
		
		Alert alert = new Alert(AlertType.NONE, s , ButtonType.OK);
		alert.initModality(Modality.WINDOW_MODAL);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.OK) {
			setVisible(false);
			((ProjStackPane) getParent().getParent()).getHomeGridPane().setVisible(true);
		}
		return alert;
	}
	public ValidationGridPane getValidationGridPane() {
		if (validationGridPane==null) {
			validationGridPane = new ValidationGridPane();
		}
		return validationGridPane;
	}
	//for the color in ValidationGridPane
	public int getAnswerIndex() {
		return answerIndex;
	}
	
	//get earning of the round 
	public double EarningWhenLost() {

		double earn = 0;
		if (pyramidActualStep > 9) {
			earn = 0;
		} else if (pyramidActualStep >= 5) {
			earn = PlayingGridPane.getEarning().getAmount(4);
		} else if (pyramidActualStep > 0) {
			earn = PlayingGridPane.getEarning().getAmount(9);
		}
		return earn;
	}

	//get earning with step
	public double earningWhenLeave() {

		double earn = 0;
		for (int i = 13; i >= 0; i--) {

			if (pyramidActualStep == i) {
				earn = PlayingGridPane.getEarning().getAmount(13 - i);
			}
		}
		return earn;
	}

	//exit button
	public Button getBtnExitWithActualEarning() {
		if (btnExitWithActualEarning == null) {
			btnExitWithActualEarning = new Button("Exit with actual earn");
			btnExitWithActualEarning.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					alertPop("You win: " + earningWhenLeave() + " Bitcoins");

				}
			});
		}
		return btnExitWithActualEarning;
	}

}
