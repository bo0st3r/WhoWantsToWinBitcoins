package exceptions;

public class RightAnswerAlreadyPresentException extends Exception {
	private static final long serialVersionUID = -1672384036925512943L;
	private String statement;

	public RightAnswerAlreadyPresentException(String statement) {
		super("A RightAnswerAlreadyPresentException has occured because the question \"");
		this.statement = statement;
	}

	public String getMessage() {
		return super.getMessage() + statement + "\" already has it's right answer.";
	}
}