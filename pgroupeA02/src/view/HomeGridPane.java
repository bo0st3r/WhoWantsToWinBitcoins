package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;


public class HomeGridPane extends GridPane {
	
	//general 
	private Button btnPlay;
	private Button btnScore;
	private Button btnRules;
	private Button btnAbout;
	private Button btnConnect;
	private Label lblTitle;
	//admin buttons
	private Button btnAddQuestion;
	//connected buttons
	private Button btnProfile;
	private Button btnDisconnect;
	private int admin;
	
//	private About about;

	

	public HomeGridPane() {
		admin = 1;
		
		this.setGridLinesVisible(true);
		
		this.setPadding(new Insets(10));
		this.setHgap(8);
		this.setVgap(8);
		
		
		//add columns
		ColumnConstraints c = new ColumnConstraints();
		c.setPercentWidth(10);
		this.getColumnConstraints().addAll(c,c,c,c,c,c,c,c,c,c);
		
		
		//add rows
		RowConstraints r = new RowConstraints();
		r.setPercentHeight(10);
		this.getRowConstraints().addAll(r,r,r,r,r,r,r,r,r,r);
		
		
		
		//title
		this.add(getLblTitle(), 2, 1, 6, 2);
		GridPane.setHalignment(getLblTitle(), HPos.CENTER);
		getLblTitle().setId("Title");
		
		//Connection button
		this.add(getBtnConnect(), 9, 0, 2, 1);
		GridPane.setHalignment(getBtnConnect(), HPos.RIGHT);
		getBtnConnect().setId("Profile");
		
		//Profile button
		this.add(getBtnProfile(), 8, 0, 2, 1);
		GridPane.setHalignment(getBtnProfile(), HPos.RIGHT);
		GridPane.setValignment(getBtnProfile(), VPos.BOTTOM);
		getBtnProfile().setId("Profile");
		// hidden if not connected
		getBtnProfile().setVisible(false);
				
		//Disconnection button
		this.add(getBtnDisconnect(), 8, 1, 2, 1);
		GridPane.setHalignment(getBtnDisconnect(), HPos.RIGHT);
		GridPane.setValignment(getBtnDisconnect(), VPos.TOP);
		getBtnDisconnect().setId("Profile");
		// hidden if not connected
		getBtnDisconnect().setVisible(false);
		
		//Play button 
		getBtnPlay().setPrefWidth(Integer.MAX_VALUE);
		getBtnPlay().setPrefHeight(Integer.MAX_VALUE);
		this.add(getBtnPlay(), 3, 4, 4, 2);
		GridPane.setHalignment(getBtnPlay(), HPos.CENTER);
		getBtnPlay().setId("Play");
		
		//Score button 
		getBtnScore().setPrefHeight(Integer.MAX_VALUE);
		getBtnScore().setPrefWidth(Integer.MAX_VALUE);
		this.add(getBtnScore(), 4, 6, 2, 1);
		getBtnScore().setId("otherButton");
		
		//Rule button
		getBtnRules().setPrefHeight(Integer.MAX_VALUE);
		getBtnRules().setPrefWidth(Integer.MAX_VALUE);
		this.add(getBtnRules(), 4, 7, 2, 1);
		getBtnRules().setId("otherButton");
		
		//AddQuestion button 
		getBtnAddQuestion().setPrefHeight(Integer.MAX_VALUE);
		getBtnAddQuestion().setPrefWidth(Integer.MAX_VALUE);
		this.add(getBtnAddQuestion(), 4, 8, 2, 1 );
		getBtnAddQuestion().setId("otherButton");
		// hidden if not admin
		getBtnAddQuestion().setVisible(false);
		
		//About button
		this.add(getBtnAbout(), 0, 9);
		getBtnAbout().setId("Profile");
		
		//Previous button
		//this.add(getBtnPrevious(), 4, 7);
		//hidden if on home
		//getBtnPrevious().setVisible(false);
		
	}
		
//	private void hideAllComponent() {
//		for(Node node :getStackPane().getChildren()) {
//			node.setVisible(false);
//		}
//	}
	
	//getters

	public Button getBtnPlay() {
		if(btnPlay==null)
			btnPlay = new Button("Play");
			btnPlay.setOnAction(new EventHandler<ActionEvent>() {
				
					@Override
					public void handle(ActionEvent event) {
						
						setVisible(false);
						((ProjStackPane)getParent().getParent()).getPlayingGridPane().setVisible(true);
					}
			});
		return btnPlay;
	}

	public Button getBtnScore() {
		if (btnScore==null) {
			btnScore = new Button("Scores");
			btnScore.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					//hideAllComponent();
					//getBtnPrevious().setVisible(true);
					
				}
			});
		}
		return btnScore;
	}

	public Button getBtnRules() {
		if (btnRules==null)
			btnRules = new Button("Rules");
		return btnRules;
	}

	public Button getBtnAbout() {
		if (btnAbout==null) {
			btnAbout= new Button("About...");
			btnAbout.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					
//					setVisible(false);
//					((ProjStackPane)getParent().getParent()).getAboutBorderPane.setVisible(true);
					
				}
			});
			
		}
		return btnAbout;
	}

	public Button getBtnConnect() {
		if (btnConnect==null) {
			btnConnect = new Button("Connection");
			btnConnect.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					getBtnConnect().setVisible(false);
					getBtnDisconnect().setVisible(true);
					getBtnProfile().setVisible(true);
					if (admin==1)
						getBtnAddQuestion().setVisible(true);
					
				}
			});
		}
		return btnConnect;
	}
	public Button getBtnProfile() {
		if (btnProfile==null)
			btnProfile = new Button("Profile");
		return btnProfile;
	}


	public Button getBtnAddQuestion() {
		if (btnAddQuestion==null) {
			btnAddQuestion = new Button("Add a question");
			btnAddQuestion.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					//AddquestionGridpane
					
				}
			});
		}
		return btnAddQuestion;
	}


	public Button getBtnDisconnect() {
		if (btnDisconnect==null)
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
		return btnDisconnect;
	}

	public Label getLblTitle() {
		if (lblTitle==null)
			lblTitle = new Label("Who wants to win Bitcoins?");
		return lblTitle;
	}

}