package view;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

public class TimerFlowPane extends FlowPane {
	private static final int INITIAL_NB_SECONDS = 60;
	private int nbSeconds;
	private Label lblTimer;

	/*
	 * Constructor. Adds the content to the pane.
	 */
	public TimerFlowPane() {
		getChildren().add(getLblTimer());
	}

	/*
	 * Run the timer, but first set nbSeconds value as INITIAL_NB_SECONDS value.
	 * When nbSeconds drop down to 0, stops the timer, sets it invisible and stop
	 * the party. When nbSeconds value is -1, cancel the timer.
	 */
	public void runTimer() {
		nbSeconds = INITIAL_NB_SECONDS;

		Timer timer = new Timer(true);
		TimerTask timerTask = new TimerTask() {

			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {

						if (nbSeconds > 0) {
							lblTimer.setText(Integer.toString(nbSeconds));
							nbSeconds--;
						} else if (nbSeconds == 0) {
							stopTimer();
							((PartyStackPane) getParent().getParent().getParent()).partyLost();
						} else {
							timer.cancel();
						}
					}
				});

			}
		};

		timer.scheduleAtFixedRate(timerTask, 0, 1000);
	}

	/*
	 * Resets nbSeconds value to INITIAL_NB_SECONDS.
	 */
	public void resetNbSecond() {
		nbSeconds = INITIAL_NB_SECONDS;
	}

	/*
	 * Sets nbSeconds value to -1 in order to cancel the timer.
	 */
	public void stopTimer() {
		nbSeconds = -1;
	}

	/*
	 * Pause the timer for the specified time.
	 * 
	 * @param time, the specified time in ms.
	 */
	public void pauseTimer(long time) throws InterruptedException {
		Thread.currentThread().wait(time);
	}

	/*
	 * If null instantiates lblTimer, then returns it.
	 * 
	 * @return lblTimer, a Label object that is the timer.
	 */
	public Label getLblTimer() {
		if (lblTimer == null) {
			lblTimer = new Label();
		}

		return lblTimer;
	}
}
