package exceptions;

public class TooMuchAnswersException extends Exception {
	private static final long serialVersionUID = 4823146638067044493L;
	private String statement;

	public TooMuchAnswersException(String statement) {
		super("A TooMuchAnswersException has occured because the question : \"");
		this.statement = statement;
	}

	public String getMessage() {
		return super.getMessage() + statement + "\" already has 4 answers.";
	}
}
