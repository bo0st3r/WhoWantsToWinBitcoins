package modele;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import enumerations.Round;
import exceptions.AnswerAlreadyPresentException;
import exceptions.RightAnswerAlreadyPresentException;
import exceptions.TooMuchAnswersException;

public class Question{
	private String author;
	private String statement;
	private Round round;
	private Map<String, Boolean> choices;

	public Question(String author, String statement, Round round) {
		this.author = author;
		this.statement = statement;
		this.round = round;
		choices = new HashMap<String, Boolean>();
	}

	public void addChoice(String answer, boolean value)
			throws TooMuchAnswersException, AnswerAlreadyPresentException, RightAnswerAlreadyPresentException {
		//Throw an exception if the question has already 4 answers 
		if (choices.size() > 3)
			throw new TooMuchAnswersException(statement);

		//Throw an exception if the passed argument "answer" is already contained by the question
		if (choices.containsKey(answer))
			throw new AnswerAlreadyPresentException(statement, answer);

		//Throw an exception it the question alreay has it's right answer
		if (value)
			if (choices.containsValue(true))
				throw new RightAnswerAlreadyPresentException(statement);
		
		//Add the answer to the question
		choices.put(answer, value);

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
