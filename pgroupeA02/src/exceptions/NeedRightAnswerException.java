package exceptions;

public class NeedRightAnswerException extends Exception {
	private static final long serialVersionUID = 4980035961175950345L;
	private String statement;
	private String answer;

	public NeedRightAnswerException(String statement, String answer) {
		super("A NeedRightAnswerException has occured while trying to add the fourth answer (\"");
		this.statement = statement;
		this.answer = answer;
	}

	public String getMessage() {
		return super.getMessage() + answer + "\") for the question : \"" + statement
				+ "\" as false and the 3 others are false too.";
	}

}
