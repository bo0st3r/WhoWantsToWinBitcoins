package view;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class ProjStackPane extends BorderPane {

	private StackPane stackPane;
	private HomeGridPane homeGridPane;
	private PlayingGridPane playingGridPane;
	private AboutGridPane aboutGridPane;

	/*
	 * ProjStackPane constructor, set homeGridPane visible at first.
	 */
	public ProjStackPane() {
		this.setCenter(getStackPane());
		hideAllComponentsInStackPane();
		// Set home visible first
		this.getHomeGridPane().setVisible(true);

	}

	/*
	 * Hide all components in stackPane.
	 */
	public void hideAllComponentsInStackPane() {
		for (Node node : getStackPane().getChildren()) {
			node.setVisible(false);
		}
	}

	/*
	 * Reset the playingGridPane in order to run a new party.
	 */
	public void resetPlayingGridPane() {
		playingGridPane = null;
		stackPane.getChildren().add(getPlayingGridPane());
	}

	// Getters
	/*
	 * If null, instantiate stackPane and add panes Home, Playing and About to it.
	 */
	public StackPane getStackPane() {
		if (stackPane == null) {
			stackPane = new StackPane();
			stackPane.getChildren().addAll(getHomeGridPane(), getPlayingGridPane(), getAboutGridPane());
		}
		return stackPane;
	}

	/*
	 * If null, instantiate homeGridPane.
	 */
	public HomeGridPane getHomeGridPane() {
		if (homeGridPane == null) {
			homeGridPane = new HomeGridPane();
		}
		return homeGridPane;
	}

	/*
	 * If null, instantiate playingGridPane.
	 */
	public PlayingGridPane getPlayingGridPane() {
		if (playingGridPane == null) {
			playingGridPane = new PlayingGridPane();
		}
		return playingGridPane;
	}

	/*
	 * If null, instantiate aboutGridPane;
	 */
	public AboutGridPane getAboutGridPane() {
		if (aboutGridPane == null) {
			aboutGridPane = new AboutGridPane();
		}
		return aboutGridPane;
	}
}
