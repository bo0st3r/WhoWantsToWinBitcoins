package unitary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import exceptions.DuplicateUserEmailException;
import exceptions.DuplicateUserException;
import exceptions.DuplicateUserPseudoException;
import exceptions.InputSyntaxException;
import exceptions.NullUserException;
import model.User;
import model.UserManagerSingleton;
import utilities.Explorer;

@SuppressWarnings("unchecked")
public class TestUserManagerSingleton {
	private UserManagerSingleton instance;
	private Set<User> users;
	private User u1;
	private User u2;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		instance = UserManagerSingleton.getInstance();
		instance.clearUsers();
		users = (Set<User>) Explorer.getField(instance, "users");
		u1 = new User("admin", "helha", "admin@helha.be", true);
		u2 = new User("Pierre", "test1", "test1@gmail.com");
	}

	@After
	public void tearDown() throws Exception {
		instance = null;
		users = null;
		u1 = u2 = null;
	}

	@Test
	public void testGetInstance() {
		assertNotNull("Instance should be not null", UserManagerSingleton.getInstance());
	}

	@Test
	public void testIsNullInstance() {
		assertFalse("Instance should be not null", UserManagerSingleton.isNullInstance());
	}

	@Test
	public void testAddUser() throws DuplicateUserPseudoException, DuplicateUserEmailException, DuplicateUserException {
		try {
			instance.addUser(null);
		} catch (NullUserException e) {

		}

		int size1 = instance.usersSize();
		instance.addUser(u1);
		assertTrue("Size should have increased by 1", instance.usersSize() == (size1 + 1));
	}

	@Test(expected = DuplicateUserEmailException.class)
	public void testAddUserDuplicateUserEmailException() throws DuplicateUserEmailException, DuplicateUserException,
			DuplicateUserPseudoException, InputSyntaxException {
		instance.addUser(u1);

		u2 = new User("aaaa", "aaaa", u1.getEmail());
		instance.addUser(u2);
	}

	@Test(expected = DuplicateUserException.class)
	public void testAddUserDuplicateUserException() throws DuplicateUserEmailException, DuplicateUserException,
			DuplicateUserPseudoException, InputSyntaxException {
		instance.addUser(u1);
		instance.addUser(u1);
	}

	@Test(expected = DuplicateUserPseudoException.class)
	public void testAddUserDuplicateUserPseudoException() throws DuplicateUserEmailException, DuplicateUserException,
			DuplicateUserPseudoException, InputSyntaxException {
		instance.addUser(u1);

		u2 = new User(u1.getPseudo(), "aaaa", "aasaas@geqzx.c");
		instance.addUser(u2);
	}

	@Test
	public void testIsUsedPseudo() {
		users.add(u1);
		assertTrue("Pseudo should be used", instance.isUsedPseudo(u1.getPseudo()));
	}

	@Test
	public void testIsUsedEmail() {
		users.add(u1);
		assertTrue("Email should be used", instance.isUsedEmail(u1.getEmail()));
	}

	@Test
	public void testRemoveUser() {
		users.add(u1);
		instance.removeUser(u1);
		instance.removeUser(null);
		assertFalse("Should not contains the user", users.contains(u1));
	}

	@Test
	public void testRemoveUserByPseudo() {
		instance.removeUserByPseudo(u1.getPseudo());
		users.add(u2);
		users.add(u1);
		instance.removeUserByPseudo(u2.getPseudo());
		assertFalse("Should not contains the user", users.contains(u2));
	}

	@Test
	public void testClearUsers() {
		users.add(u1);
		users.add(u2);
		instance.clearUsers();
		assertTrue("Should contains no users", users.size() == 0);
	}

	@Test
	public void testGetUsers() {
		assertTrue("Should be empty", instance.getUsers().size() == 0);

		users.add(u1);
		users.add(u2);
		Set<User> users2 = instance.getUsers();
		assertTrue("Should not be empty", users2.size() == 2);
		assertTrue("Return should contains the same users", users2.contains(u1));
		assertTrue("Return should contains the same users", users2.contains(u2));
	}

	@Test
	public void testGetUsersAsList() {
		assertTrue("Should be empty", instance.getUsersAsList().size() == 0);

		users.add(u1);
		users.add(u2);
		List<User> users2 = instance.getUsersAsList();
		assertTrue("Should not be empty", users2.size() == 2);
		assertTrue("Return should contains the same users", users2.contains(u1));
		assertTrue("Return should contains the same users", users2.contains(u2));
	}

	@Test
	public void getUserByPseudo() {
		users.add(u1);
		users.add(u2);

		assertEquals("Should returns another user", instance.getUserByPseudo(u1.getPseudo()), u1);
		assertNull("Should returns null", instance.getUserByPseudo("hello"));
	}

	@Test
	public void testUsersSize() {
		assertTrue("Should be empty", instance.usersSize() == 0);
		users.add(u1);
		users.add(u2);
		assertTrue("Should contains 2 elements", instance.usersSize() == 2);
	}

	@Test
	public void testContainsUser() {
		assertFalse("Shouldn't contain this user", instance.containsUser(u1));
		users.add(u1);
		assertTrue("Should contain this user", instance.containsUser(u1));
	}

	@Test
	public void testToString() throws DuplicateUserPseudoException, DuplicateUserEmailException, DuplicateUserException,
			InputSyntaxException {
		UserManagerSingleton.getInstance().addUser(new User("test", "test", "test@gmail.com"));
		UserManagerSingleton.getInstance().toString();
	}

	@Test
	public void testUpdateUser() throws DuplicateUserPseudoException, DuplicateUserEmailException,
			DuplicateUserException, InputSyntaxException {
		users.add(u1);
		instance.updateUser(u1, u2);
		assertFalse("Shouln't contain u1 anymore", users.contains(u1));
		assertTrue("Should now contains u2", users.contains(u2));

		instance.updateUser(new User("notContained", "password", "helha@gmail.coooom"), u1);
	}

	@Test
	public void testEqualsObject() {
		users.add(u1);
		users.add(u2);

		UserManagerSingleton instance2 = UserManagerSingleton.getInstance();
		Set<User> users2 = (Set<User>) Explorer.getField(instance2, "users");
		users2.add(u1);
		users2.add(u2);

		assertEquals("Should be equal cause of " + "same fields", instance, instance2);

		assertEquals("Should be equal cause of same reference", instance, instance);

		assertNotEquals("Shouldn't be equal cause of null", instance, null);
		assertNotEquals("Shouldn't be equal cause of noot same class", instance, new Object());
	}

	@Test
	public void testHashCode() {
		assertTrue("Should have the same hashcode",
				UserManagerSingleton.getInstance().hashCode() == instance.hashCode());
	}

	@Test
	public void testIncrementUserTotalEarningsWon() {
		instance.incrementUserTotalEarningsWon(u1, 30);
		users.add(u1);
		instance.incrementUserTotalEarningsWon(u1, 50);
		assertTrue("u1 should have 50 total earnings won", u1.getTotalEarningsWon() == 50);

		instance.incrementUserTotalEarningsWon(u1, 130);
		assertTrue("u1 should have 180 total earnings won", u1.getTotalEarningsWon() == 180);

		instance.incrementUserTotalEarningsWon(u1, -20);
		assertTrue("u1 should still have 180 total earnings won", u1.getTotalEarningsWon() == 180);

		users.add(u2);
		instance.incrementUserTotalEarningsWon(u2, 30);
		assertTrue("u2 should have 30 total earnings won", u2.getTotalEarningsWon() == 30);
		assertTrue("u1 should still have 180 total earnings won", u1.getTotalEarningsWon() == 180);
	}

	@Test
	public void testIncrementUserPartiesWon() {
		instance.incrementUserPartiesWon(u1);
		users.add(u1);
		instance.incrementUserPartiesWon(u1);
		assertTrue("u1 should have 1 party won", u1.getPartiesWon() == 1);

		instance.incrementUserPartiesWon(u1);
		assertTrue("u1 should have 2 parties won", u1.getPartiesWon() == 2);

		users.add(u2);
		instance.incrementUserPartiesWon(u2);
		assertTrue("u2 should have 1 party won", u2.getPartiesWon() == 1);
		assertTrue("u1 should still have 2 parties won", u1.getPartiesWon() == 2);
	}

	@Test
	public void testIncrementUserPartiesPlayied() {
		instance.incrementUserPartiesPlayied(u1);
		users.add(u1);
		instance.incrementUserPartiesPlayied(u1);
		assertTrue("u1 should have 1 party played", u1.getPartiesPlayed() == 1);

		instance.incrementUserPartiesPlayied(u1);
		assertTrue("u1 should have 2 parties played", u1.getPartiesPlayed() == 2);

		users.add(u2);
		instance.incrementUserPartiesPlayied(u2);
		assertTrue("u2 should have 1 party played", u2.getPartiesPlayed() == 1);
		assertTrue("u1 should still have 2 parties played", u1.getPartiesPlayed() == 2);
	}

	@Test
	public void testSetUserHighestEarningsWon() {
		// User's not present, shouldn't change the value
		instance.setUserHighestEarningsWon(u1, 150);

		users.add(u1);
		// Now that the user's present, it should work
		instance.setUserHighestEarningsWon(u1, 80);
		assertTrue("u1 should have 80 as highest earnings", u1.getHighestEarningsWon() == 80);

		// The new earnings are higher than the old ones, so it should work
		instance.setUserHighestEarningsWon(u1, 95);
		assertTrue("u1 should now have 95 as highest earnings", u1.getHighestEarningsWon() == 95);

		// The new earnings are lower than the old ones, so it shouldn't change anything
		instance.setUserHighestEarningsWon(u1, 85);
		assertTrue("u1 should still have 95 as highest earnings", u1.getHighestEarningsWon() == 95);
		
		// The new earnings are negatives so it shouldn't change anything
		instance.setUserHighestEarningsWon(u1, -1);
		assertTrue("u1 should still have 95 as highest earnings", u1.getHighestEarningsWon() == 95);

		users.add(u2);
		// Should only change the user 2 highest earnings won
		instance.setUserHighestEarningsWon(u2, 30);
		assertTrue("u2 should have 30 as highest earnings", u2.getHighestEarningsWon() == 30);
		assertTrue("u1 should still have 95 as highest earnings", u1.getHighestEarningsWon() == 95);
	}
}
