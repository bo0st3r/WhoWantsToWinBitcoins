package model;

import java.util.ArrayList;
import java.util.List;

public class Earning {
	private List<Integer> amounts;

	public Earning() {
		amounts = new ArrayList<Integer>();

		// TEMPORARY
		for (int i = 1; i <= Party.NB_STEPS; i++) {
			amounts.add(i * 42);
		}
	}

	public Earning(List<Integer> amounts) {
		super();
		if (amounts.size() == Party.NB_STEPS) {
			for (Integer amount : amounts) {
				if (amount > 0)
					amounts.add(amount);
			}
		}
	}

	// TODO
	public void setAmount(int index, int amount) {
		if (amounts.get(index - 1) > amount)
			;
		// MUST THROW AN EXCEPTION

		if (amounts.get(index + 1) < amount)
			;
		// MUST THROW AN EXCEPTION
	}

	/*
	 * Returns the earnings corresponding to an index.
	 * 
	 * @param actualStep Index of the step for which we want to get the
	 * corresponding earnings.
	 */
	public int getAmount(int actualStep) {
		if (actualStep < 1 || actualStep > Party.NB_STEPS)
			;
		// MUST THROW AN EXCEPTION
		return amounts.get(actualStep);
	}

	@Override
	public String toString() {
		String tmp = "";
		for (Integer i : amounts) {
			tmp += i + "\n";
		}
		return tmp;
	}

}
