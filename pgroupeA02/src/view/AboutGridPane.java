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
	private Label lblname;
	
	
	public AboutGridPane() {
		
		//add columns
		ColumnConstraints c = new ColumnConstraints();
		c.setPercentWidth(10);
		this.getColumnConstraints().addAll(c,c,c,c,c,c,c,c,c,c);
		
		//add rows
		RowConstraints r = new RowConstraints();
		r.setPercentHeight(10);
		this.getRowConstraints().addAll(r,r,r,r,r,r,r,r,r,r);
		
		//label 
		this.add(getLblname(), 3, 1, 5,1);
		
		//button 
		this.add(getBtnPrevious(), 4, 8, 2, 2);
		
		
	}

	public Button getBtnPrevious() {
		if (btnPrevious==null) {
			btnPrevious = new Button("Previous");
			btnPrevious.setOnAction(new EventHandler<ActionEvent>() {
						
				@Override
				public void handle(ActionEvent event) {
					
					setVisible(false);
					((ProjStackPane)getParent().getParent()).getHomeGridPane().setVisible(true);
					
				}
			});
		}
		return btnPrevious;
	}



	public Label getLblname() {
		if (lblname==null) {
			lblname= new Label("De Bels Lieven , Decorte Bastien, Draux Elsa");
		}
		return lblname;
	}

}
