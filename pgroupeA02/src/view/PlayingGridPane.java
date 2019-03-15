package view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.util.converter.PercentageStringConverter;

public class PlayingGridPane extends GridPane{
	
	private Label lblStatement;
	
	private Button btnA;
	private Button btnB;
	private Button btnC;
	private Button btnD;
	
	private Button btnExit;
	private Button btnFriends;
	private Button btnPublic;
	private Button btn5050;
	
	public PlayingGridPane() {
		
		//add columns
		ColumnConstraints c = new ColumnConstraints();
		c.setPercentWidth(10);
		this.getColumnConstraints().addAll(c,c,c,c,c,c,c,c,c,c,c);
				
		//add rows
		RowConstraints r = new RowConstraints();
		r.setPercentHeight(10);
		this.getRowConstraints().addAll(r,r,r,r,r,r,r,r,r,r,r);
		
		this.setPadding(new Insets(10));
		this.setHgap(5); // Espacement horizontal des composants
		this.setVgap(5);
		
		this.setGridLinesVisible(true);
		
		this.add(getLblStatement(), 1, 6, 8, 2);
		this.add(getBtnA(), 1, 8, 4, 1);
		this.add(getBtnB(), 1, 9, 4, 1);
		this.add(getBtnC(), 5, 8, 4, 1);
		this.add(getBtnD(), 5, 9, 4, 1);
		
		//Id
		getLblStatement().setId("IdQuestion");
		getBtnA().setId("AnswerA");
		getBtnB().setId("AnswerB");
		getBtnC().setId("AnswerC");
		getBtnD().setId("AnswerD");
		
		getLblStatement().setPrefHeight(Integer.MAX_VALUE);
		getLblStatement().setPrefWidth(Integer.MAX_VALUE);
		
		getBtnA().setPrefWidth(Integer.MAX_VALUE);
		getBtnA().setPrefHeight(Integer.MAX_VALUE);
		
		getBtnB().setPrefWidth(Integer.MAX_VALUE);
		getBtnB().setPrefHeight(Integer.MAX_VALUE);
		
		getBtnC().setPrefWidth(Integer.MAX_VALUE);
		getBtnC().setPrefHeight(Integer.MAX_VALUE);
		
		getBtnD().setPrefWidth(Integer.MAX_VALUE);
		getBtnD().setPrefHeight(Integer.MAX_VALUE);
		
	}

	public Label getLblStatement() {
		if(lblStatement == null) {
			lblStatement = new Label("Question");
			

		}
		return lblStatement;
	}

	public Button getBtnA() {
		if(btnA == null) {
			btnA = new Button("Réponse A");
		}
		return btnA;
	}

	public Button getBtnB() {
		if(btnB == null) {
			btnB = new Button("Réponse B");
		}
		return btnB;
	}

	public Button getBtnC() {
		if(btnC == null) {
			btnC = new Button("Réponse C");
		}
		return btnC;
	}

	public Button getBtnD() {
		if(btnD == null) {
			btnD = new Button("Réponse D");
		}
		return btnD;
	}

	public Button getBtnExit() {
		if(btnExit==null) {
			btnExit = new Button("Exit");
			
		}
		return btnExit;
	}

	public Button getBtnFriends() {
		return btnFriends;
	}

	public Button getBtnPublic() {
		return btnPublic;
	}

	public Button getBtn5050() {
		return btn5050;
	}
	
	
	
}
