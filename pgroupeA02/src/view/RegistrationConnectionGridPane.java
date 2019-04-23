package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;

import javax.lang.model.element.Element;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

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
import model.User;
import model.UserManagement;
import utilities.Serialization;


public class RegistrationConnectionGridPane extends GridPane{
	
	private Label lblTitlePaneRegistration;
	private Label lblTitleConnection;
	private Label lblPseudoConnection;
	private TextField txtPseudoConnection;
	private Label lblPasswordConnection;
	private PasswordField pwdPasswordConnection;
	private Button btnConnection;
	private Label lblTitleRegistration;
	private Label lblPseudoRegistration;
	private TextField txtPseudoRegistration;
	private Label lblPasswordRegistration;
	private PasswordField pwdPasswordRegistration;
	private Label lblEmail;
	private TextField txtEmail;
	private Button btnValidate;
	private Button btnPrevious;
	private Button btnPrevious2;
	
	
	
	public RegistrationConnectionGridPane() {
		
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
		
		//title
		this.add(getLblTitlePaneRegistration(),4,0,2,1);
		
		
		//////////////////Part connection
		this.add(getLblTitleConnection(), 3, 1, 2, 1);
		GridPane.setValignment(getLblTitleConnection(), VPos.BOTTOM);
		//pseudo
		this.add(getLblPseudoConnection(), 2, 2);
		this.add(getTxtPseudoConnection(), 3, 2, 4, 1);
		GridPane.setHalignment(getLblPseudoConnection(), HPos.RIGHT);
		GridPane.setValignment(getLblPseudoConnection(), VPos.BOTTOM);
		GridPane.setValignment(getTxtPseudoConnection(), VPos.BOTTOM);
		getTxtPseudoConnection().setPromptText("Enter your pseudo");
		//password
		this.add(getLblPasswordConnection(), 2, 3);
		this.add(getPwdPasswordConnection(), 3, 3, 4, 1);
		GridPane.setHalignment(getLblPasswordConnection(), HPos.RIGHT);
		GridPane.setValignment(getLblPasswordConnection(), VPos.TOP);
		GridPane.setValignment(getPwdPasswordConnection(), VPos.TOP);
		getPwdPasswordConnection().setPromptText("Enter your password");
		//button
		this.add(getBtnConnection(), 5, 4, 2, 1);
		this.add(getBtnPrevious(), 4, 4);
		GridPane.setHalignment(getBtnConnection(), HPos.RIGHT);
		GridPane.setValignment(getBtnConnection(), VPos.TOP);
		GridPane.setHalignment(getBtnPrevious(), HPos.RIGHT);
		GridPane.setValignment(getBtnPrevious(), VPos.TOP);
		
		
		////////////////part registration
		this.add(getLblTitleRegistration(), 3, 5, 2, 1);
		GridPane.setValignment(getLblTitleRegistration(), VPos.BOTTOM);
		//pseudo
		this.add(getLblPseudoRegistration(), 2, 6);
		this.add(getTxtPseudoRegistration(), 3, 6, 4, 1);
		getTxtPseudoRegistration().setPromptText("Choose pseudo");
		GridPane.setHalignment(getLblPseudoRegistration(), HPos.RIGHT);

		//password
		this.add(getLblPasswordRegistration(), 2, 7);
		this.add(getPwdPasswordRegistration(), 3, 7, 4, 1);
		getPwdPasswordRegistration().setPromptText("Choose password");
		GridPane.setHalignment(getLblPasswordRegistration(), HPos.RIGHT);
		this.add(getLblEmail(), 2, 8);
		this.add(getTxtEmail(), 3, 8, 4, 1);
		GridPane.setHalignment(getLblEmail(), HPos.RIGHT);
		getTxtEmail().setPromptText("Enter your Email");
		//button
		this.add(getBtnValidate(), 5, 9, 2, 1);
		this.add(getBtnPrevious2(), 4, 9);
		GridPane.setHalignment(getBtnValidate(), HPos.RIGHT);
		GridPane.setValignment(getBtnValidate(), VPos.TOP);
		GridPane.setHalignment(getBtnPrevious2(), HPos.RIGHT);
		GridPane.setValignment(getBtnPrevious2(), VPos.TOP);
		
		
		
	}



