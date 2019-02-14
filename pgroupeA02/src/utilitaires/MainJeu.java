package utilitaires;

import modele.Deck;

public class MainJeu {
	public static void main(String[] args) {
		Deck d = new Deck();
		Serialization.deckToJson(d,"main_deck");
	}
}
