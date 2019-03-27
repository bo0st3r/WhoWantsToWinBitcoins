package view;

import exceptions.DeckUnderFilledException;
import exceptions.ExceedMaxStepsException;
import exceptions.NotEnoughQuestionsException;
import exceptions.QuestionsListIsEmptyException;
import exceptions.TooMuchQuestionsException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HomeGridPane extends GridPane {

	// General
	private Button btnPlay;
	private Button btnScoreBoard;
	private Button btnRules;
	private Button btnAbout;
	private Button btnConnect;
	private Label lblTitle;

	// Admin button
	private Button btnAddQuestion;
	private int admin = 1;

	// While connected buttons
	private Button btnProfile;
	private Button btnDisconnect;

//	private About about;

	public HomeGridPane() {
//		this.setGridLinesVisible(true);

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

		// title
		this.add(getLblTitle(), 2, 1, 6, 2);
		GridPane.setHalignment(getLblTitle(), HPos.CENTER);
		getLblTitle().setId("titleHome");

		// Play button
		getBtnPlay().setPrefWidth(Integer.MAX_VALUE);
		getBtnPlay().setPrefHeight(Integer.MAX_VALUE);
		this.add(getBtnPlay(), 3, 4, 4, 2);
		GridPane.setHalignment(getBtnPlay(), HPos.CENTER);
		getBtnPlay().setId("playBtn");

		// Score button
		getBtnScoreBoard().setPrefHeight(Integer.MAX_VALUE);
		getBtnScoreBoard().setPrefWidth(Integer.MAX_VALUE);
		this.add(getBtnScoreBoard(), 4, 6, 2, 1);
		getBtnScoreBoard().setId("classicBtn");

		// Rule button
		getBtnRules().setPrefHeight(Integer.MAX_VALUE);
		getBtnRules().setPrefWidth(Integer.MAX_VALUE);
		this.add(getBtnRules(), 4, 7, 2, 1);
		getBtnRules().setId("classicBtn");

		// AddQuestion button
		getBtnAddQuestion().setPrefHeight(Integer.MAX_VALUE);
		getBtnAddQuestion().setPrefWidth(Integer.MAX_VALUE);
		this.add(getBtnAddQuestion(), 4, 8, 2, 1);
		getBtnAddQuestion().setId("classicBtn");
		// hidden if not admin
		getBtnAddQuestion().setVisible(false);

		// Connection button
		this.add(getBtnConnect(), 9, 0, 2, 1);
		GridPane.setHalignment(getBtnConnect(), HPos.RIGHT);
		getBtnConnect().setId("profile");

		// Profile button
		this.add(getBtnProfile(), 8, 0, 2, 1);
		GridPane.setHalignment(getBtnProfile(), HPos.RIGHT);
		getBtnProfile().setId("profile");
		// hidden if not connected
		getBtnProfile().setVisible(false);

		// Disconnection button
		this.add(getBtnDisconnect(), 8, 1, 2, 1);
		GridPane.setHalignment(getBtnDisconnect(), HPos.RIGHT);
		GridPane.setValignment(getBtnDisconnect(), VPos.TOP);
		getBtnDisconnect().setId("profile");
		// hidden if not connected
		getBtnDisconnect().setVisible(false);

		// About button
		this.add(getBtnAbout(), 0, 9);
		getBtnAbout().setId("about");
	}

	// Getters
	public Button getBtnPlay() {
		if (btnPlay == null) {
			btnPlay = new Button("Let's play");

			btnPlay.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					((ProjStackPane) getParent().getParent()).resetPlayingGridPane();
					try {
						((ProjStackPane) getParent().getParent()).getPlayingGridPane().getTimerFlowPane().runChrono();
						((ProjStackPane) getParent().getParent()).getPlayingGridPane().runNewParty("main_deck");
					} catch (QuestionsListIsEmptyException | DeckUnderFilledException | NotEnoughQuestionsException
							| TooMuchQuestionsException | ExceedMaxStepsException e) {
						e.printStackTrace();
					}
					setVisible(false);
					((ProjStackPane) getParent().getParent()).getPlayingGridPane().setVisible(true);
				}
			});
		}

		return btnPlay;
	}

	public Button getBtnScoreBoard() {
		if (btnScoreBoard == null) {
			btnScoreBoard = new Button("Scores board");
			btnScoreBoard.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// hideAllComponent();
					// getBtnPrevious().setVisible(true);

				}
			});
		}
		return btnScoreBoard;
	}

	public Button getBtnRules() {
		if (btnRules == null)
			btnRules = new Button("Read rules");
			btnRules.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					setVisible(false);
					((ProjStackPane) getParent().getParent()).getRulesGridPane().setVisible(true);

				}
			});
		return btnRules;
	}

	public Button getBtnAddQuestion() {
		if (btnAddQuestion == null) {
			btnAddQuestion = new Button("Add a question");

			Scene secondScene = new Scene(AddQuestionGridPane.getSingleton(), 570, 305);
			btnAddQuestion.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// Gets CSS file from the main scene
					secondScene.getStylesheets().addAll(getScene().getStylesheets());
					Stage stage = new Stage();
					stage.setTitle("Add a question");
					stage.setScene(secondScene);
					// Set the main Stage as it's owner
					stage.initOwner(getScene().getWindow());
					// Disable from acting on the owner stage while this window's open
					stage.initModality(Modality.WINDOW_MODAL);
					stage.show();
				}
			});
		}
		return btnAddQuestion;
	}

	public Button getBtnAbout() {
		if (btnAbout == null) {
			btnAbout = new Button("About...");
			btnAbout.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					setVisible(false);
					((ProjStackPane) getParent().getParent()).getAboutGridPane().setVisible(true);

				}
			});

		}
		return btnAbout;
	}

	public Button getBtnConnect() {
		if (btnConnect == null) {
			btnConnect = new Button("Connection");
			btnConnect.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					getBtnConnect().setVisible(false);
					getBtnDisconnect().setVisible(true);
					getBtnProfile().setVisible(true);
					if (admin == 1)
						getBtnAddQuestion().setVisible(true);

				}
			});
		}
		return btnConnect;
	}

	public Button getBtnProfile() {
		if (btnProfile == null)
			btnProfile = new Button("Profile");
		return btnProfile;
	}

	public Button getBtnDisconnect() {
		if (btnDisconnect == null) {
			btnDisconnect = new Button("Disconnection");
			btnDisconnect.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					getBtnConnect().setVisible(true);
					getBtnDisconnect().setVisible(false);
					getBtnProfile().setVisible(false);
					getBtnAddQuestion().setVisible(false);
				}
			});
		}

		return btnDisconnect;
	}

	public Label getLblTitle() {
		if (lblTitle == null)
			lblTitle = new Label("Who wants to win Bitcoins?");
		return lblTitle;
	}

}