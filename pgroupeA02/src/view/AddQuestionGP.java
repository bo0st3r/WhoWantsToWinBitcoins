package view;

import enumerations.Round;
import exceptions.AnswerAlreadyPresentException;
import exceptions.NeedRightAnswerException;
import exceptions.NotARoundException;
import exceptions.NotEnoughAnswersException;
import exceptions.QuestionAlreadyPresentException;
import exceptions.RightAnswerAlreadyPresentException;
import exceptions.StatementTooShortException;
import exceptions.TooMuchAnswersException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import model.Deck;
import model.Question;
import utilities.Serialization;
import view.tableviews.TableViewQuestionsBP;

public class AddQuestionGP extends GridPane {

	private TextField txtAuthor;
	private TextField txtStatement;
	private TextField txtAnswer[];

	private Label lblAuthor;
	private Label lblRound;
	private Label lblStatement;
	private Label lblStatementError;
	private Label lblChoices;
	private Label lblRight;

	private ComboBox<Round> cboBoxRound;

	private Button btnConfirm;

	private RadioButton rdoAnswer[];

	private ToggleGroup tglTrue;

	/*
	 * Constructor. Set rows and cols constraints, alignment, spacings and the pane
	 * contents.
	 */
	public AddQuestionGP() {
//		this.setGridLinesVisible(true);

		// Alignment
		this.setAlignment(Pos.CENTER);

		// Spacings
		this.setHgap(5);
		this.setVgap(20);

		// Defines 6 columns of 17% for the grid
		ColumnConstraints col = new ColumnConstraints();
		col.setPercentWidth(100. / 6.);
		this.getColumnConstraints().addAll(col, col, col, col, col, col);

		// Author
		this.add(getLblAuthor(), 0, 0);
		this.add(getTxtAuthor(), 0, 1, 3, 1);

		// Round
		this.add(getLblRound(), 5, 0);
		this.add(getCboBoxRound(), 4, 1, 2, 1);

		// Statement
		this.add(getLblStatement(), 0, 2);
		this.add(getLblStatementError(), 5, 2, 2, 1);
		this.add(getTxtStatement(), 0, 3, 6, 1);

		// Choices
		this.add(getLblChoices(), 1, 5, 4, 1);
		this.add(getLblRight(), 5, 5);

		// Adding the TextFields for answers and the RadioButtons to select the right
		// answer
		for (int i = 0; i <= Question.NB_ANSWERS - 1; i++) {
			this.add(getTxtAnswer(i), 1, i + 6, 4, 1);
			this.add(getRdoAnswer(i), 5, i + 6);
		}

		this.add(getBtnConfirm(), 4, 11, 2, 1);
	}

	/*
	 * If null, instantiates cboBoxRound and returns it. Set it's alignment, add
	 * it's items and set the first item as selected.
	 * 
	 * @return the cboBoxRound object.
	 */
	public ComboBox<Round> getCboBoxRound() {
		if (cboBoxRound == null) {
			cboBoxRound = new ComboBox<Round>();
			cboBoxRound.getStyleClass().add("combo-box-large");
			GridPane.setHalignment(cboBoxRound, HPos.RIGHT);

			for (Round r : Round.values()) {
				cboBoxRound.getItems().add(r);
			}

			cboBoxRound.setValue(Round.values()[0]);
		}

		return cboBoxRound;
	}

	/*
	 * If null, instantiates txtAnswer for the index passed as an param and returns
	 * it.
	 * 
	 * @return the txtAnswer object.
	 */
	public TextField getTxtAnswer(int index) {
		if (txtAnswer == null)
			txtAnswer = new TextField[Question.NB_ANSWERS];

		if (txtAnswer[index] == null) {
			txtAnswer[index] = new TextField();
			txtAnswer[index].getStyleClass().add("textfield-large");
			txtAnswer[index].setPromptText("Enter choice number " + (index + 1));
		}

		return txtAnswer[index];
	}

	/*
	 * If null, instantiates txtAuthor and returns it.
	 * 
	 * @return the txtAuthor object.
	 */
	public TextField getTxtAuthor() {
		if (txtAuthor == null) {
			txtAuthor = new TextField();
			txtAuthor.getStyleClass().add("textfield-large");
			txtAuthor.setPromptText("Enter author's name (can be empty)");
		}

		return txtAuthor;
	}

