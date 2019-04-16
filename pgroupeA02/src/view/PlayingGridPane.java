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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Modality;
import model.Earning;
import model.Party;
import model.Question;
import utilities.Serialization;

public class PlayingGridPane extends GridPane {
	private static Earning earning;
	private Party party;

	// Question
	private QuestionGridPane questionGP;

	// Cash in
	private Button btnCashIn;

	// Jokers
	private JokerVBox jokerVB;
	private Label[] lblJokerResults;

	// Earnings pyramid
	private PyramidGridPane pyramidGP;
	private int pyramidActualStep;

	// Validation
	private ValidationGridPane validationGP;

	// Timer
	private TimerFlowPane timerFP;

	public PlayingGridPane() {
		earning = new Earning();
		pyramidActualStep = Party.NB_STEPS - 1;
//		setGridLinesVisible(true);

		// Set columns
		double[] sizesCol = { 3, 9.5, 21.5, 5, 1.5, 15.5, 15.5, 5, 3.5, 20, 3 };
		for (int i = 0; i <= sizesCol.length - 1; i++) {
			ColumnConstraints col = new ColumnConstraints();
			col.setHalignment(HPos.CENTER);
			col.setPercentWidth(sizesCol[i]);
			getColumnConstraints().add(col);
		}

		// Set rows
		double[] sizesRow = { 3, 5.5, 10, 10, 10, 12, 21, 1.5, 12, 1, 12, 3 };
		for (int i = 0; i <= sizesRow.length - 1; i++) {
			RowConstraints row = new RowConstraints();
			row.setValignment(VPos.CENTER);
			row.setPercentHeight(sizesRow[i]);
			getRowConstraints().add(row);
		}

		// Spacings
		setPadding(new Insets(10));

		// Question
		add(getQuestionGP(), 1, 6, 7, 5);

		// Timer
		add(getTimerFP(), 5, 3, 2, 2);

		// Validation
		add(getValidationGP(), 3, 1, 5, 4);

		// Exit button
		add(getBtnCashIn(), 9, 1);

		// Joker Pane
		add(getJokerVB(), 0, 2, 2, 3);

		// Jokers labels
		add(getLblJokerResults(0), 3, 8);
		add(getLblJokerResults(1), 3, 10);
		add(getLblJokerResults(2), 7, 8);
		add(getLblJokerResults(3), 7, 10);

	}

	public void runNewParty(String dest) throws QuestionsListIsEmptyException, DeckUnderFilledException,
			NotEnoughQuestionsException, TooMuchQuestionsException, ExceedMaxStepsException {
		party = new Party(Serialization.jsonToDeck(dest));
		getNextQuestion();

		pyramidActualStep = Party.NB_STEPS - 1;
		pyramidGP = null;
		getPyramidGridPane().getLblGain(Party.NB_STEPS - 1).setId("pyramidActualStep");
	}

