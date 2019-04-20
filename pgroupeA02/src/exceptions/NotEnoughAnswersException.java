package exceptions;

public class NotEnoughAnswersException extends Exception {
	private static final long serialVersionUID = 2813524328554145130L;
	private String statement;

	public NotEnoughAnswersException(String statement) {
		super("A NotEnoughAnswersException occured while trying to add the question : \"");
		this.statement = statement;
	}

	@Override
	public String getMessage() {
		return super.getMessage() + statement + "\"  to a deck but the question had less than 4 possible answers.";
	}
}
