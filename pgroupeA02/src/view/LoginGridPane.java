package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class LoginGridPane extends GridPane {
	private Label lblTitleConnection;
	private Label lblPseudoConnection;
	private TextField txtPseudoConnection;
	private Label lblPasswordConnection;
	private PasswordField pwdPasswordConnection;
	private Button btnLogin;

	public LoginGridPane() {
//		this.setGridLinesVisible(true);

		// Add columns
		double[] sizesCol = { 3, 94, 3 };
		for (int i = 0; i <= sizesCol.length - 1; i++) {
			ColumnConstraints col = new ColumnConstraints();
			col.setHalignment(HPos.CENTER);
			col.setPercentWidth(sizesCol[i]);
			getColumnConstraints().add(col);
		}

		// Set rows
		double[] sizesRow = { 0, 17, 3, 9.5, 10, 3, 9.5, 9.5, 23.5, 15, 0 };
		double total = 0;
		for (int i = 0; i <= sizesRow.length - 1; i++) {
			total += sizesRow[i];
			RowConstraints row = new RowConstraints();
			row.setValignment(VPos.CENTER);
			row.setPercentHeight(sizesRow[i]);
			getRowConstraints().add(row);
		}
		System.out.println("login : " + total);

		// Connection part
		add(getLblTitleConnection(), 1, 1, 2, 1);

		// Pseudo
		add(getLblPseudoConnection(), 1, 3);
		add(getTxtPseudoConnection(), 1, 4);

		// Password
		add(getLblPasswordConnection(), 1, 6);
		add(getPwdPasswordConnection(), 1, 7);

		add(getBtnLogin(), 1, 9, 2, 1);
	}

	public Label getLblTitleConnection() {
		if (lblTitleConnection == null) {
			lblTitleConnection = new Label("Already registered?");
			lblTitleConnection.getStyleClass().add("title-large");
			GridPane.setValignment(lblTitleConnection, VPos.CENTER);
		}

		return lblTitleConnection;
	}

	public Label getLblPseudoConnection() {
		if (lblPseudoConnection == null) {
			lblPseudoConnection = new Label("Pseudo");
			lblPseudoConnection.getStyleClass().add("title-medium");

			GridPane.setHalignment(lblPseudoConnection, HPos.LEFT);
			GridPane.setValignment(lblPseudoConnection, VPos.BOTTOM);
		}

		return lblPseudoConnection;
	}

	public TextField getTxtPseudoConnection() {
		if (txtPseudoConnection == null) {
			txtPseudoConnection = new TextField();
			txtPseudoConnection.setPromptText("Enter your pseudo");
			txtPseudoConnection.getStyleClass().add("textfield-large");
			GridPane.setValignment(txtPseudoConnection, VPos.CENTER);
		}

		return txtPseudoConnection;
	}

	public Label getLblPasswordConnection() {
		if (lblPasswordConnection == null) {
			lblPasswordConnection = new Label("Password");
			lblPasswordConnection.getStyleClass().add("title-medium");

			GridPane.setHalignment(lblPasswordConnection, HPos.LEFT);
			GridPane.setValignment(lblPasswordConnection, VPos.BOTTOM);
		}

		return lblPasswordConnection;
	}

	public PasswordField getPwdPasswordConnection() {
		if (pwdPasswordConnection == null) {
			pwdPasswordConnection = new PasswordField();
			pwdPasswordConnection.setPromptText("Enter your password");
			pwdPasswordConnection.getStyleClass().add("textfield-large");
			GridPane.setValignment(pwdPasswordConnection, VPos.CENTER);
		}

		return pwdPasswordConnection;
	}

	public Button getBtnLogin() {
		if (btnLogin == null) {
			btnLogin = new Button("Login");
			btnLogin.getStyleClass().add("button-medium");
			GridPane.setHalignment(btnLogin, HPos.CENTER);
			GridPane.setValignment(btnLogin, VPos.CENTER);

			btnLogin.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					setVisible(false);
					((ProjStackPane) getParent().getParent()).getHomeGridPane().setVisible(true);
				}
			});
			;
		}

		return btnLogin;
	}
}
