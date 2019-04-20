package view;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class EndedPartyGridPane extends GridPane {

	private Label lblGain;
	private ImageView imgEndParty;

	/*
	 * Constructor, set cols and rows constraints plus add the pane contents.
	 */
	public EndedPartyGridPane() {
		// Set columns
		ColumnConstraints c = new ColumnConstraints();
		c.setPercentWidth(20);
		getColumnConstraints().addAll(c, c, c, c, c, c);

		// Set rows
		RowConstraints r = new RowConstraints();
		r.setPercentHeight(20);
		getRowConstraints().addAll(r, r, r, r, r, r);

		add(getImgEndParty(), 0, 0);
	}

	/*
	 * Returns lblGain and if null instantiates it plus defines it's ID.
	 */
	public Label lblGain() {
		if (lblGain == null) {
			lblGain = new Label("Votre gain est de : ");
			lblGain.setId("lblGain");
		}
		return lblGain;
	}

	/*
	 * Returns imgEndParty and if null instantiates it.
	 */
	public ImageView getImgEndParty() {
		if (imgEndParty == null) {
			imgEndParty = new ImageView("victory.png");
		}
		return imgEndParty;
	}
}
