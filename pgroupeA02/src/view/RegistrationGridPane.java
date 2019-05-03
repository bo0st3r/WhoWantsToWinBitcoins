package view;

import exceptions.DuplicateUserEmailException;
import exceptions.DuplicateUserException;
import exceptions.DuplicateUserPseudoException;
import exceptions.InputSyntaxException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import model.User;
import model.UserManagerSingleton;

public class RegistrationGridPane extends GridPane {
	private Label lblTitle;

	private Label lblPseudo;
	private Label lblPseudoError;
	private TextField txtPseudo;

	private Label lblPassword;
	private Label lblPasswordSyntax;
	private PasswordField pwdPassword;

	private Label lblEmail;
	private Label lblEmailError;
	private TextField txtEmail;

	private Button btnRegister;

	public RegistrationGridPane() {
		this.setGridLinesVisible(true);

		// Add columns
		double[] sizesCol = { 3, 94, 3 };
		for (int i = 0; i <= sizesCol.length - 1; i++) {
			ColumnConstraints col = new ColumnConstraints();
			col.setHalignment(HPos.CENTER);
			col.setPercentWidth(sizesCol[i]);
			getColumnConstraints().add(col);
		}

		// Set rows
		double[] sizesRow = { 0, 17, 3, 9.5, 10, 3, 9.5, 9.5, 3, 9.5, 11, 15, 0 };
		double total = 0;
		for (int i = 0; i <= sizesRow.length - 1; i++) {
			total += sizesRow[i];
			RowConstraints row = new RowConstraints();
			row.setValignment(VPos.CENTER);
			row.setPercentHeight(sizesRow[i]);
			getRowConstraints().add(row);
		}
		System.out.println("registration : " + total);

		// part
		add(getLblTitle(), 1, 1, 2, 1);

		// Pseudo
		add(getLblPseudo(), 1, 3);
		add(getLblPseudoError(), 1, 3);
		add(getTxtPseudo(), 1, 4);

		// Password
		add(getLblPassword(), 1, 6);
		add(getLblPasswordSyntax(), 1, 6);
		add(getPwdPassword(), 1, 7);

		// Email
		add(getLblEmail(), 1, 9);
		add(getLblEmailError(), 1, 9);
		add(getTxtEmail(), 1, 10);

		// Button
		add(getBtnRegister(), 1, 11, 2, 1);
	}

