package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class AboutGridPane extends GridPane {

	private Button btnPrevious;
	private Label lblName;

	/*
	 * Constructor. Add rows and cols constraints plus the pane content.
	 */
	public AboutGridPane() {
//		this.setGridLinesVisible(true);

		// Set columns
		ColumnConstraints c = new ColumnConstraints();
		c.setPercentWidth(10);
		this.getColumnConstraints().addAll(c, c, c, c, c, c, c, c, c, c);

		// Set rows
		RowConstraints r = new RowConstraints();
		r.setPercentHeight(10);
		this.getRowConstraints().addAll(r, r, r, r, r, r, r, r, r, r);

		// Label
		this.add(getLblname(), 3, 1, 3, 1);

		// Previous button
		this.add(getBtnPrevious(), 4, 8, 2, 2);

	}

	/*
	 * If null instantiate btnPrevious, then return it. Define it's action when
	 * using it.
	 * 
	 * @return The previous Button
	 */
	public Button getBtnPrevious() {
		if (btnPrevious == null) {
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

	/*
	 * If null instantiate lblName, then return it. Define it's action when using
	 * it.
	 * 
	 * @return The name Label
	 */
	public Label getLblname() {
		if (lblName == null) {
			lblName = new Label("----De Bels Lieven , Decorte Bastien, Draux Elsa");
		}
		return lblName;
	}

}
