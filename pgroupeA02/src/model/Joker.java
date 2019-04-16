package model;

public class Joker {
	private JokerStrategy jokerStrategy;

	public void setStrategy(JokerStrategy jokerStrategy) {
		this.jokerStrategy = jokerStrategy;

	}

	public void useJoker(Party party) {
		jokerStrategy.execute(party);
	}
}
