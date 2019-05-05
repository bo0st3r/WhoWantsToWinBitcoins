package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class RegistrationConnectionGP extends GridPane {
	private LoginGP loginGP;
	private RegistrationGP registrationGP;
	private Button btnHome;

	public RegistrationConnectionGP() {
//		this.setGridLinesVisible(true);
		getStyleClass().add("basic-background");

		// Add columns
		double[] sizesCol = { 6, 41.5, 5, 41.5, 6 };
		for (int i = 0; i <= sizesCol.length - 1; i++) {
			ColumnConstraints col = new ColumnConstraints();
			col.setHalignment(HPos.CENTER);
			col.setPercentWidth(sizesCol[i]);
			getColumnConstraints().add(col);
		}

		// Set rows
		double[] sizesRow = { 3, 82, 12, 3 };
		for (int i = 0; i <= sizesRow.length - 1; i++) {
			RowConstraints row = new RowConstraints();
			row.setValignment(VPos.CENTER);
			row.setPercentHeight(sizesRow[i]);
			getRowConstraints().add(row);
		}

		// Inner-panes
		add(getRegistrationGP(), 1, 1);
		add(getLoginGP(), 3, 1);

		// Buttons
		add(getBtnHome(), 1, 2, 3, 1);
	}

	/**
	 * If null instantiate btnHome, then returns it. Defines it's action when using
	 * it.
	 * 
	 * @return The "home" Button object.
	 */
	public Button getBtnHome() {
		if (btnHome == null) {
			btnHome = new Button("Home");
			btnHome.getStyleClass().add("button-large");
			GridPane.setHalignment(getBtnHome(), HPos.CENTER);
			GridPane.setValignment(getBtnHome(), VPos.CENTER);

			btnHome.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					setVisible(false);
					((ProjSP) getParent().getParent()).getHomeGridPane().setVisible(true);

				}
			});
		}
		return btnHome;
	}

	public LoginGP getLoginGP() {
		if (loginGP == null) {
			loginGP = new LoginGP();
			loginGP.setId("loginGP");
		}
		return loginGP;
	}

	public RegistrationGP getRegistrationGP() {
		if (registrationGP == null) {
			registrationGP = new RegistrationGP();
			registrationGP.setId("registrationGP");
		}
		return registrationGP;
	}
}