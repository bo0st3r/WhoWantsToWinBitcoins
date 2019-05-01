package exceptions;

public class NullUserException extends RuntimeException {
	private static final long serialVersionUID = -7734922916899311461L;

	public NullUserException() {
		super("Null user while trying to add it to \"users\" field from an UserManager object");
	}
}
