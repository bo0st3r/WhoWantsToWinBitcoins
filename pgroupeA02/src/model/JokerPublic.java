package model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import view.PlayingGridPane;

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
	 */
	public JokerPublic() {
		tot = 0;
		accuracyRate = 0;
		publicVotePercents = new ArrayList<>();
	}

	@Override
	public void execute(PlayingGridPane pgp) {
		if (!pgp.getBtnJokerPublic().isDisabled()) {
			int i;

			// Randomize the 4 results
			getRandomNumbers();
			// Get the joker's accuracy rate for the ongoing round
			getAccuracyRate(pgp);

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
				int rightAnswerIndex = pgp.getRightAnswerIndex();

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

			// Displays votes %
			DecimalFormat df = new DecimalFormat("#0.0");
			for (i = 0; i <= Question.NB_ANSWERS - 1; i++) {
				pgp.lblJokerResultsSetText(i, df.format(publicVotePercents.get(i)) + "%");
			}
		}
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