	public Button getBtnRegister() {
		if (btnRegister == null) {
			btnRegister = new Button("Register");
			btnRegister.getStyleClass().add("button-medium");
			GridPane.setHalignment(btnRegister, HPos.CENTER);
			GridPane.setValignment(btnRegister, VPos.CENTER);
			btnRegister.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					hideErrorLbls();
					createUser();
				}
			});
			;
		}
		return btnRegister;
	}

	/**
	 * Creates and gets an new user using the pseudo, password and email address set
	 * in the fields.
	 * 
	 * @return the new User object.
	 */
	public void createUser() {
		String pseudo = getTxtPseudo().getText();
		String password = getPwdPassword().getText();
		String email = getTxtEmail().getText();

		try {
			User user = new User(pseudo, password, email);
			registerUser(user);
		} catch (InputSyntaxException e) {
			// Show the pseudo syntax error
			if (!User.validatePseudo(pseudo)) {
				setLblText(getLblPseudoError(), "This pseudo does not fit the requirements.	");
				getLblPseudoError().setVisible(true);
			}

			// Show the password syntax error
			if (!User.validatePassword(password))
				getLblPasswordSyntax().setVisible(true);

			// Show the email syntax error
			if (!User.validateEmail(email)) {
				setLblText(getLblEmailError(), "This email does not fit the requirements.");
				getLblEmailError().setVisible(true);
			}

			e.printStackTrace();
			return;
		}
	}

	public void registerUser(User user) {
		try {
			UserManagerSingleton.getInstance().addUser(user);
		} catch (DuplicateUserPseudoException e) {
			setLblText(getLblPseudoError(), "This pseudo is already taken.");
			getLblPseudoError().setVisible(true);

			e.printStackTrace();

		} catch (DuplicateUserEmailException e) {
			setLblText(getLblEmailError(), "This email is already taken.");
			getLblEmailError().setVisible(true);

			e.printStackTrace();

		} catch (DuplicateUserException e) {
			setLblText(getLblPseudoError(), "This pseudo is already taken.");
			getLblPseudoError().setVisible(true);

			setLblText(getLblEmailError(), "This email is already taken.");
			getLblEmailError().setVisible(true);

			e.printStackTrace();
		}
	}

	/**
	 * If null, instantiates the title label and sets it's style. Then, returns it.
	 * 
	 * @return lblTitle, the Title object for the pane title.
	 */
	public Label getLblTitle() {
		if (lblTitle == null) {
			lblTitle = new Label("Not yet registered?");
			lblTitle.getStyleClass().add("title-large");
			GridPane.setValignment(getLblTitle(), VPos.CENTER);
		}

		return lblTitle;
	}

	// PSEUDO
	/**
	 * If null, instantiates the pseudo title label and sets it's style. Then
	 * returns it.
	 * 
	 * @return lblPseudo, the Label object for the pseudo title.
	 */
	public Label getLblPseudo() {
		if (lblPseudo == null) {
			lblPseudo = new Label("Pseudo");
			lblPseudo.getStyleClass().add("title-medium");
			GridPane.setHalignment(lblPseudo, HPos.LEFT);
			GridPane.setValignment(lblPseudo, VPos.BOTTOM);
		}

		return lblPseudo;
	}

	/**
	 * If null, instantiates the pseudo error Label and sets it's style. Then
	 * returns it.
	 * 
	 * @return lblPseudoError, the Label object for the pseudo syntax error.
	 */
	public Label getLblPseudoError() {
		if (lblPseudoError == null) {
			lblPseudoError = new Label("");
			lblPseudoError.getStyleClass().add("input-error");
			lblPseudoError.setVisible(false);
			GridPane.setHalignment(lblPseudoError, HPos.RIGHT);
			GridPane.setValignment(lblPseudoError, VPos.BOTTOM);
		}

		return lblPseudoError;
	}

	/**
	 * If null, instantiates the pseudo TextField and sets it's style. Then returns
	 * it.
	 * 
	 * @return txtPseudo, the TextField object for the pseudo.
	 */
	public TextField getTxtPseudo() {
		if (txtPseudo == null) {
			txtPseudo = new TextField();
			getTxtPseudo().setPromptText("Choose a pseudo");
			GridPane.setValignment(txtPseudo, VPos.CENTER);
			txtPseudo.getStyleClass().add("textfield-large");

			// Verify the input when leaving the field
			txtPseudo.focusedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					String pseudo = getTxtPseudo().getText();
					if (pseudo.length() == 0)
						return;

					if (oldValue) {
						if (!User.validatePseudo(pseudo)) {
							setLblText(getLblPseudoError(), "This pseudo does not fit the requirements.	");
							getLblPseudoError().setVisible(true);
						} else {
							getLblPseudoError().setVisible(false);
						}
					}
					System.out.println("hey");
				}
			});

			Tooltip hint = new Tooltip("Only use unaccented letters ans numbers.");
			txtPseudo.setTooltip(hint);
		}

		return txtPseudo;

	}

	// PASSWORD
	/**
	 * If null, instantiates the password title label and sets it's style. Then
	 * returns it.
	 * 
	 * @return lblPassword, the Label object for the password title.
	 */
	public Label getLblPassword() {
		if (lblPassword == null) {
			lblPassword = new Label("Password");
			lblPassword.getStyleClass().add("title-medium");
			GridPane.setHalignment(lblPassword, HPos.LEFT);
			GridPane.setValignment(lblPassword, VPos.BOTTOM);
		}

		return lblPassword;
	}

	/**
	 * If null, instantiates the password syntax error Label and sets it's style.
	 * Then returns it.
	 * 
	 * @return lblPasswordSyntax, the Label object for the password syntax error.
	 */
	public Label getLblPasswordSyntax() {
		if (lblPasswordSyntax == null) {
			lblPasswordSyntax = new Label("This password does not fit the requirements.");
			lblPasswordSyntax.getStyleClass().add("input-error");
			lblPasswordSyntax.setVisible(false);
			GridPane.setHalignment(lblPasswordSyntax, HPos.RIGHT);
			GridPane.setValignment(lblPasswordSyntax, VPos.BOTTOM);
		}

		return lblPasswordSyntax;
	}

	/**
	 * If null, instantiates the password PasswordField and sets it's style. Then
	 * returns it.
	 * 
	 * @return pwdPassword, the PasswordField object for the password.
	 */
	public PasswordField getPwdPassword() {
		if (pwdPassword == null) {
			pwdPassword = new PasswordField();
			getPwdPassword().setPromptText("Choose a password");
			pwdPassword.getStyleClass().add("textfield-large");
			GridPane.setValignment(pwdPassword, VPos.CENTER);
		}

		return pwdPassword;
	}

	// EMAIL
	/**
	 * If null, instantiates the email title label and sets it's style. Then returns
	 * it.
	 * 
	 * @return lblEmail, the Label object for the email title.
	 */
	public Label getLblEmail() {
		if (lblEmail == null) {
			lblEmail = new Label("E-Mail");
			lblEmail.getStyleClass().add("title-medium");
			GridPane.setHalignment(lblEmail, HPos.LEFT);
			GridPane.setValignment(lblEmail, VPos.BOTTOM);
		}

		return lblEmail;
	}

	/**
	 * If null, instantiates the email syntax error Label and sets it's style. Then
	 * returns it.
	 * 
	 * @return lblEmailSyntax, the Label object for the email syntax error.
	 */
	public Label getLblEmailError() {
		if (lblEmailError == null) {
			lblEmailError = new Label("");
			lblEmailError.getStyleClass().add("input-error");
			lblEmailError.setVisible(false);
			GridPane.setHalignment(lblEmailError, HPos.RIGHT);
			GridPane.setValignment(lblEmailError, VPos.BOTTOM);
		}

		return lblEmailError;
	}

	/**
	 * If null, instantiates the email TextField and sets it's style. Then returns
	 * it.
	 * 
	 * @return txtEmail, the TextField object for the email.
	 */
	public TextField getTxtEmail() {
		if (txtEmail == null) {
			txtEmail = new TextField();
			getTxtEmail().setPromptText("Enter your email");
			txtEmail.getStyleClass().add("textfield-large");
			GridPane.setValignment(txtEmail, VPos.CENTER);
		}

		return txtEmail;
	}

	/**
	 * Hides all the syntax error labels.
	 */
	public void hideErrorLbls() {
		getLblPseudoError().setVisible(false);
		getLblPasswordSyntax().setVisible(false);
		getLblEmailError().setVisible(false);
	}

	/**
	 * Sets a new text for the specified label if it and the message are not null.
	 * 
	 * @param label   the specified label.
	 * @param message the new message to set.
	 */
	public void setLblText(Label label, String message) {
		if (label != null)
			if (message.length() > 0)
				label.setText(message);
	}
}
