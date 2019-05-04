package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import enumerations.Round;
import exceptions.DeckUnderFilledException;
import exceptions.EmptyQuestionsListException;
import exceptions.ExceedMaxStepsException;
import exceptions.NotEnoughQuestionsException;
import exceptions.TooMuchQuestionsException;

public class Party {
	public static final int NB_STEPS = 15;
	public static final int NB_STEPS_BY_ROUND = 5;
	public static final String FILE_NAME = "main_deck";

	private List<Question> chosenQuestions;
	private Round actualRound;
	private int actualStep;

	private int rightAnswerIndex;
	private String rightAnswer;

	private int jokerFriendIndex;
	private List<Double> jokerPublicPercents;
	private List<Integer> joker5050Indexes;

	/**
	 * Constructor of Party class. The param "chosenQuestions" gets instantiated as
	 * an empty ArrayList<Question>. Gets Party.NB_STEPS_BY_ROUND questions for each
	 * Round and then sort is by Round order.
	 * 
	 * @param deck A Deck that contains at least 15 questions, including 5 for each
	 *             Round parameter.
	 * 
	 * @throws QuestionsListIsEmptyException occurs when the list of questions from
	 *                                       Deck is empty.
	 * 
	 * @throws DeckUnderFilledException      occurs when there's not enough
	 *                                       questions to fit the requirement of
	 *                                       Party.NB_STEPS for the Party.
	 * 
	 * @throws NotEnoughQuestionsException   occurs when a Round has less questions
	 *                                       than Party.NB_STEPS_BY_ROUND asks it to
	 *                                       have.
	 * 
	 * @throws NotEnoughQuestionsException   occurs when a Round has more questions
	 *                                       than Party.NB_STEPS_BY_ROUND asks it to
	 *                                       have.
	 */
	public Party(Deck deck) throws EmptyQuestionsListException, DeckUnderFilledException, NotEnoughQuestionsException {
		if (deck.getQuestions().size() == 0)
			throw new EmptyQuestionsListException();
		if (deck.getQuestions().size() < 15)
			throw new DeckUnderFilledException(deck.getQuestions().size());

		List<Question> questionsList = deck.getQuestions();
		chosenQuestions = new ArrayList<Question>();
		// Shuffle the list of questions to randomize the 15 questions set.
		Collections.shuffle(questionsList);

		// Adding questions to chosenQuestions
		for (Question q : questionsList) {
			switch (q.getRound()) {

			case FIRST_ROUND:
				if (getNbQuestionsForRound(Round.FIRST_ROUND) < 5 && !chosenQuestions.contains(q))
					chosenQuestions.add(q);
				break;

			case SECOND_ROUND:
				if (getNbQuestionsForRound(Round.SECOND_ROUND) < 5 && !chosenQuestions.contains(q))
					chosenQuestions.add(q);
				break;

			case LAST_ROUND:
				if (getNbQuestionsForRound(Round.LAST_ROUND) < 5 && !chosenQuestions.contains(q))
					chosenQuestions.add(q);
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

	/**
	 * Returns the nb of questions in chosenQuestions for the Round passed as an
	 * argument.
	 * 
	 * @param round The Round that will be used.
	 * 
	 * @return the number of questions the the round.
	 */
	public int getNbQuestionsForRound(Round round) {
		int nb = 0;
		if (chosenQuestions != null) {
			for (Question q : chosenQuestions) {
				if (q.getRound() == round)
					nb++;
			}
		}
		return nb;
	}

	/**
	 * Sorts the questions by Round enumeration declaration order.
	 */
	public void sortQuestionsByRounds() {
		if (chosenQuestions != null) {
			Collections.sort(chosenQuestions, new Comparator<Question>() {

				@Override
				public int compare(Question q1, Question q2) {
					int ordinalQ1 = q1.getRound().ordinal();
					int ordinalQ2 = q2.getRound().ordinal();

					return Integer.compare(ordinalQ1, ordinalQ2);
				}

			});
		}
	}

	/**
	 * Returns if the Party has a next step of is ended.
	 * 
	 * @return if actualStep is lower than NB_STEPS.
	 */
	public boolean hasNextStep() {
		return actualStep < NB_STEPS;
	}

	/**
	 * Returns the numbers of chosen questions.
	 * 
	 * @return the number of chosen question.
	 */
	public int nbChosenQuestion() {
		return chosenQuestions.size();
	}

	/**
	 * Returns the Question for the next step and increments actualStep by 1.
	 * 
	 * @throws ExceedMaxStepsException occurs if the actual step is higher than the
	 *                                 Party.NB_STEPS
	 * 
	 * @return the Question for the next step.
	 */
	public Question getQuestionNextStep() throws ExceedMaxStepsException {
		incrementActualStep();

		if (isNeedingNextRound())
			goToNextRound();

		return (chosenQuestions.get(actualStep - 1));
	}

	public void incrementActualStep() throws ExceedMaxStepsException {
		if (!hasNextStep())
			throw new ExceedMaxStepsException(actualStep);

		actualStep++;
	}

	/**
	 * Step-up the party Round.
	 */
	public void goToNextRound() {
		Round[] rounds = Round.values();
		for (int i = 0; i < rounds.length - 1; i++) {
			if (actualRound == rounds[i]) {
				actualRound = rounds[i + 1];
				break;
			}
		}
	}

	/**
	 * Return the actual Round.
	 * 
	 * @return Actual Round.
	 */
	public Round getActualRound() {
		return actualRound;
	}

	/**
	 * Return if the current round is over.
	 * 
	 * @return if the round is over.
	 */
	public boolean isNeedingNextRound() {
		return ((actualStep % Party.NB_STEPS_BY_ROUND) - 1 == 0 && actualStep > 1);
	}

	/**
	 * Returns the actual step.
	 * 
	 * @return actual step.
	 */
	public int getActualStep() {
		return actualStep;
	}

	/**
	 * Return a description of the chosen questions.
	 * 
	 * @return the description.
	 */
	@Override
	public String toString() {
		String tmp = "\tchosen questions for party : \n";

		if (chosenQuestions != null) {
			for (Question q : chosenQuestions) {
				tmp += q.toString() + "\n";
			}
		}

		return tmp;
	}

	/**
	 * Return the right answer index.
	 * 
	 * @return The int that represents the right answer index.
	 */
	public int getRightAnswerIndex() {
		return rightAnswerIndex;
	}

	/**
	 * Set a new value for rightAnswerIndex. The new value must be higher or equals
	 * to 0 and lower than Question.NB_ANSWERS.
	 * 
	 * @param rightAnswerIndex, the new right answer index.
	 * 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. Describe the
	 *                                   bounds with a message.
	 */
	public void setRightAnswerIndex(int rightAnswerIndex) {
		if (rightAnswerIndex >= 0 && rightAnswerIndex < Question.NB_ANSWERS)
			this.rightAnswerIndex = rightAnswerIndex;
		else
			throw new IndexOutOfBoundsException("Index should be equals or superior to 0 and lower than "
					+ Question.NB_ANSWERS + ". However, it's value is : " + rightAnswerIndex);
	}

	/**
	 * Return the right answer statement.
	 * 
	 * @param The String object that represents the right answer statement.
	 */
	public String getRightAnswer() {
		return rightAnswer;
	}

	/**
	 * Set a new value for rightAnswer, the value must no be empty.
	 * 
	 * @param rightAnswer the new value for rightAnswer.
	 * 
	 * @throws IllegalArgumentException if the answer is null or empty. Describe the
	 *                                  problem that occured.
	 */
	public void setRightAnswer(String rightAnswer) {
		if (rightAnswer == null)
			throw new IllegalArgumentException("The param rightAnswer is null.");
		else if (rightAnswer.isEmpty())
			throw new IllegalArgumentException("The param rightAnswer is empty.");

		this.rightAnswer = rightAnswer;
	}

	/**
	 * Return the index for the friend joker.
	 * 
	 * @return An int with the value of the joker friend index.
	 */
	public int getJokerFriendIndex() {
		return jokerFriendIndex;
	}

	/**
	 * Set a new value for jokerFriendIndex. The new value must be higher or equals
	 * to 0 and lower than Question.NB_ANSWERS.
	 * 
	 * @param jokerFriendIndex, the new joker friend index.
	 * 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. Describe the
	 *                                   bounds with a message.
	 */
	public void setJokerFriendIndex(int jokerFriendIndex) throws IndexOutOfBoundsException {
		if (jokerFriendIndex >= 0 && jokerFriendIndex < Question.NB_ANSWERS)
			this.jokerFriendIndex = jokerFriendIndex;
		else
			throw new IndexOutOfBoundsException("Index should be equals or superior to 0 and lower than "
					+ Question.NB_ANSWERS + ". However, it's value is : " + jokerFriendIndex);
	}

	/**
	 * Return the list of percents allowed by the public for the public joker.
	 * 
	 * @return A List of double var that represents the percents of votes allowed by
	 *         the public.
	 */
	public List<Double> getJokerPublicPercents() {
		return jokerPublicPercents;
	}

	/**
	 * Replace the values of the jokerPublicPercents List.
	 * 
	 * @param jokerPublicPercents, the new List of doubles.
	 * 
	 * @throws IndexOutOfBoundsException if a percents value isn't between 0 and 100
	 *                                   included.
	 * 
	 * @throws IllegalArgumentException  if the size of the param isn't equals to
	 *                                   Question.NB_ANSWERS or if the param is
	 *                                   null.
	 */
	public void setJokerPublicPercents(List<Double> jokerPublicPercents)
			throws IndexOutOfBoundsException, IllegalArgumentException {
		if (jokerPublicPercents.size() == Question.NB_ANSWERS) {
			// Right size
			for (Double percents : jokerPublicPercents) {
				if (percents < 0 || percents > 100)
					throw new IndexOutOfBoundsException(
							"Percents should be between 0% and 100%. However, it's value is : " + percents);
			}
		} else {
			// Size not right
			throw new IllegalArgumentException("The size of the jokerPublicPercents List should be equals to "
					+ Question.NB_ANSWERS + ", however it's " + jokerPublicPercents.size());
		}

		this.jokerPublicPercents = jokerPublicPercents;
	}

	/**
	 * Return the List of indexes for the 5050 joker.
	 * 
	 * @return the List of indexes.
	 */
	public List<Integer> getJoker5050Indexes() {
		return joker5050Indexes;
	}

	/**
	 * Replace the values of the joker5050Indexes List.
	 * 
	 * @param joker5050Indexes, the new List of ints.
	 * 
	 * @throws IndexOutOfBoundsException if an index isn't between 0 and
	 *                                   Question.NB_ANSWERS -1
	 * 
	 * @throws IllegalArgumentException  if the size of the param isn't equals to
	 *                                   Question.NB_ANSWERS/2 or if the param is
	 *                                   null.
	 */
	public void setJoker5050Indexes(List<Integer> joker5050Indexes)
			throws IndexOutOfBoundsException, IllegalArgumentException {
		if (joker5050Indexes.size() == (Question.NB_ANSWERS / 2)) {
			for (Integer index : joker5050Indexes) {
				if (index < 0 || index > Question.NB_ANSWERS - 1)
					throw new IndexOutOfBoundsException("Joker5050 index should be between 0 and "
							+ (Question.NB_ANSWERS - 1) + " included. However, it's value is : " + index);
			}
		} else {
			// Size not right
			throw new IllegalArgumentException("The size of the joker5050Indexes List should be equals to "
					+ Question.NB_ANSWERS / 2 + ", however it's " + joker5050Indexes.size());
		}

		this.joker5050Indexes = joker5050Indexes;
	}

	/**
	 * Clears the joker5050Indexes field.
	 */
	public void clearJoker5050Indexes() {
		joker5050Indexes.clear();
	}
}
