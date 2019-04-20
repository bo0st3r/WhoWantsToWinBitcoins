package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class RulesGridPane extends GridPane {

	private Label lblTitle;
	private Label lblText;
	private Button btnHome;

	/*
	 * Constructor. Set the spacings, cols and rows constraints and define the pane
	 * content.
	 */
	public RulesGridPane() {
		// this.setGridLinesVisible(true);

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
		getRowConstraints().addAll(r, r, r, r, r, r, r, r, r, r);

		// Title
		add(getLblTitle(), 2, 1, 6, 2);

		// Text
		add(getLblText(), 2, 2, 6, 6);

		// Previous button
		add(getBtnHome(), 4, 8, 2, 2);
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
			lblText = new Label("Test rules " + "\n\nMore rules" + "\n\nMore more rules" + "\n\nMore rules"
					+ "\n\nMore more rules" + "\n\nMore rules");
			lblText.setId("rules");
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
					((ProjStackPane) getParent().getParent()).getHomeGridPane().setVisible(true);

				}
			});
		}
		return btnHome;
	}

}
