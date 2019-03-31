package model;

import view.PlayingGridPane;

public class Joker {
	private JokerStrategy jokerStrategy;

	public void setStrategy(JokerStrategy jokerStrategy) {
		this.jokerStrategy = jokerStrategy;

	}

	public void useJoker(PlayingGridPane pgp) {
		jokerStrategy.execute(pgp);
	}
}
