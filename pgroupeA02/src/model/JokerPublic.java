package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class JokerPublic implements JokerStrategy {
	// Rates are used to determine if the highest % of vote will be assigned to the
	// right answer
	private static final double FIRST_ROUND_RATE = 0.5;
	private static final double SECOND_ROUND_RATE = 0.4;
	private static final double LAST_ROUND_RATE = 0.3;
	private List<Double> publicVotePercents;
	private double tot;
	private double accuracyRate;

	/*
	 * JokerPublic constructor. Initialize publicVotePercents as an empty ArrayList.
	 * Set tot and accuracyRate values to 0.
	 */
	public JokerPublic() {
		tot = 0;
		accuracyRate = 0;
		publicVotePercents = new ArrayList<>();
	}

	/*
	 * JokerStrategy pattern method, generates 4 random numbers whose sum is 100 and
	 * who will be used to display the JokerPublic result. A rate will be picked
	 * depending on the actual round. The higher the round is, the lower the rate
	 * will be. Then generates a random number between 0 and 1, if this number is
	 * lower than the picked round then the biggest number will be allowed to the
	 * right answer.
	 * 
	 * @see model.JokerStrategy#execute(model.Party)
	 */
	@Override
	public void execute(Party party) {
		int i;
		// Randomize the 4 results
		getRandomNumbers();
		// Get the joker's accuracy rate for the ongoing round
		getAccuracyRate(party);

		// Computes the ratio necessary for the sum of publicVotePercents to equals 100%
		double ratio = tot / 100;

		for (i = 0; i <= Question.NB_ANSWERS - 1; i++) {
			// Normalize % using computed ratio
			publicVotePercents.set(i, publicVotePercents.get(i) / ratio);
		}

		// Random test that will determine if the highest public vote will be assigned
		// to the right answer or no
		Random rand = new Random();
		double accuracyTest = rand.nextDouble();

		if (accuracyTest < accuracyRate) {
			double max = 0;
			int maxIndex = 0;
			double valueRightAnswer = 0;
			int rightAnswerIndex = party.getRightAnswerIndex();

			for (i = 0; i <= Question.NB_ANSWERS - 1; i++) {
				// Gets the max % of vote & it's index for wrong answers
				if (publicVotePercents.get(i) > max && i != rightAnswerIndex) {
					max = publicVotePercents.get(i);
					maxIndex = i;
				}
			}
			// Gets the % vote for the right answer
			valueRightAnswer = publicVotePercents.get(rightAnswerIndex);

			// If the votes % for the right answer is less than the max vote % then swap
			// their values
			if (max > valueRightAnswer) {
				Collections.swap(publicVotePercents, maxIndex, rightAnswerIndex);
			}
		}

		// Assign the percents of vote given by the public to the party
		// JokerPublicPercents
		party.setJokerPublicPercents(publicVotePercents);
	}

	/*
	 * Generates as much random numbers as Question.NB_ANSWERS value. Adds it to
	 * publicVotePercent and add it's value to tot.
	 */
	public void getRandomNumbers() {
		Random rand = new Random();
		for (int i = 0; i <= Question.NB_ANSWERS - 1; i++) {
			double nb = rand.nextDouble() * 100;
			publicVotePercents.add(nb);
			tot += nb;
		}
	}

	/*
	 * Determines the accuracyRate for the Round ongoing.
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
