package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import model.Question;

public class QuestionGP extends GridPane {
	private Label lblStatement;
	private Button btnAnswer[];
	private int answerIndex;

	/**
	 * Constructor. Sets rows and cols constraints and define the pane contents.
	 */
	public QuestionGP() {
//		setGridLinesVisible(true);

		// Row constraints
		double[] sizesRow = { 44.1, 3.2, 25.3, 2.1, 25.3 };
		for (int i = 0; i <= sizesRow.length - 1; i++) {
			RowConstraints row = new RowConstraints();
			row.setPercentHeight(sizesRow[i]);
			getRowConstraints().add(row);
		}

		// Col constraints
		int[] sizesCol = { 49, 2, 49 };
		for (int i = 0; i <= sizesCol.length - 1; i++) {
			ColumnConstraints col = new ColumnConstraints();
			col.setPercentWidth(sizesCol[i]);
			getColumnConstraints().add(col);
		}

		// Question statement label
		add(getLblStatement(), 0, 0, 5, 1);

		// Answer buttons
		add(getBtnAnswer(0), 0, 2);
		add(getBtnAnswer(1), 0, 4);
		add(getBtnAnswer(2), 2, 2);
		add(getBtnAnswer(3), 2, 4);
	}

	/**
	 * If null, instantiates lblStatement, sets it's prefered size and CSS ID, then
	 * returns it.
	 * 
	 * @return lblStatement, a Label object which contains the actual question
	 *         statement.
	 */
	public Label getLblStatement() {
		if (lblStatement == null) {
			lblStatement = new Label("");
			lblStatement.setPrefHeight(Integer.MAX_VALUE);
			lblStatement.setPrefWidth(Integer.MAX_VALUE);
			lblStatement.setId("questionStatement");
		}
		return lblStatement;
	}

	/**
	 * If null instantiates btnAnswer list, for the specified index sets it's
	 * prefered size, CSS ID and action when clicking on it and then returns it.
	 * Clicking on the button will set it's index as the new value of answerIndex,
	 * show the ValidationGridPane and disable cash in + every answers buttons.
	 * 
	 * @param index, the list index of the selected button answer button.
	 * 
	 * @return btnAnswer, a Button object which contains the answer for the
	 *         specified index.
	 */
	public Button getBtnAnswer(int index) {
		if (btnAnswer == null)
			btnAnswer = new Button[Question.NB_ANSWERS];

		if (btnAnswer[index] == null) {
			btnAnswer[index] = new Button("");
			btnAnswer[index].setPrefWidth(Integer.MAX_VALUE);
			btnAnswer[index].setPrefHeight(Integer.MAX_VALUE);
			btnAnswer[index].setId("answerBtn");

			btnAnswer[index].setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// Set the answer index
					answerIndex = index;

					PlayingGP pgp = ((PartySP) getParent().getParent().getParent()).getPlayingGridPane();

					btnAnswer[index].setStyle("-fx-opacity:1.0; -fx-font-family:'IBM Plex Sans SemiBold', sans-serif;");

					// Set validation grid pane visible
					pgp.setVisibleValidationGP(true);
					// Disable btn "Cash in"
					pgp.getBtnCashIn().setDisable(true);
					// Disable answers buttons
					pgp.setDisableBtnAnswer(true);
				}
			});
		}
		return btnAnswer[index];
	}

	/**
	 * Set disabled or not the answer btn specified by the index using the boolean
	 * value.
	 * 
	 * @param value, the new boolean value (true for disabled, false for enabled).
	 * 
	 * @param index, the index of the specified answer button.
	 */
	public void setDisableBtnAnswer(boolean value, int index) {
		btnAnswer[index].setDisable(value);
	}

	/**
	 * Set disabled or not every answer button using the boolean value.
	 * 
	 * @param value, the new boolean value (true for disabled, false for enabled).
	 */
	public void setDisableBtnAnswer(boolean value) {
		for (int i = 0; i <= Question.NB_ANSWERS - 1; i++) {
			btnAnswer[i].setDisable(value);
		}
	}

	/**
	 * Return the actual answer's index.
	 * 
	 * @return answerIndex, the index of the last answer button clicked by the user.
	 */
	public int getAnswerIndex() {
		return answerIndex;
	}
}
