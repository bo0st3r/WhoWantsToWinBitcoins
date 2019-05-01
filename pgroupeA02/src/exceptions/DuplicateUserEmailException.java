package exceptions;

public class DuplicateUserEmailException extends Exception {
	private static final long serialVersionUID = -8800750647889875621L;

	public DuplicateUserEmailException() {
		super("User email already used when trying to add it to \"users\" field from an UserManager object.");
	}
}
