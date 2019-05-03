package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class PartyLeftGridPane extends GridPane {

	private Label lblTitle;
	private Label lblEarning;
	private ImageView imgLeaveParty;
	private Button btnOk;

	/**
	 * Constructor, set cols and rows constraints plus add the pane contents.
	 */
	public PartyLeftGridPane() {
		// Set columns
		ColumnConstraints c = new ColumnConstraints();
		c.setPercentWidth(20);
		getColumnConstraints().addAll(c, c, c, c, c, c);

		// Set rows
		RowConstraints r = new RowConstraints();
		r.setPercentHeight(20);
		getRowConstraints().addAll(r, r, r, r, r, r);

		add(getLblEarning(), 2, 4, 3, 1);
		add(getBtnOk(), 2, 5, 2, 1);
		add(getLblTitle(), 3, 1, 3, 2);
	}

	/**
	 * Returns lblGain and if null instantiates it plus defines it's ID.
	 */
	public Label getLblEarning() {
		if (lblEarning == null) {
			lblEarning = new Label("You won : ");

		}
		return lblEarning;
	}

	/**
	 * Returns imgLeaveParty and if null instantiates it.
	 */
	public ImageView getImgLeaveParty() {
		if (imgLeaveParty == null) {
			imgLeaveParty = new ImageView("victory.png"); // A CHANGER
		}
		return imgLeaveParty;
	}

	public Button getBtnOk() {
		if (btnOk == null) {
			btnOk = new Button("Ok");
			btnOk.setId("okButton");
			btnOk.setPrefWidth(Integer.MAX_VALUE);
			btnOk.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// Returns to the home pane
					((PartyStackPane) getParent().getParent()).returnToHome();
				}
			});
		}
		return btnOk;
	}

	public Label getLblTitle() {
		if (lblTitle == null) {
			lblTitle = new Label("You have chosen safety.\nGood game !");
		}
		return lblTitle;
	}

}
