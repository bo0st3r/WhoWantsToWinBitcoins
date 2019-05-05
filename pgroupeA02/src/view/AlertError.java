package view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

public class AlertError extends Alert {
	public AlertError(String string) {
		super(AlertType.NONE, string, ButtonType.OK);
		initModality(Modality.APPLICATION_MODAL);
		initStyle(StageStyle.UTILITY);
	}
}