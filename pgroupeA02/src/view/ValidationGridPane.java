package view;

import exceptions.ExceedMaxStepsException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class ValidationGridPane extends GridPane {

	private Label lblValidation;
	private Button btnYes;
	private Button btnNo;

	public ValidationGridPane() {
		
		this.setId("validationPane");
		// Set columns
		ColumnConstraints c1 = new ColumnConstraints();
		c1.setPercentWidth(13);
		ColumnConstraints c2 = new ColumnConstraints();
		c2.setPercentWidth(30);
		this.getColumnConstraints().addAll(c1, c2, c1, c2, c1);

		// Set rows
		RowConstraints r = new RowConstraints();
		r.setPercentHeight(50);
		this.getRowConstraints().addAll(r, r);

		// Spacings
		this.setPadding(new Insets(10));
		this.setHgap(5);
		this.setVgap(5);

		// this.setAlignment(Pos.BASELINE_CENTER);
		// this.setGridLinesVisible(true);

		// IDs
		getLblValidation().setId("validationText");
		getBtnYes().setId("btnValidation");
		getBtnNo().setId("btnValidation");

		// Additions
		this.add(getLblValidation(), 0, 0, 5, 1);
		this.add(getBtnYes(), 1, 1, 1, 1);
		this.add(getBtnNo(), 3, 1, 1, 1);

		// Sizes
		getLblValidation().setPrefHeight(Integer.MAX_VALUE);
		getLblValidation().setPrefWidth(Integer.MAX_VALUE);

		getBtnYes().setPrefHeight(Integer.MAX_VALUE);
		getBtnYes().setPrefWidth(Integer.MAX_VALUE);

		getBtnNo().setPrefHeight(Integer.MAX_VALUE);
		getBtnNo().setPrefWidth(Integer.MAX_VALUE);
	}

	public Label getLblValidation() {
		if (lblValidation == null) {
			lblValidation = new Label("No doubt?");
		}
		return lblValidation;
	}

	public Button getBtnYes() {
		if (btnYes == null) {
			btnYes = new Button("Sure");
			btnYes.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					
					try {
						((PlayingGridPane) getParent()).verifyAnswer();
					} catch (ExceedMaxStepsException e) {
						e.printStackTrace();
					}
					int answer = ((PlayingGridPane) getParent()).getAnswerIndex();
					((PlayingGridPane) getParent()).getBtnAnswer(answer).setId("answers");

					
					setVisible(false);
					
				}
			});
		}
		return btnYes;
	}

	public Button getBtnNo() {
		if (btnNo == null) {
			btnNo = new Button("Nope");
			btnNo.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					setVisible(false);
					
					int answer = ((PlayingGridPane) getParent()).getAnswerIndex();
					((PlayingGridPane) getParent()).getBtnAnswer(answer).setId("answers");
					
				}

			});
		}
		return btnNo;
	}
}
