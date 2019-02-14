package exceptions;

public class RightAnswerAlreadyPresentException extends Exception {
	private String statement;

	public RightAnswerAlreadyPresentException(String statement) {
		super("A RightAnswerAlreadyPresentException has occured because the question \"");
		this.statement = statement;
	}

	public String getMessage() {
		return super.getMessage() + statement + "\" already has the right answer.";
	}
}