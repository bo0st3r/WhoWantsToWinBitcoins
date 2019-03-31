package model;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import view.PlayingGridPane;

public class Joker5050 implements JokerStrategy {

	@Override
	public void execute(PlayingGridPane pgp) {
		if (!pgp.getBtnJoker5050().isDisable()) {
			Random rand = new Random();
			Set<Integer> indexes = new HashSet<>();

			// Generates random indexes for buttons to be disabled
			while (indexes.size() != Question.NB_ANSWERS / 2) {
				int index = rand.nextInt(Question.NB_ANSWERS);
				if (index != pgp.getRightAnswerIndex())
					indexes.add(index);
			}

			// Disable randomly selected buttons
			for (Integer index : indexes) {
				pgp.disableBtnAnswer(true, index);
			}
		}
	}

}
