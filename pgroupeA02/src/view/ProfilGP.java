package view;

import com.sun.javafx.util.Utils;

import exceptions.DuplicateUserEmailException;
import exceptions.DuplicateUserException;
import exceptions.DuplicateUserPseudoException;
import exceptions.InputSyntaxException;
import exceptions.UserNotFoundException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import model.User;
import model.UserManagerSingleton;



public class ProfilGP extends GridPane{

	private Label lblTitle;
	private Label lblTitlePseudo;
	private Label lblPseudo;
	private Label lblTitlePassword;
	private Label lblPassword;
	private PasswordField pwdChangePassword;
	private Label lblTitleEmail;
	private Label lblEmail;
	private TextField txtChangeEmail;
	private Label lblTitlePartiesPlayed;
	private Label lblPartiesPlayed;
	private Label lblTitlePartiesWon;
	private Label lblPartiesWon;
	private Label lblTitleHighestEarningsWon;
	private Label lblHighestEarningsWon;
	private Label lblTitleTotalEarningsWon;
	private Label lblTotalEarningsWon;
	private Button btnSavePassword;
	private Button btnHome;
	private Button btnPasswordChange;
	private Button btnEmailChange;
	private Button btnSaveEmail;
	private Label lblPasswordSyntax;
	private Label lblEmailError;

	
	public ProfilGP() {

//		this.setGridLinesVisible(true);

		this.setHgap(8);
		this.setVgap(8);

		// add columns
		ColumnConstraints c = new ColumnConstraints();
		c.setPercentWidth(10);
		this.getColumnConstraints().addAll(c, c, c, c, c, c, c, c, c, c);

		// add rows
		RowConstraints r = new RowConstraints();
		r.setPercentHeight(10);
		this.getRowConstraints().addAll(r, r, r, r, r, r, r, r, r, r);
		
		this.add(getLblTitle(), 3, 0, 2, 1);
		
		this.add(getLblTitlePseudo(), 1, 1);
		this.add(getLblPseudo(), 4, 1, 2, 1);
		
		this.add(getLblTitlePassword(), 1, 2, 3, 1);
		this.add(getLblPassword(), 4, 2, 2, 1);
		
		this.add(getBtnPasswordChange(), 6, 2, 2, 1);
		this.add(getPwdChangePassword(), 6, 2, 2, 1);
		add(getLblPasswordSyntax(), 6, 2, 3, 1);
		this.add(getBtnSavePassword(), 9, 2);
		
		this.add(getLblTitleEmail(), 1, 3, 3, 1);
		this.add(getLblEmail(), 4, 3, 2, 1);
		
		this.add(getBtnEmailChange(), 6, 3, 2, 1);
		this.add(getTxtChangeEmail(), 6, 3, 2, 1);
		add(getLblEmailError(), 6, 3, 3, 1);
		this.add(getBtnSaveEmail(), 9, 3);
		
		this.add(getLblTitlePartiesPlayed(), 1, 4, 3, 1);
		this.add(getLblPartiesPlayed(), 4, 4, 2, 1);
		
		this.add(getLblTitlePartiesWon(), 1, 5, 2, 1);
		this.add(getLblPartiesWon(), 4, 5, 2, 1);
		
		this.add(getLblTitleHighestEarningsWon(), 1, 6, 3, 1);
		this.add(getLblHighestEarningsWon(), 4, 6, 4, 1);
		
		this.add(getLblTitleTotalEarningsWon(), 1, 7, 3, 1);
		this.add(getLblTotalEarningsWon(), 4, 7, 4, 1);
		
		
		this.add(getBtnHome(), 4, 8);
		
		}



		public Label getLblTitle() {
			if (lblTitle==null) {
				lblTitle=new Label("Profil");
				GridPane.setHalignment(getLblTitle(), HPos.CENTER);
				lblTitle.getStyleClass().add("title-large");
			}
			return lblTitle;
		}



		public Label getLblTitlePseudo() {
			if (lblTitlePseudo==null) {
				lblTitlePseudo = new Label("Pseudo");
				lblTitlePseudo.getStyleClass().add("title-medium");
			}
			return lblTitlePseudo;
		}



