package view;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

public class TimerFlowPane extends FlowPane {
	private static final int INITIAL_NB_SECONDS = 60;
	private Label lblTimer;
	// Time for timer
	private int nbSeconds;

	public TimerFlowPane() {
		this.getChildren().add(getLblTimer());
		nbSeconds = INITIAL_NB_SECONDS;
	}

	public void runChrono() {
		Timer timer = new Timer(true);
		TimerTask timerTask = new TimerTask() {

			@Override
			public void run() {

				Platform.runLater(new Runnable() {

					@Override
					public void run() {

						if (nbSeconds > 0) {
							lblTimer.setText(nbSeconds + "s");
							nbSeconds--;
						} else if (nbSeconds == 0) {
							stopTimer();
							setVisible(false);
							((PlayingGridPane) getParent()).alertPop("Time is over.");
						} else {
							timer.cancel();
						}
					}
				});

			}
		};

		timer.scheduleAtFixedRate(timerTask, 0, 1000);
	}

	public void resetNbSecond() {
		nbSeconds = INITIAL_NB_SECONDS;
	}

	public void stopTimer() {
		nbSeconds = -1;
	}

	public Label getLblTimer() {
		if (lblTimer == null) {
			lblTimer = new Label();
			lblTimer.setId("timer");
		}

		return lblTimer;
	}

}
