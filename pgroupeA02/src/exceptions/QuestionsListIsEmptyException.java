package exceptions;

public class QuestionsListIsEmptyException extends Exception {
	private static final long serialVersionUID = -792196609150547277L;

	public QuestionsListIsEmptyException() {
		super("A QuestionsListIsEmptyException has occured because the questions list is empty.");
	}

}
