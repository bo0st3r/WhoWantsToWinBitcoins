package view;

import enumerations.Round;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import model.Question;

public class AddQuestionGP extends GridPane {

	private TextField txtAuthor;
	private TextField txtStatement;
	private TextField txtAnswer[];

	private Label lblAuthor;
	private Label lblStatement;
	private Label lblChoices;
	private Label lblRight;

	private ComboBox<String> cboBoxRound;

	private RadioButton rdoAnswer[];

	private Button btnOk;

	private ToggleGroup tglTrue;

	/*
	 * Constructor. Set rows and cols constraints, alignment, spacings and the pane
	 * contents.
	 */
	public AddQuestionGP() {
//		this.setGridLinesVisible(true);

		// Alignment
		this.setAlignment(Pos.BASELINE_CENTER);

		// Spacings
		this.setPadding(new Insets(10));
		this.setHgap(5);
		this.setVgap(5);

		// Defines 6 columns of 17% for the grid
		ColumnConstraints col = new ColumnConstraints();
		col.setPercentWidth(17);
		this.getColumnConstraints().addAll(col, col, col, col, col, col);

		// Author
		this.add(getLblAuthor(), 0, 0);
		this.add(getTxtAuthor(), 0, 1, 3, 1);

		// Round
		this.add(getCboBoxRound(), 4, 1, 2, 1);

		// Statement
		this.add(getLblStatement(), 0, 2);
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

		this.add(getBtnOk(), 2, 12, 2, 1);
	}

	/*
	 * If null, instantiates cboBoxRound and returns it. Set it's alignment, add
	 * it's items and set the first item as selected.
	 * 
	 * @return the cboBoxRound object.
	 */
	public ComboBox<String> getCboBoxRound() {
		if (cboBoxRound == null) {
			cboBoxRound = new ComboBox<String>();
			GridPane.setHalignment(cboBoxRound, HPos.RIGHT);

			for (Round r : Round.values()) {
				cboBoxRound.getItems().add(r.getRoundStatement());
			}

			cboBoxRound.setValue(Round.values()[0].getRoundStatement());
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
			txtAuthor.setPromptText("Enter author's name");
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
		}

		return lblStatement;
	}

	/*
	 * If null, instantiates lblAuthor and returns it.
	 * 
	 * @return the lblAuthor object.
	 */
	public Label getLblAuthor() {
		if (lblAuthor == null) {
			lblAuthor = new Label("Author :");
		}

		return lblAuthor;
	}

	/*
	 * If null, instantiates lblChoices and returns it.
	 * 
	 * @return the lblChoices object.
	 */
	public Label getLblChoices() {
		if (lblChoices == null) {
			lblChoices = new Label("Choices :");
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
		if (rdoAnswer == null)
			rdoAnswer = new RadioButton[Question.NB_ANSWERS];

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
	 * If null, instantiates btnOk and returns it.
	 * 
	 * @return the btnOk object.
	 */
	public Button getBtnOk() {
		if (btnOk == null) {
			btnOk = new Button("Add");
			GridPane.setHalignment(btnOk, HPos.CENTER);
			btnOk.setPrefWidth(Integer.MAX_VALUE);
		}

		return btnOk;
	}

}