	/*
	 * If null, instantiates txtStatement and returns it.
	 * 
	 * @return the txtStatement object.
	 */
	public TextField getTxtStatement() {
		if (txtStatement == null) {
			txtStatement = new TextField();
			txtStatement.getStyleClass().add("textfield-large");
			txtStatement.setPromptText("Enter the statement");
		}

		return txtStatement;
	}

	/*
	 * If null, instantiates lblStatement and returns it.
	 * 
	 * @return the lblStatement object.
	 */
	public Label getLblStatement() {
		if (lblStatement == null) {
			lblStatement = new Label("Statement :");
			lblStatement.getStyleClass().add("title-medium");
		}

		return lblStatement;
	}

	/*
	 * If null, instantiates lblStatementError and returns it.
	 * 
	 * @return the lblStatementError object.
	 */
	public Label getLblStatementError() {
		if (lblStatementError == null) {
			lblStatementError = new Label("This statement is too short.");
			lblStatementError.getStyleClass().add("input-error");
			lblStatementError.setAlignment(Pos.BOTTOM_RIGHT);
			lblStatementError.setVisible(false);
		}

		return lblStatementError;
	}

	/*
	 * If null, instantiates lblAuthor and returns it.
	 * 
	 * @return the lblAuthor object.
	 */
	public Label getLblAuthor() {
		if (lblAuthor == null) {
			lblAuthor = new Label("Author :");
			lblAuthor.getStyleClass().add("title-medium");
		}

		return lblAuthor;
	}

	/*
	 * If null, instantiates lblRound and returns it.
	 * 
	 * @return the lblRound object.
	 */
	public Label getLblRound() {
		if (lblRound == null) {
			lblRound = new Label("Round :");
			lblRound.getStyleClass().add("title-medium");
		}

		return lblRound;
	}

	/*
	 * If null, instantiates lblChoices and returns it.
	 * 
	 * @return the lblChoices object.
	 */
	public Label getLblChoices() {
		if (lblChoices == null) {
			lblChoices = new Label("Choices :");
			lblChoices.getStyleClass().add("title-medium");
			GridPane.setHalignment(lblChoices, HPos.CENTER);
		}

		return lblChoices;
	}

	/*
	 * If null, instantiates lblRight and returns it.
	 * 
	 * @return the lblRight object.
	 */
	public Label getLblRight() {
		if (lblRight == null) {
			lblRight = new Label("Right one");
			lblRight.getStyleClass().add("title-medium");
			GridPane.setHalignment(lblRight, HPos.CENTER);
		}

		return lblRight;
	}

	/*
	 * If null, instantiates tglTrue and returns it.
	 * 
	 * @return the tglTrue object.
	 */
	public ToggleGroup getTglTrue() {
		if (tglTrue == null) {
			tglTrue = new ToggleGroup();
		}

		return tglTrue;
	}

	/*
	 * If null, instantiates rdoAnswer with Question.NB_ANSWERS for it's size and
	 * returns the rdoAnswer corresponding to the index passed as a param. Set the
	 * first RadioButton as selected.
	 * 
	 * @param int, the index used to select the RadioButton in the array.
	 * 
	 * @return the rdoAnswer object ccorresponding to the index passed as a param.
	 */
	public RadioButton getRdoAnswer(int index) {
		if (rdoAnswer == null) {
			rdoAnswer = new RadioButton[Question.NB_ANSWERS];
		}

		if (rdoAnswer[index] == null) {
			rdoAnswer[index] = new RadioButton();
			GridPane.setHalignment(rdoAnswer[index], HPos.CENTER);

			// Groups the 4 radio buttons that states which answer is right together
			rdoAnswer[index].setToggleGroup(getTglTrue());

			// Automatically sets the first answer as right so we can't add a question
			// without right answer
			if (index == 0) {
				rdoAnswer[index].setSelected(true);
			}
		}

		return rdoAnswer[index];
	}

