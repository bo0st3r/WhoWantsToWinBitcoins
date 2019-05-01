package exceptions;

public class DuplicateUserPseudoException extends Exception {
	private static final long serialVersionUID = -4813044275767911834L;

	public DuplicateUserPseudoException() {
		super("User pseudo already used when trying to add it to \"users\" field from an UserManager object.");
	}
}
