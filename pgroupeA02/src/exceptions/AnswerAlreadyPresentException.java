package exceptions;

public class AnswerAlreadyPresentException extends Exception {
	private static final long serialVersionUID = 6949230379425496752L;
	private String answer;
	private String statement;

	public AnswerAlreadyPresentException(String statement, String answer) {
		super("An AnswerAlreadyPresentException has occured because the answer \"");
		this.answer = answer;
		this.statement = statement;
	}

	public String getMessage() {
		return super.getMessage() + answer + "\" is already present for the question : \"" + statement + "\".";
	}
}