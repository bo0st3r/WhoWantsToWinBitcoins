package enumerations;

public enum Round {
	FIRST_ROUND("First round"), 
	SECOND_ROUND("Second round"), 
	LAST_ROUND("Last round");

	private String roundStatement;

	Round(String roundStatement) {
		this.roundStatement = roundStatement;
	}
	
	public String getRoundStatement() {
		return roundStatement;
	}
}
