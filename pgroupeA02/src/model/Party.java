package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import enumerations.Round;
import exceptions.DeckUnderFilledException;
import exceptions.ExceedMaxStepsException;
import exceptions.NotEnoughQuestionsException;
import exceptions.QuestionsListIsEmptyException;
import exceptions.TooMuchQuestionsException;

public class Party {
	private List<Question> choosenQuestions;
	private int actualStep = 1;
	public static final int NB_STEPS = 15;
	public static final int NB_STEPS_BY_ROUND = 5;

	/*
	 * Constructor of Party class. The param "choosenQuestions" just gets
	 * instantiated as an empty ArrayList<Question>. Gets Party.NB_STEPS_BY_ROUND
	 * questions for each Round and then sort is by Round order.
	 * 
	 * @param deck A Deck that contains at least 15 questions, including 5 for each
	 * Round parameter.
	 * 
	 * @throws QuestionsListIsEmptyException occurs when the list of questions from
	 * Deck is empty.
	 * 
	 * @throws DeckUnderFilledException occurs when there's not enough questions to
	 * fit the requirement of Party.NB_STEPS for the Party.
	 * 
	 * @throws NotEnoughQuestionsException occurs when a Round has less questions
	 * than Party.NB_STEPS_BY_ROUND asks it to have.
	 * 
	 * @throws NotEnoughQuestionsException occurs when a Round has more questions
	 * than Party.NB_STEPS_BY_ROUND asks it to have.
	 */
	public Party(Deck deck) throws QuestionsListIsEmptyException, DeckUnderFilledException, NotEnoughQuestionsException,
			TooMuchQuestionsException {
		if (deck.getQuestions() == null)
			throw new QuestionsListIsEmptyException();

		if (deck.getQuestions().size() < 15)
			throw new DeckUnderFilledException(deck.getQuestions().size());

		choosenQuestions = new ArrayList<Question>();
		// Shuffle the list of questions to randomize the 15 questions set.
		Collections.shuffle(deck.getQuestions());

		// Adding questions to choosenQuestions
		for (Question q : deck.getQuestions()) {
			switch (q.getRound()) {

			case FIRST_ROUND:
				if (getNbQuestionsRound(Round.FIRST_ROUND) < 5 && !choosenQuestions.contains(q))
					choosenQuestions.add(q);
				break;

			case SECOND_ROUND:
				if (getNbQuestionsRound(Round.SECOND_ROUND) < 5 && !choosenQuestions.contains(q))
					choosenQuestions.add(q);
				break;

			case LAST_ROUND:
				if (getNbQuestionsRound(Round.LAST_ROUND) < 5 && !choosenQuestions.contains(q))
					choosenQuestions.add(q);
				break;
			}
		}

		// Verify there's exactly 5 questions for each Round
		for (Round r : Round.values()) {
			if (getNbQuestionsRound(r) < 5) {
				throw new NotEnoughQuestionsException(r, getNbQuestionsRound(r));
			} else if (getNbQuestionsRound(r) > 5) {
				throw new TooMuchQuestionsException(r, getNbQuestionsRound(r));
			}
		}

		// Sort by rounds order
		sortQuestionsByRounds();
	}

	/*
	 * Returns the nb of questions in choosenQuestions for the Round passed as an
	 * argument.
	 * 
	 * @param round The Round that will be used.
	 */
	public int getNbQuestionsRound(Round round) {
		int nb = 0;
		if (choosenQuestions != null) {
			for (Question q : choosenQuestions) {
				if (q.getRound() == round)
					nb++;
			}
		}
		return nb;
	}

	/*
	 * Sorts the questions by Round enumeration declaration order.
	 */
	public void sortQuestionsByRounds() {
		if (choosenQuestions != null) {
			Collections.sort(choosenQuestions, new Comparator<Object>() {

				@Override
				public int compare(Object o1, Object o2) {
					Question q1 = (Question) o1;
					Round r1 = q1.getRound();

					Question q2 = (Question) o2;
					Round r2 = q2.getRound();
					return Integer.compare(r1.ordinal(), r2.ordinal());
				}

			});
		}
	}

	/*
	 * Returns if the Party has a next step of is ended.
	 */
	public boolean hasNextStep() {
		return actualStep < NB_STEPS;
	}

	/*
	 * Returns the Question for the next step and incremets actualStep by 1.
	 * 
	 * @throws ExceedMaxStepsException occurs if the actual step is higher than the Party.NB_STEPS
	 */
	public Question getQuestionNextStep() throws ExceedMaxStepsException {
		if (actualStep > NB_STEPS) {
			throw new ExceedMaxStepsException(actualStep);
		}

		actualStep++;
		return (choosenQuestions.get(actualStep));
	}

	/*
	 * Returns the actual step.
	 */
	public int getStep() {
		return actualStep;
	}

	public String toString() {
		String tmp = "\tChoosen questions for party : \n";

		if (choosenQuestions != null) {
			for (Question q : choosenQuestions) {
				tmp += q.toString() + "\n";
			}
		}

		return tmp;
	}
}
