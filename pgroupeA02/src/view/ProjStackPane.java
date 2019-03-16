package view;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class ProjStackPane extends BorderPane {

	private StackPane stackPane;
	private HomeGridPane homeGridPane;
	private PlayingGridPane playingGridPane;
	private AboutGridPane aboutGridPane;

	public ProjStackPane() {

		this.setCenter(getStackPane());
		hideAllComponentInStackPane();
		// home visible first
		this.getHomeGridPane().setVisible(true);

	}

	// hide component
	public void hideAllComponentInStackPane() {
		for (Node node : getStackPane().getChildren()) {
			node.setVisible(false);
		}
	}

	// getters
	public StackPane getStackPane() {
		if (stackPane == null) {
			stackPane = new StackPane();
			stackPane.getChildren().addAll(getHomeGridPane(), getPlayingGridPane(), getAboutGridPane());
		}
		return stackPane;
	}

	public HomeGridPane getHomeGridPane() {
		if (homeGridPane == null) {
			homeGridPane = new HomeGridPane();
		}
		return homeGridPane;
	}

	public PlayingGridPane getPlayingGridPane() {
		if (playingGridPane == null) {
			playingGridPane = new PlayingGridPane();
		}
		return playingGridPane;
	}

	public AboutGridPane getAboutGridPane() {
		if (aboutGridPane == null) {
			aboutGridPane = new AboutGridPane();
		}
		return aboutGridPane;
	}

}
