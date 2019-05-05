package exceptions;

public class UserNotFoundException extends Exception {
	private static final long serialVersionUID = -4810044275767911834L;

	public UserNotFoundException() {
		super("User not find");
	}
}
