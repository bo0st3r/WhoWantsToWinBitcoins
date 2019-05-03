package view;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class ProjStackPane extends BorderPane {

	private StackPane stackPane;
	private HomeGridPane homeGridPane;
	// private PlayingGridPane playingGridPane;
	private AboutGridPane aboutGridPane;
	private RulesGridPane rulesGridPane;
	private RegistrationConnectionGridPane registrationConnectionGridPane;
	private PartyStackPane partyStackPane;

	/*
	 * Constructor, set homeGridPane visible at first, hide the others panes and set
	 * stackPane as the center.
	 */
	public ProjStackPane() {
		// Center the pane
		setCenter(getStackPane());

		hideAllComponentsInStackPane();

		// Set home visible first
		getHomeGridPane().setVisible(true);
	}

	/*
	 * Hides each component in stackPane.
	 */
	public void hideAllComponentsInStackPane() {
		for (Node node : getStackPane().getChildren()) {
			node.setVisible(false);
		}
	}

	/*
	 * Reset the playingGridPane in order to run a new party.
	 */
//	public void resetPlayingGridPane() {
//		// Removes the playingGridPane from stackPane
//		stackPane.getChildren().remove(playingGridPane);
//
//		// Sets playingGridPane as null so getPlayingGridPane() instantiate a new one.
//		playingGridPane = null;
//		stackPane.getChildren().add(getPlayingGridPane());
//
//		System.out.println(stackPane.getChildren().size());
//	}

	// Getters
	/*
	 * If null, instantiates stackPane and adds panes Home, About and Rules to it's
	 * childrens and then returns it.
	 * 
	 * @return stackPane, a StackPane object which contains the others main panes.
	 */
	public StackPane getStackPane() {
		if (stackPane == null) {
			stackPane = new StackPane();
			stackPane.getChildren().addAll(getHomeGridPane(), getAboutGridPane(), getRulesGridPane(),
					getRegistrationConnectionGridPane(), getPartyStackPane());
		}
		return stackPane;
	}

	/*
	 * If null, instantiate homeGridPane and then returns it.
	 * 
	 * @return homeGridPane, a HomeGridPane object which is the home page.
	 */
	public HomeGridPane getHomeGridPane() {
		if (homeGridPane == null) {
			homeGridPane = new HomeGridPane();
		}
		return homeGridPane;
	}

	/*
	 * If null, instantiates playingGridPane, set it's CSS ID and then returns it.
	 * 
	 * @return playingGridPane, a PlayingGridPane object which is the playing page.
	 */
//	public PlayingGridPane getPlayingGridPane() {
//		if (playingGridPane == null) {
//			playingGridPane = new PlayingGridPane();
//			playingGridPane.setId("playingGridPane");
//		}
//		return playingGridPane;
//	}

	/*
	 * If null, instantiate aboutGridPane and then returns it.
	 * 
	 * @return aboutGridPane, an AboutGridPane object which is the "about" page.
	 */
	public AboutGridPane getAboutGridPane() {
		if (aboutGridPane == null) {
			aboutGridPane = new AboutGridPane();
		}
		return aboutGridPane;
	}

	/*
	 * If null, instantiate rulesGridPane and then returns it.
	 * 
	 * @return rulesGridPane, an RulesGridPane object which is the rules page.
	 */
	public RulesGridPane getRulesGridPane() {
		if (rulesGridPane == null) {
			rulesGridPane = new RulesGridPane();
			rulesGridPane.setId("rulesPane");
		}
		return rulesGridPane;
	}

	public RegistrationConnectionGridPane getRegistrationConnectionGridPane() {
		if (registrationConnectionGridPane == null) {
			registrationConnectionGridPane = new RegistrationConnectionGridPane();
		}
		return registrationConnectionGridPane;
	}

	public PartyStackPane getPartyStackPane() {
		if (partyStackPane == null) {
			partyStackPane = new PartyStackPane();
		}
		return partyStackPane;
	}
}
