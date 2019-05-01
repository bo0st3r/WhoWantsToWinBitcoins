package view;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class PartyStackPane extends BorderPane {

	private StackPane stackPane;
	private PlayingGridPane playingGridPane;
	private PartyWonGridPane partyWonGridPane;
	private PartyLostGridPane partyLostGridPane;
	private LeavePartyGridPane leavePartyGridPane;
	


	/**
	 * Constructor, set PlayingGridPane visible at first, hide the others panes and set
	 * stackPane as the center.
	 */
	public PartyStackPane() {
		
		setId("partyStackPane");
		// Center the pane
		setCenter(getStackPane());

		hideAllComponentsInStackPane();
	}

	/**
	 * Hides each component in stackPane.
	 */
	public void hideAllComponentsInStackPane() {
		for (Node node : getStackPane().getChildren()) {
			node.setVisible(false);
		}
	}

	/**
	 * Reset the playingGridPane in order to run a new party.
	 */
	public void resetPlayingGridPane() {
		// Removes the playingGridPane from stackPane
		stackPane.getChildren().remove(playingGridPane);

		// Sets playingGridPane as null so getPlayingGridPane() instantiate a new one.
		playingGridPane = null;
		stackPane.getChildren().add(getPlayingGridPane());
		getPlayingGridPane().setVisible(true);

	}

	// Getters
	/**
	 * If null, instantiates stackPane and adds panes Playing, PartyLost, PartyWon and LeaveParty to it's
	 * childrens and then returns it.
	 * 
	 * @return stackPane, a StackPane object which contains the others main panes.
	 */
	public StackPane getStackPane() {
		if (stackPane == null) {
			stackPane = new StackPane();
			stackPane.getChildren().addAll(
					
					getPartyLostGridPane()
					,getPartyWonGridPane()
					,getLeavePartyGridPane()
					);
		}
		return stackPane;
	}

	/**
	 * If null, instantiates playingGridPane, set it's CSS ID and then returns it.
	 * 
	 * @return playingGridPane, a PlayingGridPane object which is the playing page.
	 */
	public PlayingGridPane getPlayingGridPane() {
		if (playingGridPane == null) {
			playingGridPane = new PlayingGridPane();
			playingGridPane.setId("playingGridPane");
		}
		return playingGridPane;
	}
	/**
	 * If null, instantiates partyWonGridPane and  returns it.
	 * 
	 * @return partyWonGridPane, a PartyWonGridPane object which is the win page.
	 */
	public PartyWonGridPane getPartyWonGridPane() {
		if (partyWonGridPane==null) {
			partyWonGridPane = new PartyWonGridPane();
			partyWonGridPane.setId("wonGridPane");
		}
		return partyWonGridPane;
	}

	public PartyLostGridPane getPartyLostGridPane() {
		if (partyLostGridPane==null) {
			partyLostGridPane = new PartyLostGridPane();
			partyLostGridPane.setId("lostGridPane");
		}
		return partyLostGridPane;
	}

	public LeavePartyGridPane getLeavePartyGridPane() {
		if (leavePartyGridPane==null) {
			leavePartyGridPane = new LeavePartyGridPane();
			leavePartyGridPane.setId("leaveGridPane");
		}
		return leavePartyGridPane;
	}
	

}
