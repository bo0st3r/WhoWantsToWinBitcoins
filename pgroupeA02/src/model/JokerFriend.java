package model;

import java.util.Random;

import view.PlayingGridPane;

public class JokerFriend implements JokerStrategy {
	// Rates are used to determine if the highest % of vote will be assigned to the
	// right answer
	private static final double FIRST_ROUND_RATE = 0.7;
	private static final double SECOND_ROUND_RATE = 0.50;
	private static final double LAST_ROUND_RATE = 0.35;
	private double accuracyRate;

	public JokerFriend() {
		accuracyRate = 0;
	}
	
	@Override
	public void execute(PlayingGridPane pgp) {
		if (!pgp.getBtnJokerFriend().isDisabled()) {
			// Get the joker's accuracy rate for the ongoing round
			getAccuracyRate(pgp);

			// Random test that will determine if the highest public vote will be assigned
			// to the right answer or no
			Random rand = new Random();
			double accuracyTest = rand.nextDouble();

			// Initially index value's the right answer index
			int index = pgp.getRightAnswerIndex();
			// If the accuracy test fails, a new index different from the previous will be
			// generated
			if (accuracyTest > accuracyRate) {
				while (index == pgp.getRightAnswerIndex()) {
					index = rand.nextInt(Question.NB_ANSWERS);
				}
			}

			// Sets the button ID as "answerJokerFriend" in order to color it differently
			pgp.getBtnAnswer(index).setId("answerJokerFriend");
		}
	}

	public void getAccuracyRate(PlayingGridPane pgp) {
		switch (pgp.getParty().getActualRound()) {
		case FIRST_ROUND:
			accuracyRate = FIRST_ROUND_RATE;
			break;
		case SECOND_ROUND:
			accuracyRate = SECOND_ROUND_RATE;
			break;
		case LAST_ROUND:
			accuracyRate = LAST_ROUND_RATE;
			break;
		}
	}
}
