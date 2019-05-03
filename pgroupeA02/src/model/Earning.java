package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import utilities.IntegerComparator;
import utilities.Serialization;

public class Earning 
//implements Serializable 
{
//	private static final long serialVersionUID = -7426233831685861896L;

	public static final String FILE_NAME = "earnings";

	private List<Integer> amounts;

	/**
	 * Constructor. Instantiates the amounts field as an empty ArrayList.
	 */
	public Earning() {
		amounts = new ArrayList<Integer>();
	}

	/**
	 * Retrieves an Earning from a JSON file.
	 * 
	 * @param source String, the name of the JSON file.
	 */
	public Earning(String source) {
		super();
		Earning retrieved = Serialization.jsonToEarning(FILE_NAME);
		amounts = retrieved.amounts;
	}

	/**
	 * Retrieves an Earning from the main earning JSON file.
	 * 
	 * @return the Earning object corresponding to the main earning JSON file.
	 */
	public static Earning earning() {
		Earning retrieved = Serialization.jsonToEarning(FILE_NAME);
		return retrieved;
	}

	/**
	 * Adds the given amount to the amounts field and sorts it, then returns if it
	 * has been added. The amount must be positive and the amounts field size lower
	 * than the number of steps.
	 * 
	 * @param amount
	 * @return
	 */
	public boolean addAmount(int amount) {
		if (amount <= 0)
			return false;

		if (amounts.size() >= Party.NB_STEPS)
			return false;

		boolean added = amounts.add(amount);

		// Sorts the amounts if added
		Collections.sort(amounts, new IntegerComparator());

		return added;
	}

	/**
	 * Sets the index value with the given amount.
	 * 
	 * @throws IllegalArgumentException  when the given amount's value isn't between
	 *                                   the next and previous steps.
	 * @throws IndexOutOfBoundsException when the given index is negative.
	 * @param index  int, the index.
	 * @param amount int, the new amount.
	 */
	public void setAmount(int index, int amount) {
		// If 0
		if (index == 0) {
			if (amount > amounts.get(1))
				throw new IllegalArgumentException("The given amount higher than the next step amount. Next : "
						+ amounts.get(index + 1) + ". Given : " + amount);

			// If negative
		} else if (index < 0) {
			throw new IndexOutOfBoundsException();

			// If positive
		} else {
			// If index == last index or last index +1
			if (index >= amounts.size() - 1) {
				if (amount < amounts.get(index - 1))
					throw new IllegalArgumentException(
							"The given amount is lower than the precedent step amount. Precedent : "
									+ amounts.get(index - 1) + ". Given : " + amount);

				// If between bounds
			} else if (amounts.get(index - 1) > amount) {
				throw new IllegalArgumentException(
						"The given amount is lower than the precedent step amount. Precedent : "
								+ amounts.get(index - 1) + ". Given : " + amount);
			} else if (amounts.get(index + 1) < amount) {
				throw new IllegalArgumentException("The given amount higher than the next step amount. Next : "
						+ amounts.get(index + 1) + ". Given : " + amount);
			}
		}

		amounts.set(index, amount);
	}

	/**
	 * Removes the amount for the given index and returns it's value.
	 * 
	 * @param index the index used to remove the value.
	 * @return the amount of the value at the index.
	 */
	public int removeAmount(int index) {
		return amounts.remove(index);
	}

	/**
	 * Returns the earnings corresponding to an index.
	 * 
	 * @throws IndexOutOfBoundsException when actualStep param is out of bounds.
	 * @param index Index of the step for which we want to get the corresponding
	 *              earnings.
	 */
	public int getAmount(int index) {
		if (index < 0 || index > amounts.size() - 1)
			throw new IndexOutOfBoundsException("Index should be between 0 and " + (amounts.size() - 1)
					+ ", however the given index is : " + index);

		return amounts.get(index);
	}

	@Override
	public String toString() {
		String tmp = "";
		for (Integer i : amounts) {
			tmp += i + "\n";
		}
		return tmp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amounts == null) ? 0 : amounts.hashCode());
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
		Earning other = (Earning) obj;
		if (amounts == null) {
			if (other.amounts != null)
				return false;
		} else if (!amounts.equals(other.amounts))
			return false;
		return true;
	}
}
