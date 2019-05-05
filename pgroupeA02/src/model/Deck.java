package model;

import java.util.ArrayList;
import java.util.List;

import enumerations.Round;
import exceptions.NotEnoughAnswersException;
import exceptions.QuestionAlreadyPresentException;

public class Deck {
	public static final String FILE_NAME = "main_deck";

	private List<Question> questions;

	/**
	 * Constructor.
	 */
	public Deck() {
		questions = new ArrayList<>();
	}

	/**
	 * Constructor with given questions list.
	 * 
	 * @param questions the list.
	 */
	public Deck(List<Question> questions) {
		this.questions = questions;
	}

	/**
	 * This method adds a question to the deck if it's not yet in.
	 * 
	 * @param question A Question object
	 * 
	 * @throws QuestionAlreadyPresentException Occurs if the question is already in
	 *                                         the deck.
	 * 
	 * @throws NotAllAnswersException          Occurs if the question has less than
	 *                                         4 possible answers.
	 */
	public boolean addQuestion(Question question) throws QuestionAlreadyPresentException, NotEnoughAnswersException {
		if (questions.contains(question))
			throw new QuestionAlreadyPresentException(question.getStatement());

		if (question.getNbAnswers() < 4)
			throw new NotEnoughAnswersException(question.getStatement());

		return questions.add(question.clone());
	}

	/**
	 * If "questions" collection contains the Question passed as argument, removes
	 * it from "questions".
	 * 
	 * @param question A Question object
	 */
	public boolean removeQuestion(Question question) {
		return questions.remove(question);
	}

	/**
	 * Returns the size of the deck.
	 */
	public int questionsSize() {
		return questions.size();
	}

	/**
	 * Updates a question.
	 * 
	 * @param oldValue the old Question object.
	 * @param newValue the new Question object.
	 * @return if it has been replaced or not.
	 * @throws QuestionAlreadyPresentException
	 * @throws NotEnoughAnswersException
	 */
	public boolean update(Question oldValue, Question newValue)
			throws QuestionAlreadyPresentException, NotEnoughAnswersException {
		// If can remove the old question, tries to add the new question. If it succeed
		// returns true, otherwise re-ads the old value and returns false.
		if (questions.remove(oldValue)) {
			boolean result = addQuestion(newValue);
			if (result) {
				return true;
			} else {
				questions.add(oldValue);
				return false;
			}
		}

		// If can't remove the old question
		return false;

	}

	/**
	 * Returns a clone of the questions List.
	 */
	public List<Question> getQuestions() {
		List<Question> tmp = new ArrayList<Question>();

		for (Question q : questions) {
			tmp.add(q.clone());
		}

		return tmp;
	}

	/**
	 * Returns a clone of this deck with a different reference.
	 * 
	 * @return the clone Deck object.
	 */
	public Deck clone() {
		return new Deck(getQuestions());
	}

	/**
	 * Removes from the deck the elements contained in the given collection.
	 * 
	 * @param questions the questions to remove.
	 */
	public void removeAll(List<Question> questions) {
		for (Question q : questions) {
			this.questions.remove(q);
		}
	}

	@Override
	public String toString() {
		String result = "\tHere's the questions of this deck :\n";

		for (Question q : questions) {
			result += q.toString() + "\n";
		}

		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((questions == null) ? 0 : questions.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Deck other = (Deck) obj;
		if (questions == null) {
			if (other.questions != null)
				return false;
		} else if (!questions.equals(other.questions))
			return false;
		return true;
	}

}
