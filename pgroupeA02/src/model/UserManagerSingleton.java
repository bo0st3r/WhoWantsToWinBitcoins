package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import exceptions.DuplicateUserEmailException;
import exceptions.DuplicateUserException;
import exceptions.DuplicateUserPseudoException;
import exceptions.NullUserException;
import exceptions.UserNotFoundException;
import utilities.Serialization;

public class UserManagerSingleton implements Serializable {
	private static final long serialVersionUID = 2967689235642893360L;
	public static final String FILE_NAME = "user_manager_singleton";

	private static UserManagerSingleton INSTANCE = null;
	private Set<User> users;

	/**
	 * Private constructor. Recovers the UserManagerSingleton from the JSON file.
	 */
	private UserManagerSingleton() {
		UserManagerSingleton retrieved = Serialization.jsonToUserManagerSingleton(FILE_NAME);
		users = retrieved.users;
	}

	/**
	 * Returns the Singleton instance, if null instantiates it.
	 * 
	 * @return the UserManagerSingleton instance.
	 */
	public static UserManagerSingleton getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new UserManagerSingleton();
		}
		return INSTANCE;
	}

	/**
	 * Returns if the Singleton instance is null or not.
	 * 
	 * @return boolean the result.
	 */
	public static boolean isNullInstance() {
		return (INSTANCE == null) ? true : false;
	}

	/**
	 * Adds an user's clone to the users, returns the result and update the JSON
	 * file. There can't be two users with the same
	 * 
	 * @param user the User object to add to the users field.
	 * @return boolean, if the clone has been added.
	 * @throws NullUserException            when the clone is null.
	 * @throws DuplicateUserPseudoException when the pseudo given is already taken.
	 * @throws DuplicateUserEmailException  when the email given is already taken.
	 * @throws DuplicateUserException       when the pseudo and email given are
	 *                                      already taken.
	 */
	public boolean addUser(User user)
			throws DuplicateUserPseudoException, DuplicateUserEmailException, DuplicateUserException {
		if (user == null) {
			// Because of user was null
			throw new NullUserException();

		} else if (isUsedPseudo(user.getPseudo())) {
			if (isUsedEmail(user.getEmail())) {
				// Because pseudo and email are duplicate
				throw new DuplicateUserException();

			} else {
				// Because of duplicate pseudo
				throw new DuplicateUserPseudoException();
			}

		} else if (isUsedEmail(user.getEmail())) {
			// Because of duplicate email
			throw new DuplicateUserEmailException();
		}

		User clone = user.clone();
		boolean added = users.add(clone);

		// Updates the JSON file
		updateJSON();

		return added;
	}

	/**
	 * Returns if the pseudo is already used.
	 * 
	 * @param pseudo String, the pseudo to verify.
	 * @return if the pseudo is already used.
	 */
	public boolean isUsedPseudo(String pseudo) {
		for (User user : users) {
			if (user.getPseudo().equals(pseudo))
				return true;
		}

		return false;
	}

	/**
	 * Returns if the email is already used. Not case-sensitive.
	 * 
	 * @param email String, the email to verify.
	 * @return if the email is already used.
	 */
	public boolean isUsedEmail(String email) {
		String loweredEmail = email.toLowerCase();

		for (User user : users) {
			String loweredEmailTest = user.getEmail().toLowerCase();

			if (loweredEmailTest.equals(loweredEmail))
				return true;
		}

		return false;
	}

	/**
	 * Removes the user from the users field.
	 * 
	 * @param user the User object to remove.
	 * @return boolean, if the user has been removed.
	 */
	public boolean removeUser(User user) {
		boolean removed = users.remove(user);
		if (removed)
			updateJSON();
		return removed;
	}

	/**
	 * Removes the user corresponding to the given pseudo from the users field.
	 * 
	 * @param pseudo the String object containing the user's pseudo.
	 * @return boolean, if the user has been removed.
	 */
	public boolean removeUserByPseudo(String pseudo) {
		boolean removed;

		for (User user : users) {
			if (user.getPseudo().equals(pseudo)) {
				removed = removeUser(user);
				updateJSON();
				return removed;
			}
		}

		return false;
	}

	/**
	 * Clears the users field.
	 */
	public void clearUsers() {
		users.clear();
	}

	/**
	 * Returns a copy of the users field.
	 * 
	 * @return Set<User>, a copy of the users field.
	 */
	public Set<User> getUsers() {
		Set<User> clone = new HashSet<>();

		// Copy the users Set into a clone
		for (User user : users) {
			clone.add(user);
		}

		// Returns the clone
		return clone;
	}

	/**
	 * Returns the users field size.
	 * 
	 * @return int, the users field size.
	 */
	public int usersSize() {
		return users.size();
	}

	/**
	 * Updates the JSON file with actual instance state.
	 */
	public void updateJSON() {
		// Updates the UserManager JSON file
		Serialization.userManagerSingletonToJson(INSTANCE, FILE_NAME);
	}

	@Override
	public String toString() {
		String result = "\tHere's the users of this manager :\n";

		for (User user : users) {
			result += user.toString() + "\n";
		}

		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((users == null) ? 0 : users.hashCode());
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
		UserManagerSingleton other = (UserManagerSingleton) obj;
		if (users == null) {
			if (other.users != null)
				return false;
		} else if (!users.equals(other.users))
			return false;
		return true;
	}
	/**
	 * get User corresponding to the given pseudo from the users field.
	 * @param the String object containing the user's pseudo.
	 * @return User's clone
	 * @throws UserNotFoundException 
	 */
	public User getUserByPseudo(String pseudo) {
		User clone = null;
		for (User user : users) {
			if (user.getPseudo().equals(pseudo)) {
				clone = user.clone();
//			}else {
//				throw new UserNotFoundException();
//				
			}
		}
		return clone;
	}
	
	public boolean updateUser(User oldUser, User newUser) throws DuplicateUserPseudoException, DuplicateUserEmailException, DuplicateUserException {
		
		if (newUser.validateEmail(newUser.getEmail()) || newUser.validatePassword(newUser.getPassword())) {
			removeUserByPseudo(oldUser.getPseudo());
			addUser(newUser);
			return true;
			}
		return false;
		
		
	}
	

}
