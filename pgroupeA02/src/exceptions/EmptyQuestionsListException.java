package exceptions;

public class EmptyQuestionsListException extends RuntimeException {
	private static final long serialVersionUID = -792196609150547277L;

	public EmptyQuestionsListException() {
		super("A EmptyQuestionsListException has occured because the questions list is empty.");
	}

}
