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
	public static final int NB_STEPS = 15;
	public static final int NB_STEPS_BY_ROUND = 5;

	private List<Question> choosenQuestions;
	private Round actualRound;
	private int actualStep;

	private int rightAnswerIndex;
	private String rightAnswer;

	private int jokerFriendIndex;
	private List<Double> jokerPublicPercents;
	private List<Integer> joker5050Indexes;

	/*
	 * Constructor of Party class. The param "choosenQuestions" gets instantiated as
	 * an empty ArrayList<Question>. Gets Party.NB_STEPS_BY_ROUND questions for each
	 * Round and then sort is by Round order.
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

		List<Question> questionsList = deck.getQuestions();
		choosenQuestions = new ArrayList<Question>();
		// Shuffle the list of questions to randomize the 15 questions set.
		Collections.shuffle(questionsList);

		// Adding questions to choosenQuestions
		for (Question q : questionsList) {
			switch (q.getRound()) {

			case FIRST_ROUND:
				if (getNbQuestionsForRound(Round.FIRST_ROUND) < 5 && !choosenQuestions.contains(q))
					choosenQuestions.add(q);
				break;

			case SECOND_ROUND:
				if (getNbQuestionsForRound(Round.SECOND_ROUND) < 5 && !choosenQuestions.contains(q))
					choosenQuestions.add(q);
				break;

			case LAST_ROUND:
				if (getNbQuestionsForRound(Round.LAST_ROUND) < 5 && !choosenQuestions.contains(q))
					choosenQuestions.add(q);
				break;
			}

		}

		// Verify there's exactly 5 questions for each Round
		for (Round r : Round.values()) {
			if (getNbQuestionsForRound(r) < 5) {
				throw new NotEnoughQuestionsException(r, getNbQuestionsForRound(r));
			} else if (getNbQuestionsForRound(r) > 5) {
				throw new TooMuchQuestionsException(r, getNbQuestionsForRound(r));
			}
		}

		// Sort by rounds order
		sortQuestionsByRounds();

		rightAnswerIndex = -1;
		rightAnswer = null;

		actualStep = 0;
		actualRound = Round.FIRST_ROUND;

		jokerFriendIndex = -1;
		jokerPublicPercents = new ArrayList<>();
		joker5050Indexes = new ArrayList<>();
	}

	/*
	 * Returns the nb of questions in choosenQuestions for the Round passed as an
	 * argument.
	 * 
	 * @param round The Round that will be used.
	 */
	public int getNbQuestionsForRound(Round round) {
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
	 * @throws ExceedMaxStepsException occurs if the actual step is higher than the
	 * Party.NB_STEPS
	 */
	public Question getQuestionNextStep() throws ExceedMaxStepsException {
		if (!hasNextStep())
			throw new ExceedMaxStepsException(actualStep);

		actualStep++;
		if (isNeedingNextRound())
			getNextRound();
		return (choosenQuestions.get(actualStep - 1));
	}

	/*
	 * Returns the next Round
	 */
	public void getNextRound() {
		Round[] rounds = Round.values();
		for (int i = 0; i < rounds.length - 1; i++) {
			if (actualRound == rounds[i]) {
				actualRound = rounds[i + 1];
				break;
			}
		}
	}

	public Round getActualRound() {
		return actualRound;
	}

	/*
	 * Return if the party need to move to the next step.
	 */
	public boolean isNeedingNextRound() {
		return ((actualStep % Party.NB_STEPS_BY_ROUND) - 1 == 0 && actualStep > 1);
	}

	/*
	 * Returns the actual step.
	 */
	public int getActualStep() {
		return actualStep;
	}

	@Override
	public String toString() {
		String tmp = "\tChoosen questions for party : \n";

		if (choosenQuestions != null) {
			for (Question q : choosenQuestions) {
				tmp += q.toString() + "\n";
			}
		}

		return tmp;
	}

	public int getRightAnswerIndex() {
		return rightAnswerIndex;
	}

	public void setRightAnswerIndex(int rightAnswerIndex) {
		if (rightAnswerIndex >= 0 && rightAnswerIndex < Question.NB_ANSWERS)
			this.rightAnswerIndex = rightAnswerIndex;
		else
			;
		// EXCEPTION
	}

	public String getRightAnswer() {
		return rightAnswer;
	}

	public void setRightAnswer(String rightAnswer) {
		if (rightAnswer != null && !rightAnswer.isEmpty())
			this.rightAnswer = rightAnswer;
		else
			;
		// EXCEPTION
	}

	public int getJokerFriendIndex() {
		return jokerFriendIndex;
	}

	public void setJokerFriendIndex(int jokerFriendIndex) {
		if (jokerFriendIndex >= 0 && jokerFriendIndex < Question.NB_ANSWERS)
			this.jokerFriendIndex = jokerFriendIndex;
		else
			;
		// EXCEPTION
	}

	public List<Double> getJokerPublicPercents() {
		return jokerPublicPercents;
	}

	public void setJokerPublicPercents(List<Double> jokerPublicPercents) {
		if (jokerPublicPercents.size() == Question.NB_ANSWERS) {
			for (Double percents : jokerPublicPercents) {
				if (percents < 0 || percents > 100)
					// EXCEPTION
					return;
			}
		} else {
			System.out.println(jokerPublicPercents.size());
			// EXCEPTION
		}
		this.jokerPublicPercents = jokerPublicPercents;
	}

	public List<Integer> getJoker5050Indexes() {
		return joker5050Indexes;
	}

	public void setJoker5050Indexes(List<Integer> joker5050Indexes) {
		if (joker5050Indexes != null) {
			for (Integer index : joker5050Indexes) {
				if (index < 0 || index > Question.NB_ANSWERS - 1)
					// EXCEPTION
					return;
			}
		}
		this.joker5050Indexes = joker5050Indexes;
	}
}
