package exceptions;

import java.util.regex.Pattern;

public class InputSyntaxException extends Exception {
	private static final long serialVersionUID = -6364967702967566404L;

	public InputSyntaxException() {
		super("User's input does not fit it's relevant syntax pattern.");
	}

	public InputSyntaxException(String s) {
		super(s);
	}

	public InputSyntaxException(String s, Pattern p) {
		super(s + "\n" + p.pattern());
	}
}
