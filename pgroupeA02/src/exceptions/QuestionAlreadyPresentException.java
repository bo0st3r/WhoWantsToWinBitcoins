package exceptions;

public class QuestionAlreadyPresentException extends Exception {
	private String statement;

	public QuestionAlreadyPresentException(String statement) {
		super("A QuestionAlreadyPresentException has occured because the question \"");
		this.statement = statement;
	}

	public String getMessage() {
		return super.getMessage() + statement + "\" is already present in the deck.";
	}
}