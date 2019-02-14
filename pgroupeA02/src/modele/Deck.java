package modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import exceptions.QuestionAlreadyPresentException;

public class Deck{
	private static final long serialVersionUID = 6638000761501455L;
	private List<Question> questions;

	public Deck() {
		questions = new ArrayList<>();
	}

	public boolean addQuestion(Question question) throws QuestionAlreadyPresentException {
		if (questions.contains(question))
			throw new QuestionAlreadyPresentException(question.getStatement());

		return questions.add(question);
	}

	public int getDeckSize() {
		return questions.size();
	}
}
