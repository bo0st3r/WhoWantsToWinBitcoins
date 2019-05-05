package model;

import java.io.Serializable;
import java.util.regex.Pattern;

import exceptions.InputSyntaxException;
import utilities.SHA256Hasher;

public class User implements Serializable {
	private static final long serialVersionUID = 3778008834876366931L;

	public static final Pattern PSEUDO_SYNTAX_REGEX = Pattern.compile("[a-zA-Z0-9]{" + 3 + "," + 40 + "}");
	public static final Pattern PASSWORD_SYNTAX_REGEX = Pattern
			.compile("[a-zA-Z0-9\\p{Punct}\\p{L}]{" + 4 + "," + 40 + "}");
	public static final Pattern EMAIL_SYNTAX_REGEX = Pattern.compile(
			"(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d"
					+ "-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"
					+ "|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\"
					+ "x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])",
			Pattern.CASE_INSENSITIVE);

	private String pseudo;
	private String password;
	private String email;

	private boolean admin;

	private int partiesWon;
	private int partiesPlayed;
	private double highestEarningsWon;
	private double totalEarningsWon;

	/**
	 * Instantiates an non-admin User object using given arguments. First verify if
	 * pseudo, password and email each fit their own syntax pattern. Password is
	 * hashed. Sets default values to 0.
	 * 
	 * @param pseudo   String, the user pseudo.
	 * @param password String, the user password.
	 * @param email    String, the user email.
	 * @throws InputSyntaxException When the user's input does not fit the relevant
	 *                              pattern.
	 */
	public User(String pseudo, String password, String email) throws InputSyntaxException {
		// Verify if the given pseudo matches the syntax
		if (!validatePseudo(pseudo))
			throw new InputSyntaxException("The pseudo '" + pseudo + "' does not fit it's relevant syntax pattern.",
					PSEUDO_SYNTAX_REGEX);

		// Verify if the given password matches the syntax
		if (!validatePassword(password) && password.length() != SHA256Hasher.HASH_SIZE)
			throw new InputSyntaxException("The password '" + password + "' does not fit it's relevant syntax pattern.",
					PASSWORD_SYNTAX_REGEX);

		// Verify if the given email matches the syntax
		if (!validateEmail(email) || email.indexOf("@") != email.lastIndexOf("@"))
			throw new InputSyntaxException("The email '" + email + "'does not fit it's relevant syntax pattern.",
					EMAIL_SYNTAX_REGEX);

		// Given values
		this.pseudo = pseudo;
		this.email = email;
		this.password = password;
		// If the password isn't already hashed, hash it
		hashPassword();

		// Default values
		partiesWon = 0;
		partiesPlayed = 0;
		highestEarningsWon = 0;
		totalEarningsWon = 0;
		admin = false;
	}

	/**
	 * Instantiates an User object using given arguments. First verify if pseudo,
	 * password and email each fit their own syntax pattern. Password is hashed.
	 * Sets default values to 0.
	 * 
	 * @see #User
	 * @param pseudo   String, the user pseudo.
	 * @param password String, the user password.
	 * @param email    String, the user email.
	 * @param admin    boolean, if the user is an admin.
	 * @throws InputSyntaxException
	 */
	public User(String pseudo, String password, String email, boolean admin) throws InputSyntaxException {
		this(pseudo, password, email);
		this.admin = admin;
	}

	/**
	 * Returns if the given pseudo matches it's pattern syntax.
	 * 
	 * @param pseudo String, the pseudo to validate.
	 * @return boolean, if the pseudo is validated.
	 */
	public static boolean validatePseudo(String pseudo) {
		return Pattern.matches(PSEUDO_SYNTAX_REGEX.pattern(), pseudo);
	}

	/**
	 * Returns if the given password matches it's pattern syntax.
	 * 
	 * @param password String, the password to validate.
	 * @return boolean, if the password is validated.
	 */
	public static boolean validatePassword(String password) {
		return Pattern.matches(PASSWORD_SYNTAX_REGEX.pattern(), password);
	}

	/**
	 * Returns if the given email matches it's pattern syntax.
	 * 
	 * @param email String, the email to validate.
	 * @return boolean, if the email is validated.
	 */
	public static boolean validateEmail(String email) {
		boolean result = EMAIL_SYNTAX_REGEX.matcher(email).find();
		return result;
	}

	/**
	 * Returns the hashed password.
	 * 
	 * @see SHA256Hasher
	 * 
	 * @param password String, the password to hash.
	 * 
	 * @return String, the hashed password.
	 */
	public static String getHashedPassword(String password) {
		return SHA256Hasher.hashString(password);
	}

	/**
	 * If not already hashed, hash the user password using the SHA-256 algorithm.
	 */
	public void hashPassword() {
		// If not already hashed, hashes the password
		if (!isHashedPassword(password)) {
			// Hash the password using SHA-256 algorithm
			String hashedPassword = getHashedPassword(password);
			this.password = hashedPassword;
		}
	}

	/**
	 * Returns if the user password is already hashed.
	 * 
	 * @param password the password to verify.
	 * @return the result.
	 */
	public static boolean isHashedPassword(String password) {
		return password.length() == SHA256Hasher.HASH_SIZE;
	}

