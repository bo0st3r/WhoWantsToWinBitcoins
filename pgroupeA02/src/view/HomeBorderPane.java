package view;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class HomeBorderPane extends BorderPane{
	
	private Button btnPlay;
	private Button btnScore;
	private Button btnRules;
	private Button btnAbout;
	
	public HomeBorderPane() {
		
		
	
	}

	public Button getBtnPlay() {
		if(btnPlay==null)
			btnPlay = new Button("Play");
		return btnPlay;
	}

	public Button getBtnScore() {
		if (btnScore==null)
			btnScore = new Button("Scores");
		return btnScore;
	}

	public Button getBtnRules() {
		if (btnRules==null)
			btnRules = new Button("Rules");
		return btnRules;
	}

	public Button getBtnAbout() {
		if (btnAbout==null)
			btnAbout= new Button("About");
		return btnAbout;
	}
	

}
