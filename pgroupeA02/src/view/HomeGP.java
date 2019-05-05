package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Deck;
import utilities.Serialization;
import view.tableviews.TableViewQuestionsBP;

public class HomeGP extends GridPane {
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

	/*
	 * Constructor, sets spacings, rows and cols constraints and adds it's content.
	 */
	public HomeGP() {
//		this.setGridLinesVisible(true);

		// Spacings
		setPadding(new Insets(8));
		setHgap(6);
		setVgap(6);

		// Set columns constraints
		ColumnConstraints c = new ColumnConstraints();
		c.setPercentWidth(10);
		getColumnConstraints().addAll(c, c, c, c, c, c, c, c, c, c);

		// Set rows constraints
		RowConstraints r = new RowConstraints();
		r.setPercentHeight(10);
		getRowConstraints().addAll(r, r, r, r, r, r, r, r, r, r);

		// Title
		add(getLblTitle(), 1, 1, 8, 2);

		// Play button
		add(getBtnPlay(), 3, 4, 4, 2);

		// Score button
		add(getBtnScoreBoard(), 4, 6, 2, 1);

		// Rule button
		add(getBtnRules(), 4, 7, 2, 1);

		// AddQuestion button
		add(getBtnAddQuestion(), 4, 8, 2, 1);

		// Connection button
		add(getBtnConnect(), 9, 0, 2, 1);

		// Profile button
		add(getBtnProfile(), 8, 0, 2, 1);

		// Disconnection button
		add(getBtnDisconnect(), 8, 1, 2, 1);

		// About button
		add(getBtnAbout(), 0, 9);
	}

	// Getters
	/**
	 * Returns lblTitle, if null instantiates it, sets it's ID and alignment.
	 * 
	 * @return lblTitle, a Label object.
	 */
	public Label getLblTitle() {
		if (lblTitle == null) {
			lblTitle = new Label("Who wants to win Bitcoins?");
			GridPane.setHalignment(lblTitle, HPos.CENTER);
			lblTitle.getStyleClass().add("title-xl");
			lblTitle.setId("home-title");
		}

		return lblTitle;
	}

