package exceptions;

import model.Party;

public class ExceedMaxStepsException extends Exception {
	private static final long serialVersionUID = 3918587715746762374L;

	public ExceedMaxStepsException(int actualStep) {
		super("An ExceedMaxStepsException has occured because the actual step is " + actualStep + " however the max is "
				+ Party.NB_STEPS);
	}
}
