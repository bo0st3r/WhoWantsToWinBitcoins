package modele;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import enumerations.Round;
import exceptions.AnswerAlreadyPresentException;
import exceptions.RightAnswerAlreadyPresentException;
import exceptions.TooMuchAnswersException;

public class Question {
	private String author;
	private String statement;
	private Round round;
	private Map<String, Boolean> choices;

	/*
	 * Constructor of Question class.
	 * The param "choices" just gets instantiated as an empty HashMap<String,Boolean>.
	 * @param author The name of the question's author, as a String.
	 * @param statement The statement of the question, as a String.
	 * @param round The game round of the question.
	 */
	public Question(String author, String statement, Round round) {
		this.author = author;
		this.statement = statement;
		this.round = round;
		choices = new HashMap<String, Boolean>();
	}

	/*
	 * This method adds a choice to the question, it also indicates if it's the right answer or no.
	 * @param answer A string that is the answer which will be added to the question
	 * @param value A boolean that states if it is or not the right answer
	 * @throws TooMuchAnswersException If there's already 4 answers present.
	 * @throws AnswerAlreadyPresentException If this answer's already present.
	 * @throws RightAnswerAlreadyPresentException If we passed "value" param as true and the right answer is already present.
	 */
	public void addChoice(String answer, boolean value)
			throws TooMuchAnswersException, AnswerAlreadyPresentException, RightAnswerAlreadyPresentException {
		// Throw an exception if the question has already 4 answers
		if (choices.size() > 3)
			throw new TooMuchAnswersException(statement);

		// Throw an exception if the passed argument "answer" is already contained by
		// the question
		if (choices.containsKey(answer))
			throw new AnswerAlreadyPresentException(statement, answer);

		// Throw an exception it the question alreay has it's right answer
		if (value)
			if (choices.containsValue(true))
				throw new RightAnswerAlreadyPresentException(statement);

		// Add the answer to the question
		choices.put(answer, value);
	}
	
	public Question clone() {
		return new Question(author, statement, round);
	}

	public String toString() {
		String result = "Statement : " + statement + "\nAnswers :\n";

		Set<Entry<String, Boolean>> entries = choices.entrySet();
		for (Entry entry : entries) {
			result += "- " + entry.getKey() + "\n";
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
