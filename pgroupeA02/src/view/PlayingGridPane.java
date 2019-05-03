package view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import exceptions.DeckUnderFilledException;
import exceptions.EmptyQuestionsListException;
import exceptions.ExceedMaxStepsException;
import exceptions.NotEnoughQuestionsException;
import exceptions.TooMuchQuestionsException;
import javafx.application.Platform;
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

	// Validation
	private ValidationGridPane validationGP;

	// Timer
	private TimerFlowPane timerFP;

	/**
	 * PlayingGridPane constructor
	 * 
	 * Instantiate earning, set defaut value for pyramidActualStep, cols and rows
	 * constraints, define spacings, add nodes and runs the party. If the specified
	 * deck does not fit the requirements, it does not run the party and let the
	 * user know that there has been a problem with the deck.
	 */
	public PlayingGridPane() {
		earning = new Earning(Earning.FILE_NAME);
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

		// Starts the party
		try {
			runNewParty("main_deck");
		} catch (EmptyQuestionsListException | DeckUnderFilledException | NotEnoughQuestionsException
				| TooMuchQuestionsException e) {
			alertPop("A problem occured with the deck : \"main_deck.json\".");
			e.printStackTrace();
		} catch (ExceedMaxStepsException e1) {
			// Make the user win the party
			alertPop("A problem occured with the party.");
			e1.printStackTrace();
		}
	}

	/**
	 * Run a new party using a questions deck.
	 * 
	 * @param dest The name of the JSON file used to get the questions deck.
	 * 
	 * @throws QuestionsListIsEmptyException When the questions deck is empty.
	 * 
	 * @throws DeckUnderFilledException      When the deck size is lower than the
	 *                                       number of steps.
	 * 
	 * @throws NotEnoughQuestionsException   When the deck has not enough questions
	 *                                       for each round.
	 * 
	 * @throws TooMuchQuestionsException     When the deck has too much questions
	 *                                       for a round.
	 * 
	 * @throws ExceedMaxStepsException       When the actual step is higher than the
	 *                                       max steps.
	 */
	public void runNewParty(String dest) throws EmptyQuestionsListException, DeckUnderFilledException,
			NotEnoughQuestionsException, TooMuchQuestionsException, ExceedMaxStepsException {
		party = new Party(Serialization.jsonToDeck(dest));
		getNextQuestion();

		// Get a new Pyramid
		pyramidGP = null;
		getPyramidGP();

		// Run the timer
		getTimerFP().runTimer();
	}

	/**
	 * Verify if the user has either won the party or lost or answered correctly and
	 * reacts accordingly.
	 * 
	 * @throws ExceedMaxStepsException When the actual step is higher than the max
	 *                                 steps.
	 */
	public void verifyAnswer() throws ExceedMaxStepsException {
		int answerIndex = questionGP.getAnswerIndex();
		int rightAnswerIndex = party.getRightAnswerIndex();
		int actualStep = party.getActualStep();

		// Actual step too high
		if (actualStep > Party.NB_STEPS) {
			throw new ExceedMaxStepsException(actualStep);
			// Still playing
		} else if (answerIndex == rightAnswerIndex) {
			onRightAnswer();
			// Lost
		} else {
			onLost();
		}
	}

	/**
	 * If the answer is right and the actual step is less than the max step, then it
	 * shows to the user that he answered correctly and pause the party for 1.5
	 * seconds. Then go to the next question.
	 * 
	 * If the user has won, it indicates this to him and then stop the timer.
	 */
	public void onRightAnswer() {
		int answerIndex = questionGP.getAnswerIndex();
		int rightAnswerIndex = party.getRightAnswerIndex();
		int actualStep = party.getActualStep();

		// When the player's still playing
		if (actualStep < Party.NB_STEPS) {
			// Sets the button showing as right
			questionGP.getBtnAnswer(answerIndex).setId("answerBtnOk");

			// Stops the timer
			getTimerFP().stopTimer();

			// New thread
			new Thread(new Runnable() {
				@Override
				public void run() {
					// Pause before going to the next step
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							// Runs the timer
							getTimerFP().runTimer();

							// Get the next question
							try {
								getNextQuestion();
							} catch (ExceedMaxStepsException e) {
								e.printStackTrace();
							}

							// Old earnings
							getPyramidGP().goToNextStep();

							// Reset answers color
							questionGP.getBtnAnswer(rightAnswerIndex).setId("answerBtn");
						}
					});
				}
			}).start();

			// When the player has won
		} else if (actualStep == Party.NB_STEPS) {
			endPartyWithResult(true);
		}
	}

	/**
	 * If the user has lost it indicates to him that he lost and the amount of money
	 * he won. Then gets the GUI back to the home pane.
	 */
	public void onLost() {
		int answerIndex = questionGP.getAnswerIndex();
		int rightAnswerIndex = party.getRightAnswerIndex();

		// Disable buttons except right answer button and button clicked by the user
		for (int i = 0; i <= Question.NB_ANSWERS - 1; i++) {
			if (i != rightAnswerIndex && i != answerIndex)
				questionGP.setDisableBtnAnswer(true, i);
		}

		endPartyWithResult(false);
	}

	/**
	 * Shows the next question on the GUI and hide previous jokers effects if used.
	 * 
	 * @throws ExceedMaxStepsException When the actual step is higher than the max
	 *                                 steps.
	 */
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

		// Hide every joker's effects
		if (getJokerVB().isCancelJokerResults()) {
			setVisibleLblJokerResults(false);

			getQuestionGP().setDisableBtnAnswer(false);
			for (int i = 0; i <= Question.NB_ANSWERS - 1; i++) {
				getQuestionGP().getBtnAnswer(i).setId("");
			}

			// Set the cancelJoker5050 field to false
			getJokerVB().setCancelJoker5050(false);

		}

		// Hide the 5050 joker results
		if (getJokerVB().isCancelJoker5050()) {
			// Set the cancelJoker5050 field to false
			getJokerVB().setCancelJoker5050(false);

			// Empty the joker5050Indexes field
			getParty().clearJoker5050Indexes();
		}
	}

	/**
	 * Return the Party instance.
	 * 
	 * @return earning instance of party.
	 */
	public Party getParty() {
		return party;
	}

	/**
	 * Stops the timer, then if "value" is true hide this pane and shows the winning
	 * pane, else it shows the loosing pane.
	 * 
	 * @param value, the boolean indicating if the user won or not.
	 */
	public void endPartyWithResult(boolean value) {
		getTimerFP().stopTimer();

		// Won
		if (value) {
			// Show the party won pane
			((PartyStackPane) getParent().getParent()).partyWon();
		}

		// Lost
		else {
			// Button turn red if false
			questionGP.getBtnAnswer(questionGP.getAnswerIndex()).setId("answerBtnNotOk");

			// Hide and stop the timer
			getTimerFP().setVisible(false);

			// Hide validation pane
			setVisibleValidationGP(false);
			// Disable btn "Cash in"
			getBtnCashIn().setDisable(true);

			// Loosing alert
//			alertPop("Sorry, you're a looser !\n" + "The right answer was\n\n \"" + party.getRightAnswer()
//					+ "\"\n\n You won : " + getEarningsWhenLost() + " Bitcoins");

			// Show the party lost pane
			((PartyStackPane) getParent().getParent()).partyLost();
		}

	}

	/**
	 * Return the Earning instance.
	 * 
	 * @return the instance of Earning.
	 */
	public static Earning getEarning() {
		return earning;
	}

	/**
	 * If jokerVB is null, instantiate a JokerVBox.
	 * 
	 * @return the instance of JokerVBox.
	 */
	public JokerVBox getJokerVB() {
		if (jokerVB == null)
			jokerVB = new JokerVBox(this);

		return jokerVB;
	}

	/**
	 * If questionGP is null, instantiate a QuestionGridPane.
	 * 
	 * @return the instance of QuestionGridPane.
	 */
	public QuestionGridPane getQuestionGP() {
		if (questionGP == null)
			questionGP = new QuestionGridPane();

		return questionGP;
	}

	/**
	 * Set the if answer buttons are enabled or not.
	 * 
	 * @param value the value to set.
	 */
	public void setDisableBtnAnswer(boolean value) {
		questionGP.setDisableBtnAnswer(value);
	}

	/**
	 * Set the if the answer button is enabled or not for the specified index.
	 * 
	 * @param value the value to set.
	 * @param index the button index.
	 */
	public void setDisableBtnAnswer(boolean value, int index) {
		questionGP.setDisableBtnAnswer(value, index);
	}

	/**
	 * If lblJokerResults is null then instantiate it with Question.NB_ANSWERS as
	 * size. For each Label in the array, set the ID as "jokerResults", set it
	 * invisible and center it vertically in the pane.
	 * 
	 * @param index used to specify which Label is requested.
	 * 
	 * @return the instance of Label bound to the index.
	 */
	public Label getLblJokerResults(int index) {
		if (lblJokerResults == null)
			lblJokerResults = new Label[Question.NB_ANSWERS];

		if (lblJokerResults[index] == null) {
			lblJokerResults[index] = new Label("");
			lblJokerResults[index].setId("jokerResults");
			lblJokerResults[index].setVisible(false);
			GridPane.setValignment(lblJokerResults[index], VPos.TOP);
		}
		return lblJokerResults[index];
	}

	/**
	 * Replace the specified Label text.
	 * 
	 * @param index used to specify which Label is requested.
	 * 
	 * @param text  used to replace the Label text.
	 */
	public void lblJokerResultsSetText(int index, String text) {
		getLblJokerResults(index).setText(text);
	}

	/**
	 * Set visible or not every joker results.
	 * 
	 * @param value boolean used set visible or not.
	 */
	public void setVisibleLblJokerResults(boolean value) {
		for (int i = 0; i <= Question.NB_ANSWERS - 1; i++) {
			lblJokerResults[i].setVisible(value);
		}
	}

	/**
	 * Set visible or not all the joker results.
	 * 
	 * @param value boolean used set visible or not.
	 * 
	 * @param index used to specify which joker result label is requested.
	 */
	public void setVisibleLblJokerResults(boolean value, int index) {
		lblJokerResults[index].setVisible(value);
	}

	/**
	 * If timerFP is null, instantiate it and set his ID and alignment.
	 * 
	 * @return The FimerFlowPane instance.
	 */
	public TimerFlowPane getTimerFP() {
		if (timerFP == null) {
			timerFP = new TimerFlowPane();
			timerFP.setId("timer");
			timerFP.setAlignment(Pos.CENTER);
		}
		return timerFP;
	}

	// Answer validation
	/***
	 * @param s is the text in the alert
	 * @return Alert
	 */
	public Alert alertPop(String s) {
		Alert alert = new Alert(AlertType.NONE, s, ButtonType.OK);
		alert.initModality(Modality.WINDOW_MODAL);
		alert.showAndWait();

		((ProjStackPane) getParent().getParent()).getHomeGridPane().setVisible(true);
		setVisible(false);

		return alert;
	}

	/**
	 * If validationGP is null, instantiate it and set it invisible.
	 * 
	 * @return The ValidationGridPane instance.
	 */
	public ValidationGridPane getValidationGP() {
		if (validationGP == null) {
			validationGP = new ValidationGridPane();
			validationGP.setId("validationPane");
			validationGP.setVisible(false);
			GridPane.setValignment(validationGP, VPos.CENTER);
		}
		return validationGP;
	}

	/**
	 * Set the ValidationGridPane as visible or not.
	 * 
	 * @param value boolean value used to set visible or not.
	 */
	public void setVisibleValidationGP(boolean value) {
		getValidationGP().setVisible(value);
	}

	/**
	 * If btnCashIn is null, instantiate it, set it's min size to 0x0 and it's
	 * prefSize to max.
	 * 
	 * @return Button the button reference.
	 */
	public Button getBtnCashIn() {
		if (btnCashIn == null) {
			btnCashIn = new Button("Cash-in");
			btnCashIn.setMinSize(0, 0);
			btnCashIn.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
			btnCashIn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// Show the party left pane
					((PartyStackPane) getParent().getParent()).partyLeft();

					getTimerFP().stopTimer();
				}
			});
		}
		return btnCashIn;
	}

	/**
	 * Get the earnings when the player loses.
	 *
	 * @return double, the amount won.
	 */
	public double getEarningsWhenLost() {
		double earn = 0;
		int actualStep = pyramidGP.getPyramidActualStep();
		if (actualStep > 9) {
			earn = 0;
		} else if (actualStep >= 5) {
			earn = PlayingGridPane.getEarning().getAmount(4);
		} else if (actualStep > 0) {
			earn = PlayingGridPane.getEarning().getAmount(9);
		}
		return earn;
	}

	/**
	 * Get the earnings when the player leaves.
	 * 
	 * @return double, the amount won.
	 */
	public double getEarningsWhenLeaving() {
		double earn = 0;
		for (int i = 13; i >= 0; i--) {
			if (pyramidGP.getPyramidActualStep() == i) {
				earn = PlayingGridPane.getEarning().getAmount(13 - i);
			}
		}
		return earn;
	}

	/**
	 * If the pyramidGP is null, instantiate it, set it's min height to 0 and it's
	 * ID to "earningsPyramid". Then shows it in the pane.
	 */
	public PyramidGridPane getPyramidGP() {
		if (pyramidGP == null) {
			pyramidGP = new PyramidGridPane();
			pyramidGP.setMinHeight(0);
			pyramidGP.setId("earningsPyramid");
			this.add(getPyramidGP(), 9, 2, 1, 9);
		}
		return pyramidGP;
	}
}
