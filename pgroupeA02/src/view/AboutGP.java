package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class AboutGP extends GridPane {

	private Label lblTitle;
	private Label lblText;
	private Button btnHome;
//	private ScrollPane scpAbout;

	/*
	 * Constructor. Add rows and cols constraints plus the pane content.
	 */
	public AboutGP() {
//		this.setGridLinesVisible(true);

		// Set columns
		ColumnConstraints c = new ColumnConstraints();
		c.setPercentWidth(10);
		this.getColumnConstraints().addAll(c, c, c, c, c, c, c, c, c, c);

		// Set rows
		RowConstraints r = new RowConstraints();
		r.setPercentHeight(10);
		this.getRowConstraints().addAll(r, r, r, r, r, r, r, r, r, r);

		// title
		this.add(getLblTitle(), 4, 0, 2, 1);

		// Previous button
		this.add(getBtnHome(), 4, 8, 2, 2);
		
		add(getLblText(), 1, 2, 8, 3);

	}

	/**
	 * If null instantiate btnHome, then return it. Define it's action when
	 * using it.
	 * 
	 * @return The Home Button
	 */
	public Button getBtnHome() {
		if (btnHome == null) {
			btnHome = new Button("Previous");
			GridPane.setHalignment(getBtnHome(), HPos.CENTER);
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

	/**
	 * If null instantiate lblTitke, then return it. Define it's action when using
	 * it.
	 * 
	 * @return The Title Label
	 */
	public Label getLblTitle() {
		if (lblTitle == null) {
			lblTitle = new Label("About");
			lblTitle.getStyleClass().add("title-large");
			GridPane.setHalignment(getLblTitle(), HPos.CENTER);
		}
		return lblTitle;
	}
	
	public Label getLblText() {
		if (lblText == null) {
			lblText = new Label("This game was developed by Bastien Decorte, Elsa Draux and Lieven De Bels.\r\n\n" + 
					"Software used: Photoshop, Gimp, Eclipse and Greenshot.\r\n" + 
					"\r\n" + 
					"Developed in Java (JavaFx, CSS)");

			lblText.setId("AboutText");
			GridPane.setHalignment(getLblText(), HPos.CENTER);

		}

		return lblText;
	}
	
	
//	public ScrollPane getScpAbout() {
//		if (scpAbout == null) {
//			scpAbout = new ScrollPane();
//			scpAbout.setPadding(new Insets(5));
//			scpAbout.setFitToWidth(true);
//			scpAbout.setContent(getLblText());
//
//			scpAbout.setId("rulesPane");
//		}
//		return scpAbout;
//	}

	
}
