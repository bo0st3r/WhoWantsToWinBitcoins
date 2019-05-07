package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class PartyLostGP extends GridPane {

	private Label lblTitle;
	private Label lblEarning;
	private ImageView imgLostParty;
	private Button btnOk;

	/**
	 * Constructor, set cols and rows constraints plus add the pane contents.
	 */
	public PartyLostGP() {
//		setGridLinesVisible(true);

		// Set columns
		ColumnConstraints c = new ColumnConstraints();
		c.setPercentWidth(20);
		getColumnConstraints().addAll(c, c, c, c, c);

		// Set rows
		RowConstraints r = new RowConstraints();
		r.setPercentHeight(20);
		getRowConstraints().addAll(r, r, r, r, r);

		add(getLblTitle(), 1, 0, 3, 2);
		add(getLblEarning(), 1, 3, 3, 1);
		add(getBtnOk(), 1, 4, 3, 1);

	}

	/**
	 * Returns lblEarning and if null instantiates it plus defines it's ID.
	 */
	public Label getLblEarning() {
		if (lblEarning == null) {
			lblEarning = new Label("You won : ");
			lblEarning.setId("lblGain");
			lblEarning.getStyleClass().add("title-small");
			GridPane.setHalignment(lblEarning, HPos.CENTER);
		}
		return lblEarning;
	}

	/**
	 * Returns imgEndParty and if null instantiates it.
	 */
	public ImageView getImgLostParty() {
		if (imgLostParty == null) {
			imgLostParty = new ImageView("victory.png"); // A CHANGER
		}
		return imgLostParty;
	}

	/**
	 * If null instantiate btnOk, then return it. Define it's action when using it.
	 * 
	 * @return The Ok Button
	 */
	public Button getBtnOk() {
		if (btnOk == null) {
			btnOk = new Button("Go home");
			btnOk.setId("okButton");
			btnOk.getStyleClass().add("button-medium");
			GridPane.setHalignment(btnOk, HPos.CENTER);
//			btnOk.setPrefWidth(Integer.MAX_VALUE);

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
			lblTitle = new Label("You lost... :(");
			lblTitle.getStyleClass().add("title-medium");
			lblTitle.setStyle("-fx-text-fill:#d82811;");
			GridPane.setHalignment(lblTitle, HPos.CENTER);
			GridPane.setValignment(lblTitle, VPos.BOTTOM);
		}
		return lblTitle;
	}

	public void setLblEarningText(double earnings) {
		if (earnings > 0) {
			getLblEarning().setText("But you won : " + earnings + " BTC !");
		} else {
			getLblEarning().setText("You won nothing.");
		}
	}

}