	/**
	 * Returns btnPlay, if null instantiates it, sets it's sizes, alignment, ID and
	 * action when clicking on it.
	 * 
	 * @return btnPlay, a Button object.
	 */
	public Button getBtnPlay() {
		if (btnPlay == null) {
			btnPlay = new Button("Let's play");
			btnPlay.setId("playBtn");
			btnPlay.setPrefWidth(Integer.MAX_VALUE);
			btnPlay.setPrefHeight(Integer.MAX_VALUE);
			GridPane.setHalignment(btnPlay, HPos.CENTER);

			btnPlay.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// Resets the playing pane
					((ProjSP) getParent().getParent()).getPartyStackPane().resetPlayingGridPane();

					// Hides the Home Pane
					setVisible(false);

					// Shows the PartyStackPane
					((ProjSP) getParent().getParent()).getPartyStackPane().setVisible(true);
				}
			});
		}

		return btnPlay;
	}

	/**
	 * Returns btnScoreBoard, if null instantiates it, sets it's sizes and action
	 * when clicking on it.
	 * 
	 * @return btnScoreBoard, a Button object.
	 */
	public Button getBtnScoreBoard() {
		if (btnScoreBoard == null) {
			btnScoreBoard = new Button("Scores board");
			btnScoreBoard.setPrefHeight(Integer.MAX_VALUE);
			btnScoreBoard.setPrefWidth(Integer.MAX_VALUE);
			btnScoreBoard.getStyleClass().add("main-button-md");

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

	/**
	 * Returns btnRules, if null instantiates it, sets it's sizes and action when
	 * clicking on it.
	 * 
	 * @return btnRules, a Button object.
	 */
	public Button getBtnRules() {
		if (btnRules == null) {
			btnRules = new Button("Read rules");
			btnRules.setPrefHeight(Integer.MAX_VALUE);
			btnRules.setPrefWidth(Integer.MAX_VALUE);
			btnRules.getStyleClass().add("main-button-md");

			btnRules.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					setVisible(false);
					((ProjSP) getParent().getParent()).getRulesGridPane().setVisible(true);

				}
			});
		}
		return btnRules;
	}

	/**
	 * Returns btnAddQuestion, if null instantiates it, sets it's sizes, action when
	 * clicking on it and invisible at first.
	 * 
	 * @return btnAddQuestion, a Button object.
	 */
	public Button getBtnAddQuestion() {
		if (btnAddQuestion == null) {
			btnAddQuestion = new Button("Add a question");
			btnAddQuestion.setPrefHeight(Integer.MAX_VALUE);
			btnAddQuestion.setPrefWidth(Integer.MAX_VALUE);
			// Hidden if the user is not an admin
//			btnAddQuestion.setVisible(false);

			Scene secondScene = new Scene(new AddQuestionGP(), 570, 305);
			btnAddQuestion.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// Gets CSS file from the main scene
//					secondScene.getStylesheets().addAll(getScene().getStylesheets());
//					Stage stage = new Stage();
//					stage.setTitle("Add a question");
//					stage.setScene(secondScene);
//					// Set the main Stage as it's owner
//					stage.initOwner(getScene().getWindow());
//					// Disable from acting on the owner stage while this window's open
//					stage.initModality(Modality.WINDOW_MODAL);
//					stage.show();

					setVisible(false);
					((ProjSP) getParent().getParent()).getTvQuestionBP().setVisible(true);
				}
			});
		}
		return btnAddQuestion;
	}

	/**
	 * Returns btnAbout, if null instantiates it, sets it's ID and action when
	 * clicking on it.
	 * 
	 * @return btnAbout, a Button object.
	 */
	public Button getBtnAbout() {
		if (btnAbout == null) {
			btnAbout = new Button("About...");
			btnAbout.setId("about");

			btnAbout.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					setVisible(false);
					((ProjSP) getParent().getParent()).getAboutGridPane().setVisible(true);

				}
			});

		}
		return btnAbout;
	}

	/**
	 * Returns btnConnect, if null instantiates it, sets it's ID, alignment and
	 * action when clicking on it.
	 * 
	 * @return btnConnect, a Button object.
	 */
	public Button getBtnConnect() {
		if (btnConnect == null) {
			btnConnect = new Button("Connection");
			GridPane.setHalignment(btnConnect, HPos.RIGHT);
			btnConnect.setId("profile");

			btnConnect.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					getBtnConnect().setVisible(false);
					getBtnDisconnect().setVisible(true);
					getBtnProfile().setVisible(true);
					if (admin == 1)
						getBtnAddQuestion().setVisible(true);
					setVisible(false);
					((ProjSP) getParent().getParent()).getRegistrationConnectionGridPane().setVisible(true);
				}
			});
		}
		return btnConnect;
	}

	/**
	 * Returns btnProfile, if null instantiates it, sets it's ID, alignment,
	 * invisible at first and action when clicking on it.
	 * 
	 * @return btnProfile, a Button object.
	 */
	public Button getBtnProfile() {
		if (btnProfile == null) {
			btnProfile = new Button("Profile");
			GridPane.setHalignment(btnProfile, HPos.RIGHT);
			btnProfile.setId("profile");

			// Hidden if not connected
			btnProfile.setVisible(false);
			btnProfile.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					setVisible(false);
					((ProjSP) getParent().getParent()).getProfilGridPane().setVisible(true);

				}
			});
		}

		return btnProfile;
	}

	/**
	 * Returns btnDisconnect, if null instantiates it, sets it's ID, alignment,
	 * invisible at first and action when clicking on it.
	 * 
	 * @return btnDisconnect, a Button object.
	 */
	public Button getBtnDisconnect() {
		if (btnDisconnect == null) {
			btnDisconnect = new Button("Disconnection");
			GridPane.setHalignment(btnDisconnect, HPos.RIGHT);
			GridPane.setValignment(btnDisconnect, VPos.TOP);
			btnDisconnect.setId("profile");

			// Hidden if not connected
			btnDisconnect.setVisible(false);

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

}