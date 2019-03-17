package exceptions;

import model.Party;

public class DeckUnderFilledException extends Exception {
	private static final long serialVersionUID = -4465061752767277294L;

	public DeckUnderFilledException(int deckSize) {
		super("A DeckUnderFilledException has occured because the Deck used to instanciate a Party has " + deckSize);
	}

	public String getMessage() {
		return super.getMessage()+" questions in it's list but it requires exactly " + Party.NB_STEPS + ".";
	}
}
