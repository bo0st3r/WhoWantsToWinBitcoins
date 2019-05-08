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

public class RegistrationGP extends GridPane {
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

	public RegistrationGP() {
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
		double[] sizesRow = { 0, 17, 3, 9.5, 10, 3, 9.5, 9.5, 3, 9.5, 11, 15, 0 };
		for (int i = 0; i <= sizesRow.length - 1; i++) {
			RowConstraints row = new RowConstraints();
			row.setValignment(VPos.CENTER);
			row.setPercentHeight(sizesRow[i]);
			getRowConstraints().add(row);
		}

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

	/**
	 * Returns the register button, if null first instantiates it and sets it's
	 * action and style.
	 * 
	 * @return the register Button object.
	 */
	public Button getBtnRegister() {
		if (btnRegister == null) {
			btnRegister = new Button("Register");
			btnRegister.getStyleClass().add("button-medium");
			GridPane.setHalignment(btnRegister, HPos.CENTER);
			GridPane.setValignment(btnRegister, VPos.CENTER);

			btnRegister.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// Hide all the error labels and tries to create the user
					hideErrorLbls();

					// Creates the user
					User user = createUser();

					// Returns if created user's null
					if (user == null)
						return;

					// Returns if it didn't add the user to the users
					if (!registerUser(user))
						return;

					ProjSP.setUserSP(user);
					((ProjSP) getParent().getParent().getParent()).userConnected();
					System.out.println(user);
				}
			});
			;
		}
		return btnRegister;
	}

	/**
	 * Creates and returns a new user using the pseudo, password and email address
	 * set in the input fields.
	 * 
	 * @return the new User object.
	 */
	public User createUser() {
		String pseudo = getTxtPseudo().getText();
		String password = getPwdPassword().getText();
		String email = getTxtEmail().getText();

		try {
			// Instantiates the user
			User user = new User(pseudo, password, email);
			return user;
		} catch (InputSyntaxException e) {
			// Show the pseudo syntax error
			verifyPseudoSyntax(pseudo);

			// Show the password syntax error
			verifyPasswordSyntax(password);

			// Show the email syntax error
			verifyEmailSyntax(email);

			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Adds the user to the users database if he's not already in use, otherwise
	 * displays a error message above the concerned field.
	 * 
	 * @param user the user to add.
	 * @param the  result.
	 */
	public boolean registerUser(User user) {
		try {
			return UserManagerSingleton.getInstance().addUser(user);
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

		return false;
	}

	/**
	 * Verifies the given pseudo syntax, if incorrect displays the email error.
	 * 
	 * @param pseudo the pseudo to verify.
	 */
	public void verifyPseudoSyntax(String pseudo) {
		if (!User.validatePseudo(pseudo)) {
			setLblText(getLblPseudoError(), "This pseudo does not fit it's requirements.	");
			getLblPseudoError().setVisible(true);
		} else {
			getLblPseudoError().setVisible(false);
		}
	}

	/**
	 * Verifies the given password syntax, if incorrect displays the email error.
	 * 
	 * @param password the password to verify.
	 */
	private void verifyPasswordSyntax(String password) {
		if (!User.validatePassword(password))
			getLblPasswordSyntax().setVisible(true);
		else
			getLblPasswordSyntax().setVisible(false);
	}

	/**
	 * Verifies the given email syntax, if incorrect displays the email error.
	 * 
	 * @param email the email to verify.
	 */
	private void verifyEmailSyntax(String email) {
		if (!User.validateEmail(email)) {
			setLblText(getLblEmailError(), "This email does not fit it's requirements.");
			getLblEmailError().setVisible(true);
		} else {
			getLblEmailError().setVisible(false);
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

					if (oldValue && pseudo.length() > 0) {
						verifyPseudoSyntax(pseudo);
					}
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
			lblPasswordSyntax = new Label("This password does not fit it's requirements.");
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

			// Verify the input when leaving the field
			pwdPassword.focusedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					String password = getPwdPassword().getText();

					if (oldValue && password.length() > 0) {
						verifyPasswordSyntax(password);
					}
				}
			});
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

			// Verify the input when leaving the field
			txtEmail.focusedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					String email = getTxtEmail().getText();

					if (oldValue && email.length() > 0) {
						verifyEmailSyntax(email);
					}
				}
			});
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
