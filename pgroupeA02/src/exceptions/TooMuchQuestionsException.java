package exceptions;

import enumerations.Round;
import model.Party;

public class TooMuchQuestionsException extends Exception {
	private static final long serialVersionUID = -328395467319113763L;

	public TooMuchQuestionsException(Round round, int nbQuestions) {
		super("A NotEnoughQuestionsException has occured because the Party has " + nbQuestions
				+ " questions for the round " + round.getRoundStatement() + " but requires exactly "
				+ Party.NB_STEPS_BY_ROUND + " questions.");
	}
}
