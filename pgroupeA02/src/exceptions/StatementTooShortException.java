package exceptions;

public class StatementTooShortException extends Exception {
	private static final long serialVersionUID = 6240064546626826873L;
	private String statement;

	public StatementTooShortException(String statement) {
		super("A StatementTooShortException has occured because the statement \"");
		this.statement = statement;
	}

	@Override
	public String getMessage() {
		return super.getMessage() + statement + "\" is too short.";
	}
}
