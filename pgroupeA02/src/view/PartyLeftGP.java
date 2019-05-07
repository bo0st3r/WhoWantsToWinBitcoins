package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class PartyLeftGP extends GridPane {

	private Label lblTitle;
	private Label lblEarning;
	private Button btnOk;

	/**
	 * Constructor, set cols and rows constraints plus add the pane contents.
	 */
	public PartyLeftGP() {
//		setGridLinesVisible(true);

		// Set columns
		ColumnConstraints c = new ColumnConstraints();
		c.setPercentWidth(20);
		getColumnConstraints().addAll(c, c, c, c, c);

		// Set rows
		RowConstraints r = new RowConstraints();
		r.setPercentHeight(20);
		getRowConstraints().addAll(r, r, r, r, r);

		add(getLblTitle(), 0, 0, 5, 2);
		add(getLblEarning(), 1, 3, 3, 1);
		add(getBtnOk(), 1, 4, 3, 1);
	}

	/**
	 * Returns lblGain and if null instantiates it plus defines it's ID.
	 */
	public Label getLblEarning() {
		if (lblEarning == null) {
			lblEarning = new Label("You won : ");
			lblEarning.getStyleClass().add("title-small");
			GridPane.setHalignment(lblEarning, HPos.CENTER);
		}
		return lblEarning;
	}

	public void setLblEarningText(double earnings) {
		getLblEarning().setText("You won : " + earnings + " BTC !");
	}

	public Button getBtnOk() {
		if (btnOk == null) {
			btnOk = new Button("Go home");
			btnOk.getStyleClass().add("button-medium");
			GridPane.setHalignment(btnOk, HPos.CENTER);
			btnOk.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// Returns to the home pane
					((PartySP) getParent().getParent()).returnToHome();
				}
			});
		}
		return btnOk;
	}

	public Label getLblTitle() {
		if (lblTitle == null) {
			lblTitle = new Label("You have chosen safety.");
			lblTitle.getStyleClass().add("title-medium");
			lblTitle.setStyle("-fx-text-fill:#29c570");
			GridPane.setHalignment(lblTitle, HPos.CENTER);
			GridPane.setValignment(lblTitle, VPos.BOTTOM);
		}
		return lblTitle;
	}

}
