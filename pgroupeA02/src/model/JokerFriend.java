package model;

import java.util.Random;

public class JokerFriend implements JokerStrategy {
	// Rates are used to determine if the highest % of vote will be assigned to the
	// right answer
	private static final double FIRST_ROUND_RATE = 0.7;
	private static final double SECOND_ROUND_RATE = 0.50;
	private static final double LAST_ROUND_RATE = 0.35;
	private double accuracyRate;

	/**
	 * JokerFriend constructor. Set accuracyRate to 0.
	 */
	public JokerFriend() {
		accuracyRate = 0;
	}

	/**
	 * JokerStrategy pattern method, generate an index who will be used to display
	 * the JokerFriend result. A rate will be picked depending on the actual round.
	 * The higher the round is, the lower the rate will be. Then generates a random
	 * number between 0 and 1, if this number is lower than the picked round then
	 * the index will be the right answer index.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see model.JokerStrategy#execute(model.Party)
	 * 
	 * @param party, the ongoing Party.
	 */
	@Override
	public void execute(Party party) {
		// Get the joker's accuracy rate for the ongoing round
		getAccuracyRate(party);

		// Random test that will determine if the highest public vote will be assigned
		// to the right answer or no
		Random rand = new Random();
		double accuracyTest = rand.nextDouble();

		// Initially index value's the right answer index
		int index = party.getRightAnswerIndex();
		// If the accuracy test fails, a new index different from the previous will be
		// generated
		if (accuracyTest > accuracyRate) {
			while (index == party.getRightAnswerIndex()) {
				index = rand.nextInt(Question.NB_ANSWERS);
			}
		}

		// Assign the friend's answer index to the party JokerFriendIndex
		party.setJokerFriendIndex(index);
	}

	/**
	 * Determines the rate for the actual round.
	 * 
	 * @param party, the ongoing Party.
	 */
	public void getAccuracyRate(Party party) {
		switch (party.getActualRound()) {
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
