package view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class AddQuestionGridPane extends GridPane {
	private TextField txtStatement;
	private TextField txtAnswer[];

	private Label lblStatement;
	private Label lblChoices;
	private Label lblRight;

	private RadioButton rdoAnswer[];

	private Button btnOk;

	private ToggleGroup tglTrue;

	public AddQuestionGridPane() {
		this.setAlignment(Pos.BASELINE_CENTER);
		this.setPadding(new Insets(10));
		this.setHgap(5);
		this.setVgap(5);
//		this.setGridLinesVisible(true);

		// Defines 6 columns of 17% for the grid
		ColumnConstraints col = new ColumnConstraints();
		col.setPercentWidth(17);
		this.getColumnConstraints().addAll(col, col, col, col, col, col);

		// Statement
		this.add(getLblStatement(), 0, 0);
		this.add(getTxtStatement(), 0, 1, 6, 2);

		// Choices
		this.add(getLblChoices(), 1, 3, 4, 1);
		GridPane.setHalignment(getLblChoices(), HPos.CENTER);
		this.add(getLblRight(), 5, 3);
		GridPane.setHalignment(getLblRight(), HPos.CENTER);

		// Adding the TextFields for answers and the RadioButtons to select the right
		// answer
		for (int i = 0; i <= 3; i++) {
			this.add(getTxtAnswer(i), 1, i + 4, 4, 1);
			this.add(getRdoAnswer(i), 5, i + 4);
			GridPane.setHalignment(getRdoAnswer(i), HPos.CENTER);
		}

		this.add(getBtnOk(), 2, 10, 2, 1);
		GridPane.setHalignment(getBtnOk(), HPos.CENTER);

	}

	public TextField getTxtStatement() {
		if (txtStatement == null) {
			txtStatement = new TextField();
			txtStatement.setPromptText("Enter the statement");
		}

		return txtStatement;
	}

	public Label getLblStatement() {
		if (lblStatement == null) {
			lblStatement = new Label("Statement :");
		}

		return lblStatement;
	}

	public Label getLblChoices() {
		if (lblChoices == null) {
			lblChoices = new Label("Choices :");
		}

		return lblChoices;
	}

	public Label getLblRight() {
		if (lblRight == null) {
			lblRight = new Label("Right one");
		}

		return lblRight;
	}

	public ToggleGroup getTglTrue() {
		if (tglTrue == null) {
			tglTrue = new ToggleGroup();
		}

		return tglTrue;
	}

	public TextField getTxtAnswer(int index) {
		if (txtAnswer == null)
			txtAnswer = new TextField[4];

		if (txtAnswer[index] == null) {
			txtAnswer[index] = new TextField();
			txtAnswer[index].setPromptText("Enter choice number " + (index + 1));
		}

		return txtAnswer[index];
	}

	public RadioButton getRdoAnswer(int index) {
		if (rdoAnswer == null)
			rdoAnswer = new RadioButton[4];

		if (rdoAnswer[index] == null) {
			rdoAnswer[index] = new RadioButton();
			// Groups the 4 radio buttons that states which answer is right together
			rdoAnswer[index].setToggleGroup(getTglTrue());

			// Automaticaly sets the first answer as right so we can't add a question without right answer
			if (index == 0) {
				rdoAnswer[index].setSelected(true);
			}
		}

		return rdoAnswer[index];
	}

	public Button getBtnOk() {
		if (btnOk == null)
			btnOk = new Button("Add");

		return btnOk;
	}
}