		public Label getLblPseudo() {
			if (lblPseudo==null) {
				lblPseudo = new Label("unknown");
				lblPseudo.getStyleClass().add("title-medium");
				
			}
			return lblPseudo;
		}



		public Label getLblTitlePassword() {
			if(lblTitlePassword==null) {
				lblTitlePassword = new Label("Password");
				lblTitlePassword.getStyleClass().add("title-medium");
			}
			return lblTitlePassword;
		}
		



		public Label getLblPassword() {
			if (lblPassword==null) {
				lblPassword = new Label("******");
				lblPassword.getStyleClass().add("title-medium");
			}
			return lblPassword;
		}



		public PasswordField getPwdChangePassword() {
			if (pwdChangePassword == null) {
				pwdChangePassword = new PasswordField();
				getPwdChangePassword().setPromptText("Choose a password");
				pwdChangePassword.getStyleClass().add("textfield-large");
				pwdChangePassword.setVisible(false);
				GridPane.setValignment(pwdChangePassword, VPos.CENTER);

				// Verify the input when leaving the field
				pwdChangePassword.focusedProperty().addListener(new ChangeListener<Boolean>() {
					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
						String password = getPwdChangePassword().getText();

						if (oldValue && password.length() > 0) {
							verifyPasswordSyntax(password);
						}
					}
				});
			}

