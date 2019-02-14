package exceptions;

public class TooMuchAnswersException extends Exception {
	private String statement;

	public TooMuchAnswersException(String statement) {
		super("A TooMuchAnswersException has occured because the question : \"");
		this.statement = statement;
	}

	public String getMessage() {
		return super.getMessage() + statement + "\" already has 4 answers.";
	}
}
