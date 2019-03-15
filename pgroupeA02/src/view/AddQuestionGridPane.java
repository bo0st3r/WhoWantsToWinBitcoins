package view;

import enumerations.Round;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class AddQuestionGridPane extends GridPane {
	// No need to initialize the singleton at program's start because it's a rarely used pane
	private static AddQuestionGridPane SINGLETON = null;

	private TextField txtAuthor;
	private TextField txtStatement;
	private TextField txtAnswer[];

	private Label lblAuthor;
	private Label lblStatement;
	private Label lblChoices;
	private Label lblRight;

	private ComboBox<Round> cboBoxRound;

	private RadioButton rdoAnswer[];

	private Button btnOk;

	private ToggleGroup tglTrue;

	private AddQuestionGridPane() {
		this.setAlignment(Pos.BASELINE_CENTER);
		this.setPadding(new Insets(10));
		this.setHgap(5);
		this.setVgap(5);
//		this.setGridLinesVisible(true);

		// Defines 6 columns of 17% for the grid
		ColumnConstraints col = new ColumnConstraints();
		col.setPercentWidth(17);
		this.getColumnConstraints().addAll(col, col, col, col, col, col);

		// Author
		this.add(getLblAuthor(), 0, 0);
		this.add(getTxtAuthor(), 0, 1, 3, 1);

		// Round
		this.add(getCboBoxRound(), 4, 1, 2, 1);
		GridPane.setHalignment(getCboBoxRound(), HPos.RIGHT);

		// Statement
		this.add(getLblStatement(), 0, 2);
		this.add(getTxtStatement(), 0, 3, 6, 1);

		// Choices
		this.add(getLblChoices(), 1, 5, 4, 1);
		GridPane.setHalignment(getLblChoices(), HPos.CENTER);
		this.add(getLblRight(), 5, 5);
		GridPane.setHalignment(getLblRight(), HPos.CENTER);
		

		// Adding the TextFields for answers and the RadioButtons to select the right
		// answer
		for (int i = 0; i <= 3; i++) {
			this.add(getTxtAnswer(i), 1, i + 6, 4, 1);
			this.add(getRdoAnswer(i), 5, i + 6);
			GridPane.setHalignment(getRdoAnswer(i), HPos.CENTER);
		}

		this.add(getBtnOk(), 2, 12, 2, 1);
		GridPane.setHalignment(getBtnOk(), HPos.CENTER);
		getBtnOk().setPrefWidth(Integer.MAX_VALUE);
		System.out.println(Integer.MAX_VALUE);
	}

	public static AddQuestionGridPane getSingleton() {
		if(SINGLETON == null) {
			SINGLETON = new AddQuestionGridPane();
		}
		return SINGLETON;
	}

	public ComboBox<Round> getCboBoxRound() {
		if (cboBoxRound == null) {
			cboBoxRound = new ComboBox<Round>();
			cboBoxRound.getItems().setAll(Round.values());
			cboBoxRound.setValue(Round.values()[0]);
		}

		return cboBoxRound;
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

	public TextField getTxtAuthor() {
		if (txtAuthor == null) {
			txtAuthor = new TextField();
			txtAuthor.setPromptText("Enter author's name");
		}

		return txtAuthor;
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

	public Label getLblAuthor() {
		if (lblAuthor == null) {
			lblAuthor = new Label("Author :");
		}

		return lblAuthor;
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

	public RadioButton getRdoAnswer(int index) {
		if (rdoAnswer == null)
			rdoAnswer = new RadioButton[4];

		if (rdoAnswer[index] == null) {
			rdoAnswer[index] = new RadioButton();
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

	public Button getBtnOk() {
		if (btnOk == null)
			btnOk = new Button("Add");
			btnOk.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					setVisible(false);
					((ProjStackPane)getParent().getParent()).getHomeGridPane().setVisible(true);
				}
			});

		return btnOk;
	}

}
