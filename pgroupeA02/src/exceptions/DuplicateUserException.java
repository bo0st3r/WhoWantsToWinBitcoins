package exceptions;

public class DuplicateUserException extends Exception {
	private static final long serialVersionUID = -1946125323897603639L;

	public DuplicateUserException() {
		super("User pseudo and email are already used when trying to add it to \"users\" field from an UserManager object.");
	}
}