			return pwdChangePassword;
		}



		public Label getLblTitleEmail() {
			if(lblTitleEmail==null) {
				lblTitleEmail = new Label("Email");
				lblTitleEmail.getStyleClass().add("title-medium");
			}
			return lblTitleEmail;
		}



		public Label getLblEmail() {
			if (lblEmail==null) {
				lblEmail = new Label("unknown");
				lblEmail.getStyleClass().add("title-medium");
				lblEmail.setId("lblResize");
				lblEmail.setWrapText(true);
			}
			return lblEmail;
		}



		public TextField getTxtChangeEmail() {
			
			if (txtChangeEmail == null) {
				txtChangeEmail = new TextField();
				txtChangeEmail.setPromptText("New Email");
				txtChangeEmail.getStyleClass().add("textfield-large");
				txtChangeEmail.setVisible(false);

				// Verify the input when leaving the field
				txtChangeEmail.focusedProperty().addListener(new ChangeListener<Boolean>() {
					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
						String email = getTxtChangeEmail().getText();

						if (oldValue && email.length() > 0) {
							verifyEmailSyntax(email);
						}
					}
				});
			}

			return txtChangeEmail;
		}



		public Label getLblTitlePartiesPlayed() {
			if (lblTitlePartiesPlayed == null) {
				lblTitlePartiesPlayed = new Label("Parties played :");
				lblTitlePartiesPlayed.getStyleClass().add("title-medium");
			}
			return lblTitlePartiesPlayed;
		}



		public Label getLblPartiesPlayed() {
			if (lblPartiesPlayed==null) {
				lblPartiesPlayed = new Label("No one");
				lblPartiesPlayed.getStyleClass().add("title-medium");
			}
			return lblPartiesPlayed;
		}



		public Label getLblTitlePartiesWon() {
			if (lblTitlePartiesWon==null) {
				lblTitlePartiesWon = new Label("Parties won :");
				lblTitlePartiesWon.getStyleClass().add("title-medium");
			}
			return lblTitlePartiesWon;
		}



		public Label getLblPartiesWon() {
			if (lblPartiesWon==null) {
				lblPartiesWon = new Label("No one");
				lblPartiesWon.getStyleClass().add("title-medium");
			}
			return lblPartiesWon;
		}



		public Label getLblTitleHighestEarningsWon() {
			if (lblTitleHighestEarningsWon == null) {
				lblTitleHighestEarningsWon = new Label("Highest earnings won :");
				lblTitleHighestEarningsWon.getStyleClass().add("title-medium");
			}
			return lblTitleHighestEarningsWon;
		}



		public Label getLblHighestEarningsWon() {
			if (lblHighestEarningsWon==null) {
				lblHighestEarningsWon = new Label("No earnings");
				lblHighestEarningsWon.getStyleClass().add("title-medium");
			}
			return lblHighestEarningsWon;
		}



		public Label getLblTitleTotalEarningsWon() {
			if (lblTitleTotalEarningsWon==null) {
				lblTitleTotalEarningsWon = new Label("Total earnings won :");
				lblTitleTotalEarningsWon.getStyleClass().add("title-medium");
			}
			
			return lblTitleTotalEarningsWon;
		}

		public Label getLblTotalEarningsWon() {
			if (lblTotalEarningsWon == null) {
				lblTotalEarningsWon = new Label("No earnings");
				lblTotalEarningsWon.getStyleClass().add("title-medium");
			}
			return lblTotalEarningsWon;
		}



		public Button getBtnHome() {
			if(btnHome==null) {
				btnHome = new Button("Home");
				GridPane.setHalignment(getBtnHome(), HPos.CENTER);
				btnHome.setPrefWidth(Integer.MAX_VALUE);
				
				btnHome.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						setVisible(false);
						((ProjSP) getParent().getParent()).getHomeGridPane().setVisible(true);
						hideAndClearChangingFields();

					}
				});
			}
			return btnHome;
		}
		
		
		public Button getBtnPasswordChange() {
			if (btnPasswordChange==null) {
				btnPasswordChange = new Button("Change Password");
				btnPasswordChange.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						btnPasswordChange.setVisible(false);
						getPwdChangePassword().setVisible(true);
						getBtnSavePassword().setVisible(true);
						getBtnEmailChange().setDisable(true);
						
					}
				});
			}
			return btnPasswordChange;
		}



		public Button getBtnEmailChange() {
			if (btnEmailChange==null) {
				btnEmailChange= new Button("Change Email");
				btnEmailChange.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						btnEmailChange.setVisible(false);
						getTxtChangeEmail().setVisible(true);
						getBtnSaveEmail().setVisible(true);
						getBtnPasswordChange().setDisable(true);
						
					}
				});
			}
			return btnEmailChange;
		}
		



		public Button getBtnSavePassword() {
					if(btnSavePassword == null) {
						btnSavePassword= new Button("Save");
						GridPane.setHalignment(getBtnSavePassword(), HPos.CENTER);
		//				btnSavePassword.setPrefWidth(Integer.MAX_VALUE);
						btnSavePassword.setVisible(false);
						btnSavePassword.setOnAction(new EventHandler<ActionEvent>() {
		
							@Override
							public void handle(ActionEvent arg0) {
								User oldUser= ProjSP.getUserSP();
								
								User newUser=null;
				
								
								if (User.validatePassword(getPwdChangePassword().getText())){
								
									try {
										newUser= createUserNewPassword();
										UserManagerSingleton.getInstance().updateUser(oldUser, newUser);
		//								
									} catch (DuplicateUserPseudoException 
											| DuplicateUserException | InputSyntaxException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (DuplicateUserEmailException e) {
										setLblText(getLblEmailError(), "This email is already taken");
									}
								}
								else {
									
								}
								((ProjSP) getParent().getParent()).setUserSP(newUser);
								
								setVisible(false);
								((ProjSP) getParent().getParent()).getHomeGridPane().setVisible(true);
								hideAndClearChangingFields();
								
							}
						});
					}
					return btnSavePassword;
				}



		public Button getBtnSaveEmail() {
			if(btnSaveEmail == null) {
				btnSaveEmail= new Button("Save");
				GridPane.setHalignment(getBtnSaveEmail(), HPos.CENTER);
//				btnSaveEmail.setPrefWidth(Integer.MAX_VALUE);
				btnSaveEmail.setVisible(false);
				
				btnSaveEmail.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						User oldUser= ProjSP.getUserSP();
						
						User newUser=null;
		
						
						if (User.validateEmail(getLblEmail().getText())){
						
								try {
									newUser= createUserNewMail();
								} catch (InputSyntaxException e) {
									setLblText(getLblEmailError(), "This email does not fit it's requirements.");
								}
								try {
									registerUser(oldUser, newUser);
								} catch (DuplicateUserPseudoException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
//								((ProjSP) getParent().getParent().getParent()).setUserSP(newUser);
							
						}
						((ProjSP) getParent().getParent()).setUserSP(newUser);
						
						setVisible(false);
						((ProjSP) getParent().getParent()).getHomeGridPane().setVisible(true);
						hideAndClearChangingFields();
						
					}
				});
			}
			
			return btnSaveEmail;
		}



		public void UserToProfilFields(User u) {
			
			getLblPseudo().setText(u.getPseudo());
			getLblEmail().setText(u.getEmail());
			getLblPartiesPlayed().setText(u.getPartiesPlayed()+" parties played");
			getLblPartiesWon().setText(u.getPartiesWon()+" parties won");
			getLblHighestEarningsWon().setText(u.getHighestEarningsWon()+" is the highest earning you won");
			getLblTotalEarningsWon().setText(u.getTotalEarningsWon()+" is your earning's total");
		}
		
		public User createUserNewMail() throws InputSyntaxException  {
			
			String pseudo = ProjSP.getUserSP().getPseudo();
			String password = ProjSP.getUserSP().getPassword();
			String email = getTxtChangeEmail().getText();
			int partiesWon = ProjSP.getUserSP().getPartiesWon();
			int partiesPlayed = ProjSP.getUserSP().getPartiesPlayed();
			double highestEarningsWon = ProjSP.getUserSP().getHighestEarningsWon();
			double totalEarningsWon = ProjSP.getUserSP().getTotalEarningsWon();
			
			
			User user = new User(pseudo, password, email, partiesWon, partiesPlayed, highestEarningsWon, totalEarningsWon);
			
			return user;
		}
		
		public User createUserNewPassword() throws InputSyntaxException {
			String pseudo = ProjSP.getUserSP().getPseudo();
			String password = getPwdChangePassword().getText();
			String email = ProjSP.getUserSP().getEmail();
			int partiesWon = ProjSP.getUserSP().getPartiesWon();
			int partiesPlayed = ProjSP.getUserSP().getPartiesPlayed();
			double highestEarningsWon = ProjSP.getUserSP().getHighestEarningsWon();
			double totalEarningsWon = ProjSP.getUserSP().getTotalEarningsWon();
			
			
			User user = new User(pseudo, password, email, partiesWon, partiesPlayed, highestEarningsWon, totalEarningsWon);
			return user;
		}
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
		public Label getLblEmailError() {
			if (lblEmailError == null) {
				lblEmailError = new Label("");
				lblEmailError.getStyleClass().add("input-error");
				lblEmailError.setVisible(false);
				GridPane.setHalignment(lblEmailError, HPos.RIGHT);
				GridPane.setValignment(lblEmailError, VPos.BOTTOM);
				lblEmailError.setPadding(new Insets(0));
			}

			return lblEmailError;
		}
		
		public void registerUser(User oldUser, User newUser) throws DuplicateUserPseudoException {
			try {
				UserManagerSingleton.getInstance().updateUser(oldUser, newUser);
		
			} catch (DuplicateUserEmailException e) {
				setLblText(getLblEmailError(), "This email is already taken.");
				getLblEmailError().setVisible(true);

				e.printStackTrace();

			} catch (DuplicateUserException e) {
				
				setLblText(getLblEmailError(), "This email is already taken.");
				getLblEmailError().setVisible(true);

				e.printStackTrace();
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
		public void setLblText(Label label, String message) {
			if (label != null)
				if (message.length() > 0)
					label.setText(message);
		}
		
		
		public void hideAndClearChangingFields() {
			getTxtChangeEmail().setVisible(false);
			getPwdChangePassword().setVisible(false);
			getBtnSaveEmail().setVisible(false);
			getBtnSavePassword().setVisible(false);
			getBtnEmailChange().setVisible(true);
			getBtnPasswordChange().setVisible(true);
			getTxtChangeEmail().clear();
			getPwdChangePassword().clear();
		}
	

}

