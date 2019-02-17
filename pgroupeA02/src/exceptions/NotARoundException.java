package exceptions;

public class NotARoundException extends Exception {
	private static final long serialVersionUID = -1663438657232089939L;

	public NotARoundException() {
		super("A NotARoundException has occured because the \"round\" param passed was not a Round enum.");
	}
}
