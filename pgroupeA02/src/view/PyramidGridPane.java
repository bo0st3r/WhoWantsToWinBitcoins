package view;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.Party;

public class PyramidGridPane extends GridPane {
	private List<Label> lblGain;

	public PyramidGridPane() {
		// setGridLinesVisible(true);

		// add columns
		ColumnConstraints c = new ColumnConstraints();
		c.setPercentWidth(100);
		this.getColumnConstraints().addAll(c);

		// add rows
		RowConstraints r = new RowConstraints();
		r.setPercentHeight(6.66);
		for (int i = Party.NB_STEPS - 1; i >= 0; i--) {
			this.getRowConstraints().add(r);
		}

		for (int i = Party.NB_STEPS - 1; i >= 0; i--) {
			this.add(getLblGain(i), 0, i);
			this.getLblGain(i).setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
			this.getLblGain(i).setAlignment(Pos.CENTER);
		}

		// Layout
		this.setPrefWidth(Integer.MAX_VALUE);
		this.getLblGain(10).getStyleClass().add("pyramidTextRound");
		this.getLblGain(10).setId("");
		this.getLblGain(5).getStyleClass().add("pyramidTextRound");
		this.getLblGain(5).setId("");
		this.getLblGain(0).getStyleClass().add("pyramidTextRound");
		this.getLblGain(0).setId("");
	}

	public Label getLblGain(int index) {
		if (lblGain == null) {
			lblGain = new ArrayList<>();

			for (int i = Party.NB_STEPS - 1; i >= 0; i--) {
				lblGain.add(new Label((i + 1) + "\t" + PlayingGridPane.getEarning().getAmount(i)));
				lblGain.get(Party.NB_STEPS - 1 - i).setId("textEarningsPyramid");
			}
		}
		return lblGain.get(index);
	}

}