	public Label getLblTitlePaneRegistration() {
		if(lblTitlePaneRegistration==null) {
			lblTitlePaneRegistration = new Label("Who wants to win Bitcoins?");
			lblTitlePaneRegistration.setId("titleRegistrationPane");
		}
		return lblTitlePaneRegistration;
	}



	public Label getLblTitleConnection() {
		if (lblTitleConnection == null) {
			lblTitleConnection = new Label("Already registered?");
			lblTitleConnection.setId("titleForRegistration");
		}
		return lblTitleConnection;
	}



	public Label getLblPseudoConnection() {
		if (lblPseudoConnection == null) {
			lblPseudoConnection = new Label("Pseudo");
			
		}
		return lblPseudoConnection;
	}



	public TextField getTxtPseudoConnection() {
		if (txtPseudoConnection==null) {
			txtPseudoConnection = new TextField();
		}
		return txtPseudoConnection;
	}



	public Label getLblPasswordConnection() {
		if (lblPasswordConnection == null) {
			lblPasswordConnection = new Label("Password");
		}
		return lblPasswordConnection;
	}



	public PasswordField getPwdPasswordConnection() {
		if (pwdPasswordConnection == null) {
			pwdPasswordConnection = new PasswordField();
		}
		return pwdPasswordConnection;
	}
	
	
	public Button getBtnConnection() {
		if (btnConnection==null) {
			btnConnection = new Button("Connection");
		}
		return btnConnection;
	}



	public Label getLblTitleRegistration() {
		if (lblTitleRegistration == null) {
			lblTitleRegistration = new Label("Not yet registered? create an account");
			lblTitleRegistration.setId("titleForRegistration");
		}
		return lblTitleRegistration;
	}



	public Label getLblPseudoRegistration() {
		if (lblPseudoRegistration == null) {
			lblPseudoRegistration = new Label("Pseudo");
		}
		return lblPseudoRegistration;
	}



	public TextField getTxtPseudoRegistration() {
		if (txtPseudoRegistration==null) {
			txtPseudoRegistration = new TextField();
		}
		return txtPseudoRegistration;
	}



	public Label getLblPasswordRegistration() {
		if (lblPasswordRegistration == null) {
			lblPasswordRegistration = new Label("Password");
		}
		return lblPasswordRegistration;
	}



	public PasswordField getPwdPasswordRegistration() {
		if (pwdPasswordRegistration==null) {
			pwdPasswordRegistration = new PasswordField();
		}
		return pwdPasswordRegistration;
	}



	public Label getLblEmail() {
		if (lblEmail==null) {
			lblEmail = new Label("E-Mail");
		}
		return lblEmail;
	}



	public TextField getTxtEmail() {
		if (txtEmail == null) {
			txtEmail = new TextField();
		}
		return txtEmail;
	}



	public Button getBtnValidate() {
		if (btnValidate==null) {
			btnValidate = new Button("Validate");
			btnValidate.setOnAction(new EventHandler<ActionEvent>() {
				

				@Override
				public void handle(ActionEvent event) {
					setVisible(false);
					((ProjStackPane) getParent().getParent()).getHomeGridPane().setVisible(true);
					//Actualiser pour table de score
					UserManagement.addUser(createUser());
					//Serialization.UserToJson(createUser(),"UserJson");
			
				}
			});;
		}
		return btnValidate;
	}


	/**
	 * If null instantiate btnPrevious, then return it. Define it's action when
	 * using it.
	 * @return The previous Button
	 */
	public Button getBtnPrevious() {
		if (btnPrevious==null) {
			btnPrevious = new Button("Previous");
			btnPrevious.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					setVisible(false);
					((ProjStackPane) getParent().getParent()).getHomeGridPane().setVisible(true);
					
				}
			});
		}
		return btnPrevious;
	}
	
	public Button getBtnPrevious2() {
		if (btnPrevious2==null) {
			btnPrevious2 = new Button("Previous");
			
			btnPrevious2.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					setVisible(false);
					((ProjStackPane) getParent().getParent()).getHomeGridPane().setVisible(true);
					
					
				}
			});
		}
		return btnPrevious2;
	}
	/**
	 * gets the pseudo, password and email address of a created user
	 * @return return a new user, to add it to the json file
	 */
	public User createUser(){
	
		String pseudo = getTxtPseudoRegistration().getText();
		
		String password = getPwdPasswordRegistration().getText();
		
		String email = getTxtEmail().getText();
	
		return new User(pseudo, password, email);
	}	
	
}