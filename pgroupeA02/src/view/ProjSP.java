package view;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import model.Deck;
import model.Earning;
import utilities.Serialization;
import view.tableviews.TableViewEarningsBP;
import view.tableviews.TableViewQuestionsBP;
import view.tableviews.TableViewScoresBP;

public class ProjSP extends BorderPane {

	private StackPane stackPane;
	private HomeGP homeGridPane;
	private AboutGP aboutGridPane;
	private RulesGP rulesGridPane;
	private RegistrationConnectionGP registrationConnectionGridPane;
	private PartySP partyStackPane;
	private ProfilGP profilGridPane;
	private TableViewQuestionsBP tvQuestionBP;
	private TableViewEarningsBP tvEarningsBP;
	private TableViewScoresBP tvScoresBP;

	/*
	 * Constructor, set homeGridPane visible at first, hide the others panes and set
	 * stackPane as the center.
	 */
	public ProjSP() {
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

	// Getters
	/*
	 * If null, instantiates stackPane and adds panes Home, About and Rules to it's
	 * children and then returns it.
	 * 
	 * @return stackPane, a StackPane object which contains the others main panes.
	 */
	public StackPane getStackPane() {
		if (stackPane == null) {
			stackPane = new StackPane();
			stackPane.getChildren().addAll(getHomeGridPane(), getAboutGridPane(), getRulesGridPane(),
					getRegistrationConnectionGridPane(), getPartyStackPane(), getProfilGridPane());
		}

		return stackPane;
	}

	/*
	 * If null, instantiate homeGridPane and then returns it.
	 * 
	 * @return homeGridPane, a HomeGridPane object which is the home page.
	 */
	public HomeGP getHomeGridPane() {
		if (homeGridPane == null) {
			homeGridPane = new HomeGP();
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
	public AboutGP getAboutGridPane() {
		if (aboutGridPane == null) {
			aboutGridPane = new AboutGP();
		}
		return aboutGridPane;
	}

	/*
	 * If null, instantiate rulesGridPane and then returns it.
	 * 
	 * @return rulesGridPane, an RulesGridPane object which is the rules page.
	 */
	public RulesGP getRulesGridPane() {
		if (rulesGridPane == null) {
			rulesGridPane = new RulesGP();
			rulesGridPane.setId("rulesPane");
		}
		return rulesGridPane;
	}

	public RegistrationConnectionGP getRegistrationConnectionGridPane() {
		if (registrationConnectionGridPane == null) {
			registrationConnectionGridPane = new RegistrationConnectionGP();
		}
		return registrationConnectionGridPane;
	}

	public PartySP getPartyStackPane() {
		if (partyStackPane == null) {
			partyStackPane = new PartySP();
		}
		return partyStackPane;
	}

	public ProfilGP getProfilGridPane() {
		if (profilGridPane == null) {
			profilGridPane = new ProfilGP();
			profilGridPane.setId("");
		}
		return profilGridPane;
	}

	public TableViewQuestionsBP getTvQuestionBP() {
		if (tvQuestionBP == null) {
			tvQuestionBP = new TableViewQuestionsBP(Serialization.jsonToDeck(Deck.FILE_NAME));
			tvQuestionBP.getStyleClass().add("basic-background");
			stackPane.getChildren().add(tvQuestionBP);
		}

		return tvQuestionBP;
	}

	public TableViewEarningsBP getTvEarningsBP() {
		if (tvEarningsBP == null) {
			tvEarningsBP = new TableViewEarningsBP(Serialization.jsonToEarning(Earning.FILE_NAME));
			tvEarningsBP.getStyleClass().add("basic-background");
			stackPane.getChildren().add(tvEarningsBP);
		}

		return tvEarningsBP;
	}

	public TableViewScoresBP getTvScoresBP() {
		if (tvScoresBP == null) {
			tvScoresBP = new TableViewScoresBP();
			tvScoresBP.getStyleClass().add("basic-background");
			stackPane.getChildren().add(tvScoresBP);
		}

		return tvScoresBP;
	}
}
