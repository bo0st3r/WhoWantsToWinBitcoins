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
import javafx.geometry.Insets;
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
import javafx.stage.StageStyle;
import model.Earning;
import model.Joker;
import model.JokerPublic;
import model.Party;
import model.Question;
import utilities.Serialization;

public class PlayingGridPane extends GridPane {
	private static Earning earning;
	private Party party;

	private Label lblStatement;

	private Button btnAnswer[];
	private Button btnPrevious;
	private Button btnExit;

	private int answerIndex;
	private String rightAnswer;

	// Jokers
	private Joker joker;
	private Button btnJokerPublic;
	private Button btnJokerFriend;
	private Button btnJoker5050;

	// Earnings pyramid
	private PyramidVBox pyramidVbox;
	private Paint rgbGreen = Color.rgb(100, 255, 100);
	private Paint rgbActualStepColor = Color.rgb(255, 255, 100);
	private int pyramidActualStep;

	// Timer
	private TimerFlowPane timerFlowPane;

	public PlayingGridPane() {
		earning = new Earning();
		joker = new Joker();
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
		this.add(getTimerFlowPane(), 4, 5);

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

			getNextQuestion();
			// Reset the timer
			resetTimer();

			// pyramid METHODE A PART !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			getPyramidVbox().getGain(pyramidActualStep)
					.setBackground(new Background(new BackgroundFill(rgbGreen, null, null)));
			getPyramidVbox().getGain(pyramidActualStep - 1)
					.setBackground(new Background(new BackgroundFill(rgbActualStepColor, null, null)));
			pyramidActualStep--;

			// Won the party
		} else if (party.getActualStep() > Party.NB_STEPS) {
			endParty();
			setVisible(false);
			((ProjStackPane) getParent().getParent()).getHomeGridPane().setVisible(true);
			// Loosed
		} else {
			endParty();
			setVisible(false);
			((ProjStackPane) getParent().getParent()).getHomeGridPane().setVisible(true);
		}
	}

	public Button getBtnJokerPublic() {
		if (btnJokerPublic == null) {
			btnJokerPublic = new Button("Call the public");

			btnJokerPublic.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					joker.setStrategy(new JokerPublic());
					joker.useJoker();
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
//				Scene secondScene = new Scene(new ValidationGridPane(), 450, 180);
			btnAnswer[index].setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					answerIndex = index;
//						secondScene.getStylesheets().addAll(getScene().getStylesheets());
//						Stage secondStage = new Stage();
//						secondStage.setTitle("Validation");
//						secondStage.setScene(secondScene);
//						// Set the main Stage as it's owner
//						secondStage.initOwner(getScene().getWindow());
//						// Disable from acting on the owner stage while this window's open
//						secondStage.initModality(Modality.WINDOW_MODAL);
//						// Removes basic Windows style
//						secondStage.initStyle(StageStyle.UNDECORATED);
//						secondStage.show();
					Alert alert = new Alert(AlertType.NONE, "Are you sure?", ButtonType.YES, ButtonType.NO);
					alert.initModality(Modality.WINDOW_MODAL);
					alert.initStyle(StageStyle.UNDECORATED);
					alert.showAndWait();

					if (alert.getResult() == ButtonType.YES)
						try {
							verifyAnswer();
						} catch (ExceedMaxStepsException e) {
							e.printStackTrace();
						}

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

	public Button getBtnExit() {
		if (btnExit == null) {
			btnExit = new Button("Exit");

		}
		return btnExit;
	}

	public PyramidVBox getPyramidVbox() {
		if (pyramidVbox == null) {
			pyramidVbox = new PyramidVBox();
			this.add(pyramidVbox, 9, 1, 2, 9);
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

}
