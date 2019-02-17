package exceptions;

public class NotAllAnswersException extends Exception {
	private static final long serialVersionUID = 2813524328554145130L;
	private String statement;
	
	public NotAllAnswersException(String statement) {
		super("A NotAllAnswersException occured while trying to add the question : \"");
		this.statement=statement;
	}
	
	public String getMessage() {
		return super.getMessage() + statement + "\"  to a deck but it has less than 4 possible answers.";
	}
}