	/*
	 * Returns the confirmation button, if null instantiates it before returning.
	 * When clicking on this button, it tries to add a question to the deck using
	 * this pane fields.
	 * 
	 * @return the Button object "btnConfirm".
	 */
	public Button getBtnConfirm() {
		if (btnConfirm == null) {
			btnConfirm = new Button("Confirm");
			btnConfirm.getStyleClass().add("button-medium");
			GridPane.setHalignment(btnConfirm, HPos.CENTER);
			btnConfirm.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// Creates the question
					Question question = createQuestion();
					if (question == null)
						return;

					// Gets the old deck
					Deck deck = Serialization.jsonToDeck(Deck.FILE_NAME);

					// Updates the deck file and the view if the question has been added
					if (addQuestionToDeck(deck, question)) {
						Serialization.deckToJson(deck, Deck.FILE_NAME);
						((TableViewQuestionsBP) getParent()).getTv().getItems().add(question);

					}
				}
			});
		}

		return btnConfirm;
	}

	/**
	 * Tries to create a fulfilled Question object using this pane fields and
	 * returns it. Returns null instead of a Question object if an error happened.
	 * 
	 * @return the created Question or null if an error happened.
	 */
	public Question createQuestion() {
		String author = getTxtAuthor().getText();
		String statement = getTxtStatement().getText();
		if (statement.length() > 0)
			statement = statement.substring(0, 1).toUpperCase() + statement.substring(1);
		Round round = getCboBoxRound().getValue();

		Question newQuestion = null;
		// Creates the question
		try {
			newQuestion = new Question(author, statement, round);

			// Adds the answers to it
			if (!addChoicesToQuestion(newQuestion))
				return null;

		} catch (StatementTooShortException e) {
			AlertError error = new AlertError("This statement is too short.");
			error.showAndWait();
			e.printStackTrace();
			return null;

		} catch (NotARoundException e) {
			AlertError error = new AlertError("You have to chose a round for this question.");
			error.showAndWait();
			e.printStackTrace();
			return null;
		}

		return newQuestion;
	}

	/**
	 * Adds choices to the given Question object using this pane fields and returns
	 * true if no error happened.
	 * 
	 * @param newQuestion the Question where to add the choices.
	 * @return boolean if the method worked successfully.
	 */
	public boolean addChoicesToQuestion(Question newQuestion) {
		for (int i = 0; i <= Question.NB_ANSWERS - 1; i++) {
			String answer = getTxtAnswer(i).getText();
			if (answer.length() < 1) {
				AlertError error = new AlertError("Every choice field must be filled.");
				error.showAndWait();
				return false;
			}
			boolean value = getRdoAnswer(i).isSelected();

			try {
				newQuestion.addChoice(answer, value);

			} catch (TooMuchAnswersException e) {
				AlertError error = new AlertError("There's too much choices for this question.");
				error.showAndWait();
				e.printStackTrace();
				return false;

			} catch (AnswerAlreadyPresentException e) {
				AlertError error = new AlertError("Two or more choices are the same.");
				error.showAndWait();
				e.printStackTrace();
				return false;

			} catch (RightAnswerAlreadyPresentException e) {
				AlertError error = new AlertError("There's can be only one right choice.");
				error.showAndWait();
				e.printStackTrace();
				return false;

			} catch (NeedRightAnswerException e) {
				AlertError error = new AlertError("Your question need a right choice.");
				error.showAndWait();
				e.printStackTrace();
				return false;
			}
		}

		return true;
	}

	/**
	 * Adds the given Question to the deck and returns true if no error happened.
	 * 
	 * @param deck        the Deck where to put newQuestion.
	 * @param newQuestion the Question to put.
	 * @return boolean if the method worked successfully.
	 */
	public boolean addQuestionToDeck(Deck deck, Question newQuestion) {
		try {
			deck.addQuestion(newQuestion.clone());
			((TableViewQuestionsBP) getParent()).getDeck().addQuestion(newQuestion.clone());

		} catch (QuestionAlreadyPresentException e) {
			AlertError error = new AlertError("The deck already contains this question.");
			error.showAndWait();
			e.printStackTrace();
			return false;

		} catch (NotEnoughAnswersException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
}
