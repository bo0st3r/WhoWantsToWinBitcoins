package view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.ValueAxis;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderImage;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.Earning;
import model.Party;
import model.Question;

public class PyramidVBox extends GridPane {

	private List<Label> gain;
	private Paint blanc = Color.rgb(255,255,255);
	private Paint rouge = Color.rgb(255, 0, 0);

	public PyramidVBox() {
		
		// add columns
		ColumnConstraints c = new ColumnConstraints();
		c.setPercentWidth(100);
		this.getColumnConstraints().addAll(c);
		this.setPadding(new Insets(10));

		// add rows
		RowConstraints r = new RowConstraints();
		r.setPercentHeight(10);
		this.getRowConstraints().addAll(r, r, r, r, r, r, r, r, r, r,r,r,r,r,r);
		
		this.setGridLinesVisible(true);
		
		for (int i = Party.NB_STEPS-1 ; i>=0 ; i--) {
			this.add(getGain(i), 0, i);
			this.getGain(i).setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
			this.getGain(i).setAlignment(Pos.CENTER);
			
		}
		
		//layout
		this.setPrefWidth(Integer.MAX_VALUE);
		this.setBackground(new Background(new BackgroundFill(blanc, CornerRadii.EMPTY, Insets.EMPTY)));
		this.getGain(5).setTextFill(rouge);
		this.getGain(10).setTextFill(rouge);
		this.getGain(5).setStyle("-fx-font-weight: bold;");
		this.getGain(10).setStyle("-fx-font-weight: bold;");
		
		
		
	}

	public Label getGain(int index) {
		if (gain == null) {
			
			gain = new ArrayList<>();
			
			
			for (int i = Party.NB_STEPS-1 ; i>=0 ; i--) {
				gain.add( new Label((i + 1) +"        "+PlayingGridPane.getEarning().getAmount(i)));
				
			}

		}
		return gain.get(index);

	}

}
