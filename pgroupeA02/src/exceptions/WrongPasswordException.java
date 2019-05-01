package exceptions;

public class WrongPasswordException extends Exception {
	private static final long serialVersionUID = 1913477070690250120L;

	public WrongPasswordException() {
		super("A wrong password has been given.");
	}

}
