package view;

import java.util.Map.Entry;
import java.util.Set;

import exceptions.DeckUnderFilledException;
import exceptions.ExceedMaxStepsException;
import exceptions.NotEnoughQuestionsException;
import exceptions.QuestionsListIsEmptyException;
import exceptions.TooMuchQuestionsException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Party;
import model.Question;
import utilities.Serialization;

public class PlayingGridPane extends GridPane {
	private Party party;

	private Label lblStatement;

	private Button btnAnswer[];
	private Button btnPrevious;
	private Button btnExit;
	private Button btnFriend;
	private Button btnPublic;
	private Button btn5050;

	private int answerIndex;
	private String rightAnswer;

	public PlayingGridPane() {
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
	}

	public void runNewParty(String dest) throws QuestionsListIsEmptyException, DeckUnderFilledException,
			NotEnoughQuestionsException, TooMuchQuestionsException, ExceedMaxStepsException {
		party = new Party(Serialization.jsonToDeck(dest));

		getNextQuestion();
	}

	public void verifyAnswer() throws ExceedMaxStepsException {
		if (getBtnAnswer(answerIndex).getText().equals(rightAnswer))
			getNextQuestion();
		else {
			endParty();
			setVisible(false);
			((ProjStackPane) getParent().getParent()).getHomeGridPane().setVisible(true);
		}
			
	}

	public void getNextQuestion() throws ExceedMaxStepsException  {
		// Gets the next question
		Question actualQuestion = party.getQuestionNextStep();

		// Sets new statement
		getLblStatement().setText(actualQuestion.getStatement());

		// Sets new answers
		Set<Entry<String, Boolean>> choices = actualQuestion.getChoices().entrySet();

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
		if (btnAnswer == null) {
			btnAnswer = new Button[Question.NB_ANSWERS];
			for (int i = 0; i <= Question.NB_ANSWERS - 1; i++) {
				btnAnswer[i] = new Button("");
			}

			for (int i = 0; i <= Question.NB_ANSWERS - 1; i++) {

				Scene secondScene = new Scene(new ValidationGridPane(), 450, 180);
				btnAnswer[i].setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						secondScene.getStylesheets().addAll(getScene().getStylesheets());
						Stage secondStage = new Stage();
						secondStage.setTitle("Validation");
						secondStage.setScene(secondScene);
						// Set the main Stage as it's owner
						secondStage.initOwner(getScene().getWindow());
						// Disable from acting on the owner stage while this window's open
						secondStage.initModality(Modality.WINDOW_MODAL);
						// Removes basic Windows style
						secondStage.initStyle(StageStyle.UNDECORATED);
						secondStage.show();
					}
				});
			}
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

	public Button getBtnExit() {
		if (btnExit == null) {
			btnExit = new Button("Exit");

		}
		return btnExit;
	}

	public Button getBtnFriends() {
		return btnFriend;
	}

	public Button getBtnPublic() {
		return btnPublic;
	}

	public Button getBtn5050() {
		return btn5050;
	}

}
