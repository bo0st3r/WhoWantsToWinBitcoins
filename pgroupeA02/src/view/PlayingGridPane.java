package view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.text.html.HTMLDocument.HTMLReader.HiddenAction;

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
	private PyramidGridPane pyramidGridPane;
	private int pyramidActualStep;

	// Validation
	private ValidationGridPane validationGridPane;

	// Timer
	private TimerFlowPane timerFlowPane;

	public PlayingGridPane() {
		earning = new Earning();
		joker = new Joker();
		pyramidActualStep = Party.NB_STEPS - 1;
		this.setGridLinesVisible(true);

		// Set columns
		double[] sizesCol = { 3, 9, 22.5, 5, 1.5, 14.5, 17, 5, 3.5, 20, 3 };
		for (int i = 0; i <= sizesCol.length - 1; i++) {
			ColumnConstraints col = new ColumnConstraints();
			col.setHalignment(HPos.CENTER);
			col.setPercentWidth(sizesCol[i]);
			this.getColumnConstraints().add(col);
		}

		// Set rows
		double[] sizesRow = { 3, 3.5, 10, 10, 10, 14, 20, 1.5, 12, 1, 12, 3 };
		for (int i = 0; i <= sizesRow.length - 1; i++) {
			RowConstraints row = new RowConstraints();
			row.setValignment(VPos.CENTER);
			row.setPercentHeight(sizesRow[i]);
			this.getRowConstraints().add(row);
		}

		// Spacings
		this.setPadding(new Insets(10));
//		this.setHgap(5);
//		this.setVgap(5);

		// Question statement label
		this.add(getLblStatement(), 1, 6, 7, 1);
		// Question statement sizes
		getLblStatement().setPrefHeight(Integer.MAX_VALUE);
		getLblStatement().setPrefWidth(Integer.MAX_VALUE);

		// Answer buttons
		this.add(getBtnAnswer(0), 1, 8, 3, 1);
		this.add(getBtnAnswer(1), 1, 10, 3, 1);
		this.add(getBtnAnswer(2), 5, 8, 3, 1);
		this.add(getBtnAnswer(3), 5, 10, 3, 1);

		// Timer
		this.add(getTimerFlowPane(), 5, 3, 2, 2);
		getTimerFlowPane().setId("timer");
		getTimerFlowPane().setAlignment(Pos.CENTER);

		// Validation
		this.add(getValidationGridPane(), 1, 1, 5, 4);
		getValidationGridPane().setVisible(false);

		// Exit button
		this.add(getBtnCashIn(), 9, 0, 1, 2);

		// Joker Public
		this.add(getBtnJokerPublic(), 0, 2, 2, 1);
		// Joker Friend
		this.add(getBtnJokerFriend(), 0, 3, 2, 1);
		// Joker 5050
		this.add(getBtnJoker5050(), 0, 4, 2, 1);
		// Jokers labels
		this.add(getLblJokerResults(0), 3, 8);
		this.add(getLblJokerResults(1), 3, 10);
		this.add(getLblJokerResults(2), 7, 8);
		this.add(getLblJokerResults(3), 7, 10);
	}

	public void runNewParty(String dest) throws QuestionsListIsEmptyException, DeckUnderFilledException,
			NotEnoughQuestionsException, TooMuchQuestionsException, ExceedMaxStepsException {
		party = new Party(Serialization.jsonToDeck(dest));
		getNextQuestion();

		pyramidActualStep = Party.NB_STEPS - 1;
		pyramidGridPane = null;
		getPyramidGridPane().getLblGain(Party.NB_STEPS - 1).setId("pyramidActualStep");
	}

	public void verifyAnswer() throws ExceedMaxStepsException {
		// Still playing
		if (answerIndex == rightAnswerIndex && party.getActualStep() < Party.NB_STEPS) {
			// green color when OK
			getBtnAnswer(answerIndex).setId("answerBtnOk");
			// NEED PAUSE 1SEC BETWEEN 2 QUESTIONS

			getNextQuestion();
			// Reset the timer
			resetTimer();

			// Reset answers color
			getBtnAnswer(answerIndex).setId("");

			// Old earnings
			getPyramidGridPane().getLblGain(pyramidActualStep).setId("textEarningsPyramid");
			// Actual earnings
			getPyramidGridPane().getLblGain(pyramidActualStep - 1).setId("pyramidActualStep");
			pyramidActualStep--;

			// Party won
		} else if (party.getActualStep() == Party.NB_STEPS) {
			alertPop("Congrats, you won !");
			stopTimer();
			// Party lost
		} else {
			// Disable buttons except right answer button and button clicked by the user
			for (int i = 0; i <= Question.NB_ANSWERS - 1; i++) {
				if (i != rightAnswerIndex && i != answerIndex)
					disableBtnAnswer(false, i);
			}

			// Hide and stop the timer
			stopTimer();
			setVisibileTimerFlowPane(false);

			// Hide validation pane
			setVisibleValidationGridPane(false);

			// Button turn red if false
			getBtnAnswer(answerIndex).setId("answerBtnNotOk");

			// Loosing alert
			alertPop("Sorry, you're a looser !\n" + "The right answer was\n\n \"" + rightAnswer + "\"\n\n You won : "
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
				btnAnswer[i].setId("");
			}
			cancelJokerResults = false;
		}

	}

	public int getRightAnswerIndex() {
		return rightAnswerIndex;
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
			btnJokerPublic.getStyleClass().add("btnJoker");

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
			btnJokerPublic.getStyleClass().add("btnJoker");

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
			btnJokerPublic.getStyleClass().add("btnJoker");

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
			lblStatement.setId("questionStatement");
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
			btnAnswer[index].setId("");

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

	public void setVisibileTimerFlowPane(boolean value) {
		getTimerFlowPane().setVisible(value);
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

	public void setVisibleValidationGridPane(boolean value) {
		getValidationGridPane().setVisible(value);
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
	public PyramidGridPane getPyramidGridPane() {
		if (pyramidGridPane == null) {
			pyramidGridPane = new PyramidGridPane();
			pyramidGridPane.setId("earningsPyramid");
			pyramidGridPane.getStyleClass().add("pane");
			this.add(getPyramidGridPane(), 9, 2, 1, 9);
		}
		return pyramidGridPane;
	}

}
