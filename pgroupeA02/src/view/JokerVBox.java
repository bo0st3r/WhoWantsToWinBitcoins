package view;

import java.text.DecimalFormat;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import model.Joker;
import model.Joker5050;
import model.JokerFriend;
import model.JokerPublic;
import model.Question;

public class JokerVBox extends VBox {
	private PlayingGridPane pgp;
	private Joker joker;
	private boolean cancelJokerResults;

	private Button btnJokerPublic;
	private Button btnJokerFriend;
	private Button btnJoker5050;

	/*
	 * Constructor, set spacings, alignment, instance variables and add the pane
	 * content.
	 */
	public JokerVBox(PlayingGridPane pgp) {
		// Spacings
		setPadding(new Insets(10));
		setSpacing(45);

		// Alignment
		setAlignment(Pos.CENTER);

		// Setting instance variables
		this.pgp = pgp;
		joker = new Joker();
		cancelJokerResults = false;

		// Add the content
		getChildren().addAll(getBtnJoker5050(), getBtnJokerFriend(), getBtnJokerPublic());
	}

	// Joker Public
	/*
	 * Return btnJokerPublic, if null instantiates it, adds CSS class "btnJoker" and
	 * set it's action when clicking on it. Action : shows 4 (almost) randoms % of
	 * public vote for each answer button.
	 * 
	 * @return btnJokerPublic, the Button object used for the public joker.
	 */
	public Button getBtnJokerPublic() {
		if (btnJokerPublic == null) {
			btnJokerPublic = new Button("Ask the public");
			btnJokerPublic.getStyleClass().add("btnJoker");

			btnJokerPublic.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					setCancelJokerResults(true);
					joker.setStrategy(new JokerPublic());
					joker.useJoker(pgp.getParty());

					// Displays votes %
					DecimalFormat df = new DecimalFormat("#0.0");
					for (int index = 0; index <= Question.NB_ANSWERS - 1; index++) {
						pgp.getLblJokerResults(index)
								.setText(df.format(pgp.getParty().getJokerPublicPercents().get(index)) + "%");
					}
					pgp.setVisibleLblJokerResults(true);

					btnJokerPublic.setDisable(true);
				}
			});
		}
		return btnJokerPublic;
	}

	// Joker Friend
	/*
	 * Return btnJokerFriend, if null instantiates it, adds CSS class "btnJoker" and
	 * set it's action when clicking on it. Action : for 1 answer button, changes
	 * it's style in order to designate the answer that the player's friend gave.
	 * 
	 * @return btnJokerFriend, the Button object used for the friend joker.
	 */
	public Button getBtnJokerFriend() {
		if (btnJokerFriend == null) {
			btnJokerFriend = new Button("Call a friend");
			btnJokerFriend.getStyleClass().add("btnJoker");

			btnJokerFriend.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					setCancelJokerResults(true);
					joker.setStrategy(new JokerFriend());
					joker.useJoker(pgp.getParty());

					pgp.getQuestionGP().getBtnAnswer(pgp.getParty().getJokerFriendIndex()).setId("answerJokerFriend");

					btnJokerFriend.setDisable(true);
				}
			});
		}
		return btnJokerFriend;
	}

	// Joker 5050
	/*
	 * Return btnJoker5050, if null instantiates it, adds CSS class "btnJoker" and
	 * set it's action when clicking on it. Action : disable half of the answer
	 * buttons, the right answer button can't be disabled.
	 * 
	 * @return btnJoker5050, the Button object used for the 50/50 joker.
	 */
	public Button getBtnJoker5050() {
		if (btnJoker5050 == null) {
			btnJoker5050 = new Button("50/50");
			btnJoker5050.getStyleClass().add("btnJoker");

			btnJoker5050.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					setCancelJokerResults(true);
					joker.setStrategy(new Joker5050());
					joker.useJoker(pgp.getParty());

					// Disable randomly selected buttons
					for (Integer index : pgp.getParty().getJoker5050Indexes()) {
						pgp.getQuestionGP().setDisableBtnAnswer(true, index);
					}

					btnJoker5050.setDisable(true);
				}
			});
		}

		return btnJoker5050;
	}

	/*
	 * Return if there's the need of canceling the jokers results.
	 * 
	 * @return cancelJokerResults, the boolean value.
	 */
	public boolean isCancelJokerResults() {
		return cancelJokerResults;
	}

	/*
	 * Change cancelJokerResults value, which determine if there's the need of
	 * canceling the jokers results.
	 * 
	 * @param cancelJokerResults, the new boolean value.
	 */
	public void setCancelJokerResults(boolean cancelJokerResults) {
		this.cancelJokerResults = cancelJokerResults;
	}
}
