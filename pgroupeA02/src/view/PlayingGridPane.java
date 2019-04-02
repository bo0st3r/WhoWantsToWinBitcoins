package view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import exceptions.DeckUnderFilledException;
import exceptions.ExceedMaxStepsException;
import exceptions.NotEnoughQuestionsException;
import exceptions.QuestionsListIsEmptyException;
import exceptions.TooMuchQuestionsException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
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
import model.Earning;
import model.Joker;
import model.Joker5050;
import model.JokerFriend;
import model.JokerPublic;
import model.Party;
import model.Question;
import utilities.Serialization;

public class PlayingGridPane extends GridPane {
	private static Earning earning;
	private Party party;

	private Label lblStatement;

	private Button btnAnswer[];
	private int answerIndex;
	private String rightAnswer;
	private int rightAnswerIndex;

	private Button btnCashIn;

	// Jokers
	private Joker joker;
	private Button btnJokerPublic;
	private Label[] lblJokerResults;
	private Button btnJokerFriend;
	private Button btnJoker5050;
	private boolean cancelJokerResults;

	// Earnings pyramid
	private PyramidVBox pyramidVbox;
	private Paint rgbGreen = Color.rgb(100, 255, 100);
	private Paint rgbActualStepColor = Color.rgb(255, 255, 100);
	private int pyramidActualStep;

	// Validation
	private ValidationGridPane validationGridPane;

	// Timer
	private TimerFlowPane timerFlowPane;

	public PlayingGridPane() {
		earning = new Earning();
		joker = new Joker();
		pyramidActualStep = Party.NB_STEPS - 1;
//		this.setGridLinesVisible(true);

		// Set columns
		ColumnConstraints c = new ColumnConstraints();
		c.setPercentWidth(10);
		c.setHalignment(HPos.CENTER);
		this.getColumnConstraints().addAll(c, c, c, c, c, c, c, c, c, c, c);

		// Set rows
		RowConstraints r = new RowConstraints();
		r.setPercentHeight(10);
		r.setValignment(VPos.CENTER);
		this.getRowConstraints().addAll(r, r, r, r, r, r, r, r, r, r, r);

		// Spacings
		this.setPadding(new Insets(10));
		this.setHgap(5);
		this.setVgap(5);

		// Question statement label
		this.add(getLblStatement(), 1, 6, 8, 2);
		// Question statement ID
		getLblStatement().setId("pgp_statement");
		// Question statement sizes
		getLblStatement().setPrefHeight(Integer.MAX_VALUE);
		getLblStatement().setPrefWidth(Integer.MAX_VALUE);

		// Answer buttons
		this.add(getBtnAnswer(0), 1, 8, 4, 1);
		this.add(getBtnAnswer(1), 1, 9, 4, 1);
		this.add(getBtnAnswer(2), 5, 8, 4, 1);
		this.add(getBtnAnswer(3), 5, 9, 4, 1);

		// Timer
		this.add(getTimerFlowPane(), 4, 5, 2, 1);
		getTimerFlowPane().setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
		getTimerFlowPane().setAlignment(Pos.CENTER);

		// Validation
		this.add(getValidationGridPane(), 3, 1, 4, 4);
		getValidationGridPane().setVisible(false);

		// Exit button
		this.add(getBtnCashIn(), 7, 0, 2, 1);

		// Joker Public
		this.add(getBtnJokerPublic(), 0, 1);
		this.add(getLblJokerResults(0), 4, 8);
		this.add(getLblJokerResults(1), 4, 9);
		this.add(getLblJokerResults(2), 8, 8);
		this.add(getLblJokerResults(3), 8, 9);
		// Joker Friend
		this.add(getBtnJokerFriend(), 0, 2);
		// Joker 5050
		this.add(getBtnJoker5050(), 0, 3);
	}

	public void runNewParty(String dest) throws QuestionsListIsEmptyException, DeckUnderFilledException,
			NotEnoughQuestionsException, TooMuchQuestionsException, ExceedMaxStepsException {
		party = new Party(Serialization.jsonToDeck(dest));
		getNextQuestion();

		pyramidActualStep = Party.NB_STEPS - 1;
		pyramidVbox = null;
		getPyramidVbox().getLblGain(Party.NB_STEPS - 1)
				.setBackground(new Background(new BackgroundFill(rgbActualStepColor, null, null)));
	}

