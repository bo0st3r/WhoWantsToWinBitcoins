package model;

public class Joker {
	private JokerStrategy jokerStrategy;

	/*
	 * Sets the new jokerStrategy.
	 * 
	 * @param jokerStrategy a JokerStrategy reference.
	 */
	public void setStrategy(JokerStrategy jokerStrategy) {
		this.jokerStrategy = jokerStrategy;

	}

	/*
	 * Use the actual jokerStrategy execute method.
	 * 
	 * @param party, the ongoing Party.
	 */
	public void useJoker(Party party) {
		jokerStrategy.execute(party);
	}
}
