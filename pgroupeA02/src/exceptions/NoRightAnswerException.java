package exceptions;

public class NoRightAnswerException extends Exception {
	private static final long serialVersionUID = 7886954901322413242L;
	private String statement;

	public NoRightAnswerException(String statement) {
		super("A NoRightAnswerException has occured because the question \"");
		this.statement = statement;
	}

	@Override
	public String getMessage() {
		return super.getMessage() + statement + "\" has no right answer.";
	}
}