	public void verifyAnswer() throws ExceedMaxStepsException {
		// Still playing
		if (answerIndex == rightAnswerIndex && party.getActualStep() < Party.NB_STEPS) {
			// green color when OK
			getBtnAnswer(answerIndex).setId("answerOk");
			// NEED PAUSE 1SEC BETWEEN 2 QUESTIONS

			getNextQuestion();
			// Reset the timer
			resetTimer();
			// Reset answers color
			getBtnAnswer(answerIndex).setId("answers");

			// pyramid METHODE A PART !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			getPyramidVbox().getLblGain(pyramidActualStep)
					.setBackground(new Background(new BackgroundFill(rgbGreen, null, null)));
			getPyramidVbox().getLblGain(pyramidActualStep - 1)
					.setBackground(new Background(new BackgroundFill(rgbActualStepColor, null, null)));
			pyramidActualStep--;

			// Party won
		} else if (party.getActualStep() == Party.NB_STEPS) {
			endParty();
			alertPop("CONGRATS");

			// Party lost
		} else {
			endParty();
			// button turn red if false
			getBtnAnswer(answerIndex).setId("answerNotOk");
			/* Mettre en vert la bonne r�ponse */

			// alert with message
			alertPop("Sorry, you're a looser\n" + "the right answer was\n\n " + rightAnswer + "\n\n you win : "
					+ getEarningsWhenLost() + " Bitcoins");
		}
	}

	public void getNextQuestion() throws ExceedMaxStepsException {
		// Gets the next question
		Question actualQuestion = party.getQuestionNextStep();

		// Sets the new statement
		getLblStatement().setText(actualQuestion.getStatement());

		// Sets new answers
		List<Map.Entry<String, Boolean>> choices = new ArrayList<>(actualQuestion.getChoices().entrySet());
		Collections.shuffle(choices);

		int index = 0;
		for (Entry<String, Boolean> choice : choices) {
			String answer = choice.getKey();
			getBtnAnswer(index).setText(answer);

			if (choice.getValue()) {
				rightAnswer = answer;
				rightAnswerIndex = index;
			}

			index++;
		}

		// Hide joker effects
		if (cancelJokerResults) {
			setVisibleLblJokerResults(false);
			disableBtnAnswer(false);
			for (int i = 0; i <= Question.NB_ANSWERS - 1; i++) {
				btnAnswer[i].setId("answers");
			}
			cancelJokerResults = false;
		}

	}

	public int getRightAnswerIndex() {
		return rightAnswerIndex;
	}

	public void endParty() {
		party = null;
		getTimerFlowPane().stopTimer();
	}

	public Party getParty() {
		return party;
	}

	public static Earning getEarning() {
		return earning;
	}

