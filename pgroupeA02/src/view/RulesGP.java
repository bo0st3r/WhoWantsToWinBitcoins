package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class RulesGP extends GridPane {

	private Label lblTitle;
	private Label lblText;
	private Button btnHome;
	private ScrollPane scpRule;

	/*
	 * Constructor. Set the spacings, cols and rows constraints and define the pane
	 * content.
	 */
	public RulesGP() {
		 this.setGridLinesVisible(true);
		setId("rules");

		// Spacings
		setPadding(new Insets(10));
		setHgap(8);
		setVgap(8);

		// Columns constraints
		ColumnConstraints c = new ColumnConstraints();
		c.setPercentWidth(10);
		getColumnConstraints().addAll(c, c, c, c, c, c, c, c, c, c);

		// Rows constraints
		RowConstraints r = new RowConstraints();
		r.setPercentHeight(10);
		getRowConstraints().addAll(r, r, r, r, r, r, r, r, r, r, r, r, r, r);

		// Title
		add(getLblTitle(), 2, 1, 6, 2);

		// Text
		add(getScpRule(), 1, 3, 8, 9);

		// Previous button
		add(getBtnHome(), 4, 12, 2, 2);
	}

	/*
	 * If null instantiates lblTitle, sets it's horizontal alignment and ID, then
	 * returns it.
	 * 
	 * @return lblTitle, a Label object which contains the the title of the pane.
	 */
	public Label getLblTitle() {
		if (lblTitle == null) {
			lblTitle = new Label("Game rules");
			GridPane.setHalignment(lblTitle, HPos.CENTER);
			lblTitle.getStyleClass().add("title-large");
			lblTitle.setId("titleHome");
		}
		return lblTitle;
	}

	/*
	 * If null instantiates lblTitle, sets it's ID and then returns it.
	 * 
	 * @return lblTitle, a Label object which contains the the rules.
	 */
	public Label getLblText() {
		if (lblText == null) {
			lblText = new Label(
					"The \"Who Wants to win Bitcoins\" questions are structured according to five differents Levels with each level increasing in difficulty. Each level contains five questions.\r\n"
							+ "\r\n"
							+ "Questions that are grouped into the same level will all be of similar difficulty. For example: Questions 1-5 make up the first Level and will contain the easiest questions. The second Level (Questions 6 � 10) will be compose by middle difficult questions. The thirtLevel (Questions 11-15) will consist of really difficult questions.\r\n"
							+ "\r\n"
							+ "It's important to remember that the questions which make up each level will not necessarily relate to the same or even similar topics, but their overall level of difficulty will be the same. \r\n"
							+ "\r\n"
							+ "There are three �step� in the question structure (Questions five, ten and fifteen). Gamers accumulate money with each correct answer, but should the they answer incorrectly before reaching a step, they stand to lose a large amount of winnings. \r\n"
							+ "\r\n"
							+ "If gamers get a question wrong before the first step, they leave with nothing. If this question is answered correctly, gamers are guaranteed the of the fifth question amount\r\n"
							+ "\r\n"
							+ "If gamers get this question wrong before the second step, they leave with the of the fifth question amount. If this question is answered correctly, gamers are guaranteed the of the 10th question amount.\r\n"
							+ "\nJOKERS:"
							+ "\nGamers are allowed three Jokers that they can use at any point during the game. Each Joker can only be used once.\r\n"
							+ "\r\n"
							+ "50/50 � eliminates two incorrect answers from the multiple-choice selection, leaving the gamers with only one correct and one incorrect option. This means they have a 50/50 chance.\r\n"
							+ "\r\n"
							+ "Ask the Public � the public is asked the same question as the gamer and a quick poll is done to show the answers. If the chart shows a clear majority for a specific answer, this joker can be extremely helpful, but it�s still up to the contestant whether or not to go with the results obtained from the public.\r\n"
							+ "\r\n"
							+ "Phone a Friend � gamers are allowed to make a call to a friend . Here the friend will give everytime an answer, good or false.\n");

			lblText.setId("rulesText");

		}

		return lblText;
	}

	/*
	 * If null instantiates btnPrevious, sets it's action when clicking on it and
	 * then returns it.
	 * 
	 * @return btnHome, a Button object which is the button that bring the user back
	 * to the home pane.
	 */
	public Button getBtnHome() {
		if (btnHome == null) {
			btnHome = new Button("Previous");
			btnHome.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					setVisible(false);
					((ProjSP) getParent().getParent()).getHomeGridPane().setVisible(true);

				}
			});
		}
		return btnHome;
	}

	public ScrollPane getScpRule() {
		if (scpRule == null) {
			scpRule = new ScrollPane();
			scpRule.setPadding(new Insets(5));
			scpRule.setFitToWidth(true);
			scpRule.setContent(getLblText());

			scpRule.setId("rulesPane");
		}
		return scpRule;
	}

}
