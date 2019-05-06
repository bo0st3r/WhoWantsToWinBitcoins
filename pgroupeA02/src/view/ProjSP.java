package view;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import model.Deck;
import model.Earning;
import model.User;
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
	private static User USER;

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
		USER = null;
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

	/**
	 * If null, instantiate aboutGridPane and then returns it.
	 * 
	 * @return aboutGridPane, an AboutGridPane object which is the "about" page.
	 */
	public AboutGP getAboutGridPane() {
		if (aboutGridPane == null) {
			aboutGridPane = new AboutGP();
			aboutGridPane.setId("rulesPane");
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
			profilGridPane.setId("profilPane");
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

	public void userConnected() {
		hideAllComponentsInStackPane();

		HomeGP home = getHomeGridPane();
		home.setVisible(true);
		home.getBtnConnect().setVisible(false);
		home.getBtnDisconnect().setVisible(true);
		home.getBtnProfile().setVisible(true);

		if (USER.isAdmin())
			home.getBtnManageQuestions().setVisible(true);
	}

	public void userDisconnected() {
		setUserSP(null);

		hideAllComponentsInStackPane();
		HomeGP home = getHomeGridPane();
		home.setVisible(true);
		home.getBtnConnect().setVisible(true);
		home.getBtnDisconnect().setVisible(false);
		home.getBtnProfile().setVisible(false);
		home.getBtnManageQuestions().setVisible(false);
	}

	public static void setUserSP(User user) {
		USER = user;
	}

	public static User getUserSP() {
		return USER;
	}

}