	// Joker Public
	public Button getBtnJokerPublic() {
		if (btnJokerPublic == null) {
			btnJokerPublic = new Button("Ask the public");

			btnJokerPublic.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					cancelJokerResults = true;
					joker.setStrategy(new JokerPublic());
					joker.useJoker(((ProjStackPane) getParent().getParent()).getPlayingGridPane());
					setVisibleLblJokerResults(true);
					btnJokerPublic.setDisable(true);
				}
			});
		}
		return btnJokerPublic;
	}

	public Label getLblJokerResults(int index) {
		if (lblJokerResults == null)
			lblJokerResults = new Label[Question.NB_ANSWERS];

		if (lblJokerResults[index] == null) {
			lblJokerResults[index] = new Label("" + index);
			lblJokerResults[index].setPrefHeight(Integer.MAX_VALUE);
			lblJokerResults[index].setPrefWidth(Integer.MAX_VALUE);
			lblJokerResults[index].setId("jokerResults");
			lblJokerResults[index].setVisible(false);
			GridPane.setHalignment(lblJokerResults[index], HPos.CENTER);
		}
		return lblJokerResults[index];
	}

	public void lblJokerResultsSetText(int index, String text) {
		getLblJokerResults(index).setText(text);
	}

	// Joker Friend
	public Button getBtnJokerFriend() {
		if (btnJokerFriend == null) {
			btnJokerFriend = new Button("Call a friend");

			btnJokerFriend.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					cancelJokerResults = true;
					joker.setStrategy(new JokerFriend());
					joker.useJoker(((ProjStackPane) getParent().getParent()).getPlayingGridPane());
					btnJokerFriend.setDisable(true);
				}
			});
		}
		return btnJokerFriend;
	}

	// Joker 5050
	public Button getBtnJoker5050() {
		if (btnJoker5050 == null) {
			btnJoker5050 = new Button("50/50");

			btnJoker5050.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					cancelJokerResults = true;
					joker.setStrategy(new Joker5050());
					joker.useJoker(((ProjStackPane) getParent().getParent()).getPlayingGridPane());
					btnJoker5050.setDisable(true);
				}
			});
		}
		return btnJoker5050;
	}

	public void setVisibleLblJokerResults(boolean value) {
		for (int i = 0; i <= Question.NB_ANSWERS - 1; i++) {
			lblJokerResults[i].setVisible(value);
		}
	}

	public void setVisibleLblJokerResults(boolean value, int index) {
		lblJokerResults[index].setVisible(value);
	}

	// Question part
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
			btnAnswer[index].setPrefWidth(Integer.MAX_VALUE);
			btnAnswer[index].setPrefHeight(Integer.MAX_VALUE);

			btnAnswer[index].setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {

					answerIndex = index;
					getValidationGridPane().setVisible(true);
				}
			});
		}
		return btnAnswer[index];
	}

	public void disableBtnAnswer(boolean value, int index) {
		btnAnswer[index].setDisable(value);
	}

	public void disableBtnAnswer(boolean value) {
		for (int i = 0; i <= Question.NB_ANSWERS - 1; i++) {
			btnAnswer[i].setDisable(value);
		}
	}

	/*
	 * Return the actual answer's index.
	 */
	public int getAnswerIndex() {
		return answerIndex;
	}

	// Timer
	public TimerFlowPane getTimerFlowPane() {
		if (timerFlowPane == null) {
			timerFlowPane = new TimerFlowPane();

		}
		return timerFlowPane;
	}

	public void resetTimer() {
		getTimerFlowPane().resetNbSecond();
	}
	
	public void stopTimer() {
		getTimerFlowPane().stopTimer();
	}

	// Answer validation
	/**
	 * @param s is the text in the alert
	 * @return Alert
	 */
	public Alert alertPop(String s) {
		Alert alert = new Alert(AlertType.NONE, s, ButtonType.OK);
		alert.initModality(Modality.WINDOW_MODAL);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.OK) {
			setVisible(false);
			((ProjStackPane) getParent().getParent()).getHomeGridPane().setVisible(true);
		}
		return alert;
	}

	public ValidationGridPane getValidationGridPane() {
		if (validationGridPane == null) {
			validationGridPane = new ValidationGridPane();
		}
		return validationGridPane;
	}

	// Exit button
	public Button getBtnCashIn() {
		if (btnCashIn == null) {
			btnCashIn = new Button("Cash-in");
			btnCashIn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					alertPop("You won: " + getEarningsWhenLeaving() + " Bitcoins");
					stopTimer();
					endParty();
				}
			});
		}
		return btnCashIn;
	}

	/*
	 * Get the earnings when the player loses.
	 */
	public double getEarningsWhenLost() {
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

	/*
	 * Get the earnings when the player leaves.
	 */
	public double getEarningsWhenLeaving() {
		double earn = 0;
		for (int i = 13; i >= 0; i--) {
			if (pyramidActualStep == i) {
				earn = PlayingGridPane.getEarning().getAmount(13 - i);
			}
		}
		return earn;
	}

	// Pyramid
	public PyramidVBox getPyramidVbox() {
		if (pyramidVbox == null) {
			pyramidVbox = new PyramidVBox();
			pyramidVbox.getStyleClass().add("pane");
			this.add(getPyramidVbox(), 9, 1, 2, 9);
		}
		return pyramidVbox;
	}

}
