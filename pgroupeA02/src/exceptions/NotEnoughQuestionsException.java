package exceptions;

import enumerations.Round;
import model.Party;

public class NotEnoughQuestionsException extends Exception {
	private static final long serialVersionUID = 5486210757896599145L;

	public NotEnoughQuestionsException(Round round, int nbQuestions) {
		super("A NotEnoughQuestionsException has occured because the Party has only " + nbQuestions
				+ " questions for the round " + round.getRoundStatement() + " but requires exactly "
				+ Party.NB_STEPS_BY_ROUND + "questions.");
	}
}
