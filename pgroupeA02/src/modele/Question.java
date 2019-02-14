package modele;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import enumerations.Round;
import exceptions.AnswerAlreadyPresentException;
import exceptions.RightAnswerAlreadyPresentException;
import exceptions.TooMuchAnswersException;

public class Question implements Serializable{
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
		// Propage une excption s'il y a déjà les 4 réponses possibles à la question
		if (choices.size() > 3)
			throw new TooMuchAnswersException(statement);

		// Propage une exception si cette réponse est déjà présente pour la question
		if (choices.containsKey(answer))
			throw new AnswerAlreadyPresentException(statement, answer);

		// Propage une exception s'il y a déjà la bonne réponse à la question
		if (value)
			if (choices.containsValue(true))
				throw new RightAnswerAlreadyPresentException(statement);
		
		//Ajout de la réponse à la question
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
