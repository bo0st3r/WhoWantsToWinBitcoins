package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;



public class ProfilGP extends GridPane{
	
	private Label lblTitle;
	private Label lblTitlePseudo;
	private Label lblPseudo;
	private Label lblTitlePassword;
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
	private Button btnSave;
	private Button btnPrevious;
	

	
	public ProfilGP() {

	this.setGridLinesVisible(true);

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
	
	this.add(getLblTitle(), 4, 0, 2, 1);
	this.add(getLblTitlePseudo(), 2, 1);
	this.add(getLblPseudo(), 4, 1);
	
	this.add(getLblTitlePassword(), 2, 2);
	this.add(getPwdChangePassword(), 6, 2);
	
	this.add(getLblTitleEmail(), 2, 3);
	this.add(getLblEmail(), 4, 3);
	this.add(getTxtChangeEmail(), 6, 3);
	
	this.add(getLblTitlePartiesPlayed(), 2, 4);
	this.add(getLblPartiesPlayed(), 4, 4);
	
	this.add(getLblTitlePartiesWon(), 2, 5);
	this.add(getLblPartiesWon(), 4, 5);
	
	this.add(getLblTitleHighestEarningsWon(), 2, 6);
	this.add(getLblHighestEarningsWon(), 4, 6);
	
	this.add(getLblTitleTotalEarningsWon(), 2, 7);
	this.add(getLblTotalEarningsWon(), 4, 7);
	
	this.add(getBtnSave(), 3, 8);
	this.add(getBtnPrevious(), 5, 8);
	
	
	
	}



	public Label getLblTitle() {
		if (lblTitle==null) {
			lblTitle=new Label("Profil");
			GridPane.setHalignment(getLblTitle(), HPos.CENTER);
		}
		return lblTitle;
	}



	public Label getLblTitlePseudo() {
		if (lblTitlePseudo==null) {
			lblTitlePseudo = new Label("Pseudo");
		}
		return lblTitlePseudo;
	}



	public Label getLblPseudo() {
		if (lblPseudo==null) {
			lblPseudo = new Label("unknown");
		}
		return lblPseudo;
	}



	public Label getLblTitlePassword() {
		if(lblTitlePassword==null) {
			lblTitlePassword = new Label("Password");
		}
		return lblTitlePassword;
	}



	public PasswordField getPwdChangePassword() {
		if(pwdChangePassword==null) {
			pwdChangePassword= new PasswordField();
			pwdChangePassword.setPromptText("New Password");
		}
		return pwdChangePassword;
	}



	public Label getLblTitleEmail() {
		if(lblTitleEmail==null) {
			lblTitleEmail = new Label("Email");
		}
		return lblTitleEmail;
	}



	public Label getLblEmail() {
		if (lblEmail==null) {
			lblEmail = new Label("unknown");
		}
		return lblEmail;
	}



	public TextField getTxtChangeEmail() {
		if (txtChangeEmail==null) {
			txtChangeEmail = new TextField();
			txtChangeEmail.setPromptText("New Email");
		}
		return txtChangeEmail;
	}



	public Label getLblTitlePartiesPlayed() {
		if (lblTitlePartiesPlayed == null) {
			lblTitlePartiesPlayed = new Label("Parties played :");
		}
		return lblTitlePartiesPlayed;
	}



	public Label getLblPartiesPlayed() {
		if (lblPartiesPlayed==null) {
			lblPartiesPlayed = new Label("No one");
		}
		return lblPartiesPlayed;
	}



	public Label getLblTitlePartiesWon() {
		if (lblTitlePartiesWon==null) {
			lblTitlePartiesWon = new Label("Parties won :");
		}
		return lblTitlePartiesWon;
	}



	public Label getLblPartiesWon() {
		if (lblPartiesWon==null) {
			lblPartiesWon = new Label("No one");
		}
		return lblPartiesWon;
	}



	public Label getLblTitleHighestEarningsWon() {
		if (lblTitleHighestEarningsWon == null) {
			lblTitleHighestEarningsWon = new Label("Highest earnings won :");
		}
		return lblTitleHighestEarningsWon;
	}



	public Label getLblHighestEarningsWon() {
		if (lblHighestEarningsWon==null) {
			lblHighestEarningsWon = new Label("No earnings");
		}
		return lblHighestEarningsWon;
	}



	public Label getLblTitleTotalEarningsWon() {
		if (lblTitleTotalEarningsWon==null) {
			lblTitleTotalEarningsWon = new Label("Total earnings won :");
		}
		
		return lblTitleTotalEarningsWon;
	}

	public Label getLblTotalEarningsWon() {
		if (lblTotalEarningsWon == null) {
			lblTotalEarningsWon = new Label("No earnings");
		}
		return lblTotalEarningsWon;
	}



	public Button getBtnSave() {
		if(btnSave == null) {
			btnSave= new Button("Save");
			GridPane.setHalignment(getBtnSave(), HPos.CENTER);
		}
		return btnSave;
	}



	public Button getBtnPrevious() {
		if(btnPrevious==null) {
			btnPrevious = new Button("Previous");
			GridPane.setHalignment(getBtnPrevious(), HPos.CENTER);
			btnPrevious.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					setVisible(false);
					((ProjSP) getParent().getParent()).getHomeGridPane().setVisible(true);

				}
			});
		}
		return btnPrevious;
	}
	

}

