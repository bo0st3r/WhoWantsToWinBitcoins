package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import enumerations.Round;
import exceptions.AnswerAlreadyPresentException;
import exceptions.NeedRightAnswerException;
import exceptions.NoRightAnswerException;
import exceptions.NotARoundException;
import exceptions.RightAnswerAlreadyPresentException;
import exceptions.StatementTooShortException;
import exceptions.TooMuchAnswersException;

public class Question {
	public static final int NB_ANSWERS = 4;
	public static final int STATEMENT_MIN_LENGTH = 10;
	private String author;
	private String statement;
	private Round round;
	private Map<String, Boolean> choices;

	/**
	 * Constructor of Question class. The param "choices" just gets instantiated as
	 * an empty HashMap<String,Boolean>.
	 * 
	 * @param author    The name of the question's author, as a String.
	 * 
	 * @param statement The statement of the question, as a String.
	 * 
	 * @param round     The game round of the question.
	 * 
	 * @throws StatementTooShortException If the statement is less than 15
	 *                                    characters long.
	 * 
	 * @throws NotARoundException         If the round param passed is not from
	 *                                    Round enum.
	 */
	public Question(String author, String statement, Round round)
			throws StatementTooShortException, NotARoundException {
		if (statement.length() < STATEMENT_MIN_LENGTH)
			throw new StatementTooShortException(statement);
		else
			this.statement = statement;

		if (!(round instanceof Round))
			throw new NotARoundException();
		else
			this.round = round;

		if (author.isEmpty())
			this.author = "Satoshi Nakamoto";
		else
			this.author = author;

		choices = new HashMap<String, Boolean>();
	}

	/**
	 * This method adds a choice to the question, it also indicates if it's the
	 * right answer or no.
	 * 
	 * @param answer A string that is the answer which will be added to the question
	 * 
	 * @param value  A boolean that states if it is or not the right answer
	 * 
	 * @throws TooMuchAnswersException            If there's already 4 answers
	 *                                            present.
	 * 
	 * @throws AnswerAlreadyPresentException      If this answer's already present.
	 * 
	 * @throws RightAnswerAlreadyPresentException If we passed "value" param as true
	 *                                            and the right answer is already
	 *                                            present.
	 * 
	 * @throws NeedRightAnswerException           If we passed the fourth choice
	 *                                            with "value" as false and there's
	 *                                            already 3 false answers. (Cause we
	 *                                            need the right answer)
	 */
	public void addChoice(String answer, boolean value) throws TooMuchAnswersException, AnswerAlreadyPresentException,
			RightAnswerAlreadyPresentException, NeedRightAnswerException {
		if (choices.size() > 3)
			throw new TooMuchAnswersException(statement);

		if (choices.containsKey(answer))
			throw new AnswerAlreadyPresentException(statement, answer);

		if (value)
			if (choices.containsValue(true))
				throw new RightAnswerAlreadyPresentException(statement);

		if (choices.size() == 3)
			if (!value)
				if (!choices.containsValue(true))
					throw new NeedRightAnswerException(statement, answer);

		// Adds the answer to the question
		choices.put(answer, value);
	}

	/**
	 * Remove the answer related to the statement.
	 * 
	 * @param statement The statement of the answer to remove.
	 */
	public boolean removeChoice(String statement) {
		return choices.remove(statement);
	}

	/**
	 * If the question already has it's right answer, removes it from the choices.
	 */
	public void removeRightAnswer() {
		if (choices.containsValue(true)) {
			Set<Entry<String, Boolean>> entries = choices.entrySet();
			for (Entry<String, Boolean> entry : entries) {
				if (entry.getValue())
					choices.remove(entry.getKey());
			}
		}
	}

	/**
	 * If the question already has it's right answer, returns it.
	 * 
	 * @throws NoRightAnswerException If there is no right answer yet.
	 */
	public String getRightAnswer() throws NoRightAnswerException {
		if (choices.containsValue(true)) {
			Set<Entry<String, Boolean>> entries = choices.entrySet();
			for (Entry<String, Boolean> entry : entries) {
				if (entry.getValue())
					return entry.getKey();
			}
		}

		throw new NoRightAnswerException(statement);
	}

	/**
	 * Clear the list of answer choices.
	 */
	public void clearChoices() {
		choices.clear();
	}

	@Override
	public Question clone() {
		Question clone = null;
		try {
			clone = new Question(author, statement, round);
		} catch (StatementTooShortException | NotARoundException e) {
			e.printStackTrace();
		}

		if (!choices.isEmpty()) {
			Set<Entry<String, Boolean>> entries = choices.entrySet();
			for (Entry<String, Boolean> entry : entries) {
				clone.choices.put(entry.getKey(), entry.getValue());
			}
		}

		return clone;
	}

	@Override
	public String toString() {
		String result = "Author : " + author + "\nStatement : " + statement + "\nRound : " + round.getRoundStatement()
				+ "\nAnswers :\n";

		Set<Entry<String, Boolean>> entries = choices.entrySet();
		for (Entry<String, Boolean> entry : entries) {
			result += "- " + entry.getKey();
			if (entry.getValue())
				result += " - right choice";
			result += "\n";
		}

		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((statement == null) ? 0 : statement.hashCode());
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
		Question other = (Question) obj;
		if (statement == null) {
			if (other.statement != null)
				return false;
		} else if (!statement.equals(other.statement))
			return false;
		return true;
	}

	/**
	 * Sets a new statement if the given statement size is higher than
	 * STATEMENT_MIN_LENGHT.
	 * 
	 * @param statement the new statement.
	 * @throws StatementTooShortException if the statement is size is lower than
	 *                                    STATEMENT_MIN_LENGHT.
	 */
	public void setStatement(String statement) throws StatementTooShortException {
		if (statement.length() < STATEMENT_MIN_LENGTH)
			throw new StatementTooShortException(statement);
		this.statement = statement;
	}

	/**
	 * Sets a new value for the author name, if the given value is empty it sets the
	 * author as "Satoshi Nakamoto".
	 * 
	 * @param author the new author.
	 */
	public void setAuthor(String author) {
		if (author.isEmpty())
			this.author = "Satoshi Nakamoto";
		else
			this.author = author;
	}

	public void setRound(Round round) {
		if (round != null) {
			this.round = round;
		}
	}

	public int getNbAnswers() {
		return choices.size();
	}

	public Map<String, Boolean> getChoices() {
		return choices;
	}

	public String getAuthor() {
		return author;
	}

	public String getStatement() {
		return statement;
	}

	public Round getRound() {
		return round;
	}

}