	/**
	 * Compares this user's password with an not hashed password.
	 * 
	 * @param password
	 * @return
	 */
	public boolean comparePassword(String password) {
		return this.password.equals(getHashedPassword(password));
	}

	/**
	 * Returns the user pseudo.
	 * 
	 * @return String, the pseudo field.
	 */
	public String getPseudo() {
		return pseudo;
	}

	/**
	 * Returns the hashed user password.
	 * 
	 * @return String, the password field.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets a new password to the user, only if it fits the syntax pattern. Hash the
	 * password using SHA-256 algorithm before setting it's new value.
	 * 
	 * @see getHashedPassword(String password)
	 * 
	 * @param password String, the user field.
	 * @throws InputSyntaxException When the given password doesn't fit it's syntax
	 *                              pattern.
	 */
	public void setPassword(String password) throws InputSyntaxException {
		if (!validatePassword(password))
			throw new InputSyntaxException("The pseudo '" + pseudo + "' does not fit it's relevant syntax pattern.",
					PSEUDO_SYNTAX_REGEX);

		// Hash the password using SHA-256 algorithm
		password = getHashedPassword(password);

		this.password = password;
	}

	/**
	 * Returns the user's email.
	 * 
	 * @return String, the email field.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Returns if the user is an admin.
	 * 
	 * @return boolean the admin field.
	 */
	public boolean isAdmin() {
		return admin;
	}

	/**
	 * Sets if the user is or not an admin.
	 * 
	 * @param isAdmin boolean the new admin value.
	 */
	public void setAdmin(boolean isAdmin) {
		this.admin = isAdmin;
	}

	/**
	 * Returns the total of won earnings won by the user.
	 * 
	 * @return int, the totalEarningsWon field.
	 */
	public double getTotalEarningsWon() {
		return totalEarningsWon;
	}

	/**
	 * Increments the user's total earnings using the passed value. Only if
	 * increment is higher than 0.
	 * 
	 * @param increment double
	 */
	public void incrementTotalEarningsWon(double increment) {
		if (increment > 0)
			totalEarningsWon += increment;
	}

	/**
	 * Returns the number of parties won by the user.
	 * 
	 * @return int, the partiesWon field.
	 */
	public int getPartiesWon() {
		return partiesWon;
	}

	/**
	 * Increments by 1 the number of parties won by the user.
	 */
	public void incrementPartiesWon() {
		partiesWon++;
	}

	/**
	 * Returns the number of parties played by the user.
	 * 
	 * @return int, the partiesPlayed field.
	 */
	public int getPartiesPlayed() {
		return partiesPlayed;
	}

	/**
	 * Increments by 1 the number of parties played by the user.
	 */
	public void incrementPartiesPlayed() {
		partiesPlayed++;
	}

	/**
	 * Returns the number of parties lost by the user. If result is lower than 0,
	 * returns -1.
	 * 
	 * @return int, partiesPlayed field minus partiesWon field.
	 */
	public int numberPartiesLost() {
		int partiesLost = partiesPlayed - partiesWon;
		if (partiesLost >= 0)
			return partiesLost;

		// If, for any reason, lower than 0
		return -1;
	}

	/**
	 * Gets the highest earnings won by the user.
	 * 
	 * @return double, the highestEarningsWon field.
	 */
	public double getHighestEarningsWon() {
		return highestEarningsWon;
	}

	/**
	 * Sets a new value for the highestEarningsWon field. Only >= 0 and higher than
	 * the actual one values.
	 * 
	 * @param highestEarningsWon double the new value.
	 */
	public void setHighestEarningsWon(double highestEarningsWon) {
		if (highestEarningsWon >= 0 && this.highestEarningsWon < highestEarningsWon)
			this.highestEarningsWon = highestEarningsWon;
	}

	/**
	 * Returns a clone of this user.
	 * 
	 * @return User a clone of this User.
	 */
	public User clone() {
		User clone = null;

		try {
			clone = new User(pseudo, password, email, admin);
			clone.partiesWon = partiesWon;
			clone.partiesPlayed = partiesPlayed;
			clone.highestEarningsWon = highestEarningsWon;
			clone.totalEarningsWon = totalEarningsWon;
		} catch (InputSyntaxException e) {
			e.printStackTrace();
		}

		return clone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((pseudo == null) ? 0 : pseudo.hashCode());
		return result;
	}

	/**
	 * Returns if this user is equal to the one given. Compare using password,
	 * pseudo and email fields, only if the object is an User.
	 * 
	 * @param obj An object, supposed to be an User.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (pseudo == null) {
			if (other.pseudo != null)
				return false;
		} else if (!pseudo.equals(other.pseudo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [pseudo=" + pseudo + ", password=" + password + ", email=" + email + ", admin=" + admin
				+ ", partiesWon=" + partiesWon + ", partiesPlayed=" + partiesPlayed + ", highestEarningsWon="
				+ highestEarningsWon + ", totalEarningsWon=" + totalEarningsWon + "]";
	}

	public User(String pseudo, String password, String email, int partiesWon, int partiesPlayed,
			double highestEarningsWon, double totalEarningsWon) throws InputSyntaxException {
		this(pseudo, password, email);
		this.partiesWon = partiesWon;
		this.partiesPlayed = partiesPlayed;
		this.highestEarningsWon = highestEarningsWon;
		this.totalEarningsWon = totalEarningsWon;
	}
	
}