	public void verifyAnswer() throws ExceedMaxStepsException {
		// Still playing
		if (questionGP.getAnswerIndex() == party.getRightAnswerIndex() && party.getActualStep() < Party.NB_STEPS) {
			// green color when OK
			questionGP.getBtnAnswer(questionGP.getAnswerIndex()).setId("answerBtnOk");
			// NEED PAUSE 1SEC BETWEEN 2 QUESTIONS

			getNextQuestion();
			// Reset the timer
			resetTimer();

			// Reset answers color
			questionGP.getBtnAnswer(questionGP.getAnswerIndex()).setId("");

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
				if (i != party.getRightAnswerIndex() && i != questionGP.getAnswerIndex())
					questionGP.setDisableBtnAnswer(false, i);
			}

			// Hide and stop the timer
			stopTimer();
			setVisibileTimerFlowPane(false);

			// Hide validation pane
			setVisibleValidationGP(false);

			// Button turn red if false
			questionGP.getBtnAnswer(questionGP.getAnswerIndex()).setId("answerBtnNotOk");

			// Loosing alert
			alertPop("Sorry, you're a looser !\n" + "The right answer was\n\n \"" + party.getRightAnswer()
					+ "\"\n\n You won : " + getEarningsWhenLost() + " Bitcoins");
		}
	}

	public void getNextQuestion() throws ExceedMaxStepsException {
		// Gets the next question
		Question actualQuestion = party.getQuestionNextStep();

		// Sets the new statement
		questionGP.getLblStatement().setText(actualQuestion.getStatement());

		// Sets new answers
		List<Map.Entry<String, Boolean>> choices = new ArrayList<>(actualQuestion.getChoices().entrySet());
		Collections.shuffle(choices);

		int index = 0;
		for (Entry<String, Boolean> choice : choices) {
			String answer = choice.getKey();
			questionGP.getBtnAnswer(index).setText(answer);

			if (choice.getValue()) {
				party.setRightAnswer(answer);
				party.setRightAnswerIndex(index);
			}

			index++;
		}

		// Hide joker effects
		if (jokerVB.isCancelJokerResults()) {
			setVisibleLblJokerResults(false);
			questionGP.setDisableBtnAnswer(false);
			for (int i = 0; i <= Question.NB_ANSWERS - 1; i++) {
				questionGP.getBtnAnswer(i).setId("");
			}
			jokerVB.setCancelJokerResults(false);
		}

	}

	public Party getParty() {
		return party;
	}

	public static Earning getEarning() {
		return earning;
	}

	public JokerVBox getJokerVB() {
		if (jokerVB == null)
			jokerVB = new JokerVBox(this);

		return jokerVB;
	}

	public QuestionGridPane getQuestionGP() {
		if (questionGP == null)
			questionGP = new QuestionGridPane();

		return questionGP;
	}

	public Label getLblJokerResults(int index) {
		if (lblJokerResults == null)
			lblJokerResults = new Label[Question.NB_ANSWERS];

		if (lblJokerResults[index] == null) {
			lblJokerResults[index] = new Label("" + index);
			lblJokerResults[index].setId("jokerResults");
			lblJokerResults[index].setVisible(false);
			GridPane.setValignment(lblJokerResults[index], VPos.TOP);
		}
		return lblJokerResults[index];
	}

	public void lblJokerResultsSetText(int index, String text) {
		getLblJokerResults(index).setText(text);
	}

	public void setVisibleLblJokerResults(boolean value) {
		for (int i = 0; i <= Question.NB_ANSWERS - 1; i++) {
			lblJokerResults[i].setVisible(value);
		}
	}

	public void setVisibleLblJokerResults(boolean value, int index) {
		lblJokerResults[index].setVisible(value);
	}

	// Timer
	public TimerFlowPane getTimerFP() {
		if (timerFP == null) {
			timerFP = new TimerFlowPane();
			timerFP.setId("timer");
			timerFP.setAlignment(Pos.CENTER);
		}
		return timerFP;
	}

	public void resetTimer() {
		getTimerFP().resetNbSecond();
	}

	public void stopTimer() {
		getTimerFP().stopTimer();
	}

	public void setVisibileTimerFlowPane(boolean value) {
		getTimerFP().setVisible(value);
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

	public ValidationGridPane getValidationGP() {
		if (validationGP == null) {
			validationGP = new ValidationGridPane();
			validationGP.setVisible(false);
		}
		return validationGP;
	}

	public void setVisibleValidationGP(boolean value) {
		getBtnCashIn().setDisable(value);
		questionGP.setDisableBtnAnswer(value);

		getValidationGP().setVisible(value);
	}

	// Exit button
	public Button getBtnCashIn() {
		if (btnCashIn == null) {
			btnCashIn = new Button("Cash-in");
			btnCashIn.setMinSize(0, 0);
			btnCashIn.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
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
		if (pyramidGP == null) {
			pyramidGP = new PyramidGridPane();
			pyramidGP.setMinHeight(0);
			pyramidGP.setId("earningsPyramid");
			this.add(getPyramidGridPane(), 9, 2, 1, 9);
		}
		return pyramidGP;
	}

}
