package modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import exceptions.QuestionAlreadyPresentException;

public class Deck {
	private static final long serialVersionUID = 6638000761501455L;
	private List<Question> questions;

	public Deck() {
		questions = new ArrayList<>();
	}

	public boolean addQuestion(Question question) throws QuestionAlreadyPresentException {
		if (questions.contains(question))
			throw new QuestionAlreadyPresentException(question.getStatement());

		return questions.add(question.clone());
	}


	public String toString() {
		String result = "Here's the questions of this deck :\n";

		for (Question q : questions) {
			result += q.toString() + "\n";
		}

		return result;
	}

	public int getDeckSize() {
		return questions.size();
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
