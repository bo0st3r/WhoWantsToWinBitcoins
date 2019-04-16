package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import model.Question;

public class QuestionGridPane extends GridPane {
	private Label lblStatement;
	private Button btnAnswer[];
	private int answerIndex;

	public QuestionGridPane() {
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

	public Label getLblStatement() {
		if (lblStatement == null) {
			lblStatement = new Label("");
			lblStatement.setPrefHeight(Integer.MAX_VALUE);
			lblStatement.setPrefWidth(Integer.MAX_VALUE);
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
					((ProjStackPane) getParent().getParent().getParent()).getPlayingGridPane().setVisibleValidationGP(true);
				}
			});
		}
		return btnAnswer[index];
	}

	public void setDisableBtnAnswer(boolean value, int index) {
		btnAnswer[index].setDisable(value);
	}

	public void setDisableBtnAnswer(boolean value) {
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
}
