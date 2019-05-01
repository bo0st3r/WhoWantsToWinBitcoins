package unitary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import exceptions.InputSyntaxException;
import model.User;
import utilities.Explorer;
import utilities.SHA256Hasher;

public class TestUser {
	private User user;
	private String pseudo;
	private String password;
	private String email;
	private boolean admin;
	private int partiesWon;
	private int partiesPlayed;
	private double highestEarningsWon;
	private double totalEarningsWon;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		user = new User("testUser", "password", "test-user@testing.com");
		pseudo = (String) Explorer.getField(user, "pseudo");
		password = (String) Explorer.getField(user, "password");
		email = (String) Explorer.getField(user, "email");
	}

	@After
	public void tearDown() throws Exception {
		user = null;
		pseudo = password = email = null;
	}

	@Test
	public void testUserStringStringString() throws InputSyntaxException {
		assertEquals("Usernames should be equal", pseudo, "testUser");
		assertEquals("Passwords should be equal", password, SHA256Hasher.hashString("password"));
		assertEquals("Emails should be equal", email, "test-user@testing.com");
		assertEquals("Admins should be false", admin, false);
		assertTrue("partiesWon should be set to 0", partiesWon == 0);
		assertTrue("partiesPlayed should be set to 0", partiesPlayed == 0);
		assertTrue("highestEarningsWon should be set to 0", highestEarningsWon == 0);
		assertTrue("totalEarningsWon should be set to 0", totalEarningsWon == 0);
	}

	@Test(expected = InputSyntaxException.class)
	public void testUserInputSyntaxExceptionPseudo() throws InputSyntaxException {
		User u = new User("use!r", "helha", "la15555@helha.be");
	}

	@Test(expected = InputSyntaxException.class)
	public void testUserInputSyntaxExceptionPassword() throws InputSyntaxException {
		User u = new User("user", "he", "la15555@helha.be");
	}

	@Test(expected = InputSyntaxException.class)
	public void testUserInputSyntaxExceptionEmail() throws InputSyntaxException {
		User u = new User("user", "helha", "la15555@helha.be@qze");
	}

	@Test
	public void testUserStringStringStringBoolean() throws InputSyntaxException {
		User u = new User("testUser", "password", "test-user@testing.com", true);

		assertTrue("admin should be true", u.isAdmin());
	}

	@Test
	public void testGetHashedPassword() {
		assertTrue("Password should be " + SHA256Hasher.HASH_SIZE + " characters long.",
				User.getHashedPassword(password).length() == SHA256Hasher.HASH_SIZE);
		assertNotEquals("Hashed password should be different.", User.getHashedPassword(password), password);
		assertNotNull("Hashed password shouldn't be null.", User.getHashedPassword(password));
	}

	@Test
	public void testSetPassword() throws InputSyntaxException {
		user.setPassword("newPassword");
		String hashedPassword = SHA256Hasher.hashString("newPassword");
		assertEquals("Passwords should be equals", user.getPassword(), hashedPassword);
	}

	@Test(expected = InputSyntaxException.class)
	public void testSetPasswordInputSyntaxException() throws InputSyntaxException {
		user.setPassword("ne");
	}

	@Test
	public void testIsHashedPassword() {
		assertTrue("Should return true", User.isHashedPassword(password));
		assertFalse("Should return false", User.isHashedPassword("test"));
	}

	@Test
	public void testComparePassword() {
		assertTrue("Should be true", user.comparePassword("password"));
		assertFalse("Shouldn't be true", user.comparePassword("NOTTHESAMEPASSWORD"));
	}

	@Test
	public void testGetPseudo() {
		assertEquals("Emails should be equal", user.getPseudo(), "testUser");
	}

	@Test
	public void testGetPassword() {
		String hashedPassword = SHA256Hasher.hashString("password");
		assertEquals("Emails should be equal", user.getPassword(), hashedPassword);
	}

	@Test
	public void testGetEmail() {
		assertEquals("Emails should be equal", user.getEmail(), "test-user@testing.com");
	}

	@Test
	public void testIsAdmin() throws InputSyntaxException {
		assertFalse("Should be false", user.isAdmin());
		User admin = new User("admin", "password", "admin@admin.com", true);
		assertTrue("Should be true", admin.isAdmin());
	}

	@Test
	public void testSetAdmin() {
		assertFalse("Should be false", admin);
		user.setAdmin(true);
		boolean admin = (boolean) Explorer.getField(user, "admin");
		assertTrue("Should be true", admin);
	}

	@Test
	public void testGetTotalEarningsWon() {
		assertTrue("totalEarningsWon should be set to 0", user.getTotalEarningsWon() == 0);
	}

	@Test
	public void testIncrementTotalEarningsWon() {
		user.incrementTotalEarningsWon(-5);
		double value = (double) Explorer.getField(user, "totalEarningsWon");
		assertTrue("totalEarningsWon should be set to 0", value == 0);

		user.incrementTotalEarningsWon(500);
		value = (double) Explorer.getField(user, "totalEarningsWon");
		assertTrue("totalEarningsWon should be set to 500", value == 500);
	}

	@Test
	public void testGetPartiesWon() {
		assertTrue("partiesWon should be set to 0", user.getPartiesWon() == 0);
	}

	@Test
	public void testIncrementPartiesWon() {
		user.incrementPartiesWon();
		int value = (int) Explorer.getField(user, "partiesWon");
		assertTrue("partiesWon should be set to 1", value == 1);

		user.incrementPartiesWon();
		user.incrementPartiesWon();
		value = (int) Explorer.getField(user, "partiesWon");
		assertTrue("partiesWon should be set to 1", value == 3);
	}

	@Test
	public void testGetPartiesPlayed() {
		assertTrue("partiesPlayed should be set to 0", user.getPartiesPlayed() == 0);
	}

	@Test
	public void testIncrementPartiesPlayed() {
		user.incrementPartiesPlayed();
		int value = (int) Explorer.getField(user, "partiesPlayed");
		assertTrue("partiesPlayed should be set to 1", value == 1);

		user.incrementPartiesPlayed();
		user.incrementPartiesPlayed();
		value = (int) Explorer.getField(user, "partiesPlayed");
		assertTrue("partiesPlayed should be set to 1", value == 3);
	}

	@Test
	public void testNumberPartiesLost() {
		user.incrementPartiesWon();
		assertTrue("Should be equal to -1", user.numberPartiesLost() == -1);

		user.incrementPartiesPlayed();
		user.incrementPartiesPlayed();
		user.incrementPartiesPlayed();
		assertTrue("Should be equal to 2", user.numberPartiesLost() == 2);

		user.incrementPartiesWon();
		assertTrue("Should be equal to 1", user.numberPartiesLost() == 1);
	}

	@Test
	public void testGetHighestEarningsWon() {
		double value = 523.4;
		user.setHighestEarningsWon(value);
		assertTrue("highestEarningsWon should be equal to " + 523.4, user.getHighestEarningsWon() == value);
	}

	@Test
	public void testSetHighestEarningsWon() {
		user.setHighestEarningsWon(-10.);
		double value = (double) Explorer.getField(user, "highestEarningsWon");
		assertTrue("highestEarningsWon shoud be equal to 0", value == 0);

		user.setHighestEarningsWon(52.3);
		value = (double) Explorer.getField(user, "highestEarningsWon");
		assertTrue("highestEarningsWon shoud be equal to 52.3", value == 52.3);

		user.setHighestEarningsWon(50);
		value = (double) Explorer.getField(user, "highestEarningsWon");
		assertTrue("highestEarningsWon shoud be equal to 52.3", value == 52.3);
	}

	@Test
	public void testClone() {
		User clone = user.clone();
		assertEquals("Users should be equal", user, clone);
		assertTrue("Users should have a different reference", user != clone);
	}

	@Test
	public void testEqualsObject() throws InputSyntaxException {
		User copy = new User("testUser", "password", "test-user@testing.com");
		assertEquals("Users should be equal", user, copy);

		copy.setPassword("passtest");
		assertNotEquals("Shouldn' be equal cause of different fields", user, copy);
		assertEquals("Should be equal cause of same reference", user, user);
		assertNotEquals("Shouldn't be equal cause of null", user, null);
		assertNotEquals("Shouldn't be equal cause of noot same class", user, new Object());
	}

	@Test
	public void testHashCode() throws InputSyntaxException {
		User other = new User("testUser", "password", "test-user@testing.com");
		assertTrue("Hashcode shoud be equal", user.hashCode() == (other.hashCode()));

		other = new User("testUser", "newPassword", "test-user@testing.com");
		assertFalse("Hashcode shoudn't be equal", user.hashCode() == (other.hashCode()));
	}
}
