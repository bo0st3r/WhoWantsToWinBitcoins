package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PlayingGridPane extends GridPane {

	private Label lblStatement;

	private Button btnAnswer[];

	public void setBtnAnswer(Button[] btnAnswer) {
		this.btnAnswer = btnAnswer;
	}

	private Button btnExit;
	private Button btnFriends;
	private Button btnPublic;
	private Button btn5050;

	public PlayingGridPane() {

		// add columns
		ColumnConstraints c = new ColumnConstraints();
		c.setPercentWidth(10);
		this.getColumnConstraints().addAll(c, c, c, c, c, c, c, c, c, c, c);

		// add rows
		RowConstraints r = new RowConstraints();
		r.setPercentHeight(10);
		this.getRowConstraints().addAll(r, r, r, r, r, r, r, r, r, r, r);

		this.setPadding(new Insets(10));
		this.setHgap(5); // Espacement horizontal des composants
		this.setVgap(5);

		this.setGridLinesVisible(true);

		this.add(getLblStatement(), 1, 6, 8, 2);

		this.add(getBtnAnswer(0), 1, 8, 4, 1);
		this.add(getBtnAnswer(1), 1, 9, 4, 1);
		this.add(getBtnAnswer(2), 5, 8, 4, 1);
		this.add(getBtnAnswer(3), 5, 9, 4, 1);
		
		for(int i=0; i<=3; i++) {	
			//ID
			getBtnAnswer(i).setId("answers");
		}
		

		// Id
		getLblStatement().setId("idQuestion");

		getLblStatement().setPrefHeight(Integer.MAX_VALUE);
		getLblStatement().setPrefWidth(Integer.MAX_VALUE);
		
		for(int i=0; i<=3; i++) {
			getBtnAnswer(i).setPrefWidth(Integer.MAX_VALUE);
			getBtnAnswer(i).setPrefHeight(Integer.MAX_VALUE);
		}
	}
	
	public Label getLblStatement() {
		if (lblStatement == null) {
			lblStatement = new Label("Question");

		}
		return lblStatement;
	}
	
	public Button getBtnAnswer(int index) {
		if(btnAnswer == null) {
			btnAnswer = new Button[4];
			for(int i=0; i<=3; i++) {
				btnAnswer[i] = new Button("Answer");
			}
			
			Scene secondScene = new Scene(new ValidationGridPane(), 450, 180);
			
			for(int i=0; i<=3; i++) {
				btnAnswer[i].setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						secondScene.getStylesheets().addAll(getScene().getStylesheets());
						Stage secondstage = new Stage();
						secondstage.setTitle("Validation");
						secondstage.setScene(secondScene);
						// Set the main Stage as it's owner
						secondstage.initOwner(getScene().getWindow());
						// Disable from acting on the owner stage while this window's open
						secondstage.initModality(Modality.WINDOW_MODAL);
						secondstage.show();
					}
					
				});
			}
		}
		return btnAnswer[index];
	}
	
	public Button getBtnExit() {
		if (btnExit == null) {
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
