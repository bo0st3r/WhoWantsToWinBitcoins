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

	public JokerVBox(PlayingGridPane pgp) {
		this.setPadding(new Insets(10));
		this.setSpacing(45);

		this.pgp = pgp;
		joker = new Joker();

		cancelJokerResults = false;

		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(getBtnJoker5050(), getBtnJokerFriend(), getBtnJokerPublic());
	}

	// Joker Public
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

	public boolean isCancelJokerResults() {
		return cancelJokerResults;
	}

	public void setCancelJokerResults(boolean cancelJokerResults) {
		this.cancelJokerResults = cancelJokerResults;
	}
}
