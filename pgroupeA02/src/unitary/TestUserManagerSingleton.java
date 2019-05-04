package unitary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
import model.Earning;
import model.User;
import model.UserManagerSingleton;
import utilities.Explorer;

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
	public void testUsersSize() {
		assertTrue("Should be empty", instance.usersSize() == 0);
		users.add(u1);
		users.add(u2);
		assertTrue("Should contains 2 elements", instance.usersSize() == 2);
	}

	@Test
	public void testToString() throws DuplicateUserPseudoException, DuplicateUserEmailException, DuplicateUserException,
			InputSyntaxException {
		UserManagerSingleton.getInstance().addUser(new User("test", "test", "test@gmail.com"));
		UserManagerSingleton.getInstance().toString();
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
}
