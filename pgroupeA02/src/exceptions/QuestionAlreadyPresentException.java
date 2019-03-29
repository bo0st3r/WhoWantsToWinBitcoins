package exceptions;

public class QuestionAlreadyPresentException extends Exception {
	private static final long serialVersionUID = -273369812921936398L;
	private String statement;

	public QuestionAlreadyPresentException(String statement) {
		super("A QuestionAlreadyPresentException has occured because the question \"");
		this.statement = statement;
	}

	@Override
	public String getMessage() {
		return super.getMessage() + statement + "\" is already present in the deck.";
	}
}