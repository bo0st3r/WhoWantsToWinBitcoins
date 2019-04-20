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
	private int pyramidActualStep;

	/*
	 * Constructor. Sets pyramidActualStep value, cols and rows constraints, pane
	 * content and the pane width.
	 */
	public PyramidGridPane() {
//		setGridLinesVisible(true);

		// First step
		pyramidActualStep = Party.NB_STEPS - 1;

		// Add col
		ColumnConstraints col = new ColumnConstraints();
		col.setPercentWidth(100);
		getColumnConstraints().add(col);

		// Add rows
		RowConstraints row = new RowConstraints();
		row.setPercentHeight((double) 100 / (double) Party.NB_STEPS);
		for (int i = Party.NB_STEPS - 1; i >= 0; i--) {
			getRowConstraints().add(row);
		}

		// Adding steps
		for (int i = Party.NB_STEPS - 1; i >= 0; i--) {
			add(getLblGain(i), 0, i);
		}

		// Default step ID
		getLblGain(Party.NB_STEPS - 1).setId("pyramidActualStep");

		// Layout
		setPrefWidth(Integer.MAX_VALUE);
	}

	/*
	 * If null instantiates lblGain and it's values for each step plus the ID and
	 * size for the step, then returns the value for the specified index.
	 * 
	 * @param index, the index of the required Label.
	 * 
	 * @return lblGain, a Label object for the specified location.
	 */
	public Label getLblGain(int index) {
		if (lblGain == null) {
			lblGain = new ArrayList<>();

			for (int i = Party.NB_STEPS - 1; i >= 0; i--) {
				lblGain.add(new Label((i + 1) + "\t" + PlayingGridPane.getEarning().getAmount(i)));

				int step = Party.NB_STEPS - 1 - i;
				lblGain.get(step).setId("textEarningsPyramid");
				lblGain.get(step).setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);

				if (step % 5 == 0 && step != Party.NB_STEPS) {
					System.out.println(step);
					lblGain.get(step).setId("");
					lblGain.get(step).getStyleClass().add("pyramidTextRound");
				}
			}
		}
		return lblGain.get(index);
	}

	/*
	 * Set the actual step ID as actual step and set the old step to the default ID.
	 */
	public void goToNextStep() {
		// Old earnings
		getLblGain(pyramidActualStep).setId("textEarningsPyramid");
		// Actual earnings
		getLblGain(pyramidActualStep - 1).setId("pyramidActualStep");
		pyramidActualStep--;
	}

	/*
	 * Return pyramidActualStep, the actual step for the ongoing party.
	 * 
	 * @return pyramidActualStep, the actual step for the ongoin party.
	 */
	public int getPyramidActualStep() {
		return pyramidActualStep;
	}
}
