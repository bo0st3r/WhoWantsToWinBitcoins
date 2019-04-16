package view;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import model.Party;

public class PyramidGridPane extends GridPane {
	private List<Label> lblGain;

	public PyramidGridPane() {
//		setGridLinesVisible(true);

		// Add col
		ColumnConstraints col = new ColumnConstraints();
		col.setPercentWidth(100);
		this.getColumnConstraints().add(col);

		// Add rows
		RowConstraints row = new RowConstraints();
		row.setPercentHeight((double) 100 / (double) Party.NB_STEPS);
		for (int i = Party.NB_STEPS - 1; i >= 0; i--) {
			this.getRowConstraints().add(row);
		}

		// Adding steps
		for (int i = Party.NB_STEPS - 1; i >= 0; i--) {
			this.add(getLblGain(i), 0, i);
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
				lblGain.get(Party.NB_STEPS - 1 - i).setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
			}
		}
		return lblGain.get(index);
	}

}
