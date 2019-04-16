package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Joker5050 implements JokerStrategy {

	@Override
	public void execute(Party party) {
		Random rand = new Random();
		List<Integer> indexes = new ArrayList<>();

		// Generates random indexes for buttons to be disabled
		while (indexes.size() != Question.NB_ANSWERS / 2) {
			int randomIndex = rand.nextInt(Question.NB_ANSWERS);
			if (randomIndex != party.getRightAnswerIndex() && !indexes.contains(randomIndex))
				indexes.add(randomIndex);
		}

		// Assign the index of 2 wrong answers to the party Joker5050Indexes
		party.setJoker5050Indexes(indexes);
	}

}
