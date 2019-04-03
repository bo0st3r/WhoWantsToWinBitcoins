package view;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class EndedPartyGridPane  extends GridPane{
	
	private Label lblGain;
	private ImageView imgEndParty;
	
	public EndedPartyGridPane() {
		// Set columns
		ColumnConstraints c = new ColumnConstraints();
		c.setPercentWidth(20);
		this.getColumnConstraints().addAll(c, c, c, c, c, c);

		// Set rows
		RowConstraints r = new RowConstraints();
		r.setPercentHeight(20);
		this.getRowConstraints().addAll(r, r, r, r, r, r);
		
		//ID
		getLblGain().setId("lblGain");
		
		this.add(getImgEndParty(),0,0);
		
		
	}

	public Label getLblGain() {
		if(lblGain == null) {
			lblGain = new Label("Votre gain est de : ");
		}
		return lblGain;
	}

	public ImageView getImgEndParty() {
		if(imgEndParty == null) {
			imgEndParty = new ImageView("victory.png");
		}
		return imgEndParty;
	}
}
