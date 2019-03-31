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
	private Button btnPrevious;

	public RulesGridPane() {
		// this.setGridLinesVisible(true);

		this.setPadding(new Insets(10));
		this.setHgap(8);
		this.setVgap(8);

		// add columns
		ColumnConstraints c = new ColumnConstraints();
		c.setPercentWidth(10);
		this.getColumnConstraints().addAll(c, c, c, c, c, c, c, c, c, c);

		// add rows
		RowConstraints r = new RowConstraints();
		r.setPercentHeight(10);
		this.getRowConstraints().addAll(r, r, r, r, r, r, r, r, r, r);

		this.setPadding(new Insets(10));
		this.setHgap(8);
		this.setVgap(8);

		// title
		this.add(getLblTitle(), 2, 1, 6, 2);
		GridPane.setHalignment(getLblTitle(), HPos.CENTER);
		getLblTitle().setId("titleHome");

		// text
		this.add(getLblText(), 2, 2, 6, 6);

		// Previous button
		this.add(getBtnPrevious(), 4, 8, 2, 2);
	}

	public Label getLblTitle() {
		if (lblTitle == null) {
			lblTitle = new Label("Game rules");
		}
		return lblTitle;
	}

	public Label getLblText() {
		if (lblText == null) {

			lblText = new Label("Test rules " + "\n\nMore rules" + "\n\nMore more rules" + "\n\nMore rules"
					+ "\n\nMore more rules" + "\n\nMore rules");
		}
		this.setId("rules");

		return lblText;
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

}
