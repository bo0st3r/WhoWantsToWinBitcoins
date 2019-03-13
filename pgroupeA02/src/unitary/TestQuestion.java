package unitary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import enumerations.Round;
import exceptions.AnswerAlreadyPresentException;
import exceptions.NeedRightAnswerException;
import exceptions.NoRightAnswerException;
import exceptions.NotARoundException;
import exceptions.RightAnswerAlreadyPresentException;
import exceptions.StatementTooShortException;
import exceptions.TooMuchAnswersException;
import model.Question;
import utilities.Explorer;

public class TestQuestion {
	private Question question;
	private static String author;
	private static String statement;
	private static Round round;
	private Map<String, Boolean> choices;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		author = "Author";
		statement = "It is a random statement";
		round = Round.FIRST_ROUND;
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		question = new Question(author, statement, round);
		choices = (Map<String, Boolean>) Explorer.getField(question, "choices");
	}

	@After
	public void tearDown() throws Exception {
		question = null;
		choices = null;
	}

	@Test
	public void testQuestion() {
		assertTrue("Author name not set", question.getAuthor().equals("Author"));
		assertTrue("Statement not set", question.getStatement().equals("It is a random statement"));
		assertTrue("Round not set", question.getRound() == Round.FIRST_ROUND);
	}

	@Test(expected = StatementTooShortException.class)
	public void testQuestionStatementTooShortException() throws StatementTooShortException, NotARoundException {
		question = new Question("Author", "123456789", Round.FIRST_ROUND);
	}

	@Test(expected = NotARoundException.class)
	public void testQuestionNotARoundException() throws StatementTooShortException, NotARoundException {
		question = new Question("Author", "It is a random statement", null);
	}

	@Test
	public void testQuestionEmptyAuthor() throws StatementTooShortException, NotARoundException {
		question = new Question("", "It is a random statement", Round.FIRST_ROUND);
		assertEquals("Author remains empty", question.getAuthor(), "Satoshi Nakamoto");
	}

	@Test
	public void testAddChoice() throws TooMuchAnswersException, AnswerAlreadyPresentException,
			RightAnswerAlreadyPresentException, NeedRightAnswerException {
		question.addChoice("1", false);
		assertTrue("1st choice not added to the question", choices.size() == 1);

		question.addChoice("2", false);
		assertTrue("2nd choice not added to the question", choices.size() == 2);

		question.addChoice("3", false);
		assertTrue("3rd choice not added to the question", choices.size() == 3);

		question.addChoice("4", true);
		assertTrue("4th choice not added to the question", choices.size() == 4);
	}

	@Test(expected = TooMuchAnswersException.class)
	public void testAddChoiceTooMuchAnswersException() throws TooMuchAnswersException, AnswerAlreadyPresentException,
			RightAnswerAlreadyPresentException, NeedRightAnswerException {
		question.addChoice("1", false);
		question.addChoice("2", true);
		question.addChoice("3", false);
		question.addChoice("4", false);
		question.addChoice("5", false);
	}

	@Test(expected = AnswerAlreadyPresentException.class)
	public void testAddChoiceAnswerAlreadyPresentException() throws TooMuchAnswersException,
			AnswerAlreadyPresentException, RightAnswerAlreadyPresentException, NeedRightAnswerException {
		question.addChoice("1", false);
		question.addChoice("1", false);
	}

	@Test(expected = RightAnswerAlreadyPresentException.class)
	public void testAddChoiceRightAnswerAlreadyPresentException() throws TooMuchAnswersException,
			AnswerAlreadyPresentException, RightAnswerAlreadyPresentException, NeedRightAnswerException {
		question.addChoice("1", true);
		question.addChoice("2", true);
	}

	@Test(expected = NeedRightAnswerException.class)
	public void testAddChoiceNeedRightAnswerException() throws TooMuchAnswersException, AnswerAlreadyPresentException,
			RightAnswerAlreadyPresentException, NeedRightAnswerException {
		question.addChoice("1", false);
		question.addChoice("2", false);
		question.addChoice("3", false);
		question.addChoice("4", false);
	}

	@Test
	public void testRemoveChoice() {
		choices.put(statement, false);
		question.removeChoice(statement);
		assertFalse("Choise not removed", choices.containsKey(statement));
	}

	@Test
	public void testRemoveRightAnswer() {
		choices.put("1", false);
		choices.put("2", false);
		choices.put("3", false);
		question.removeRightAnswer();
		assertTrue("A not true answer has been removed", choices.size() == 3);
		choices.put("4", true);
		question.removeRightAnswer();
		assertTrue("Right answer hasn't been removed", choices.size() == 3);

	}

	@Test
	public void testGetRightAnswer() throws NoRightAnswerException {
		choices.put("1", false);
		choices.put("2", false);
		choices.put("3", false);
		choices.put("4", true);
		assertEquals("A non true answer was removed", question.getRightAnswer(), "4");
	}

	@Test(expected = NoRightAnswerException.class)
	public void testGetRightAnswerNoRightAnswerException() throws NoRightAnswerException {
		choices.put("1", false);
		question.getRightAnswer();
	}

	@Test
	public void testClearChoices() {
		choices.put("1", false);
		choices.put("2", false);
		choices.put("3", false);
		choices.put("4", true);
		question.clearChoices();
		assertEquals("Choices wasn't cleared", choices.size(), 0);
	}

	@Test
	public void testClone() throws StatementTooShortException {
		choices.put("1", false);
		choices.put("2", false);
		choices.put("3", false);
		choices.put("4", true);

		Question question2 = question.clone();
		assertEquals("Questions statements are not equals", question.getStatement(), question2.getStatement());
		assertEquals("Questions authors are not equals", question.getAuthor(), question2.getAuthor());
		assertEquals("Questions rounds are not equals", question.getRound(), question2.getRound());
		assertEquals("Questions choices are not equals", choices.size(), question2.getChoices().size());

		assertNotSame("Questions are exactly the same objects", question, question2);
	}

	@Test
	public void testEqualsObject() throws StatementTooShortException, NotARoundException {
		Question question2 = new Question(author, statement, round);
		assertTrue("Question equals doesn't work", question.equals(question2));
	}

	@Test
	public void testSetStatement() throws StatementTooShortException {
		question.setStatement("Test Test Test Test Test Test");
		assertNotEquals("Statement wasn't changed", question.getStatement(), statement);
	}

	@Test(expected = StatementTooShortException.class)
	public void testSetStatementStatementTooShortException() throws StatementTooShortException {
		question.setStatement("Should fail");
	}

	@Test
	public void testNbAnswers() {
		choices.put("1", false);
		choices.put("2", false);
		choices.put("3", false);
		assertEquals("Didn't get the right number of choices", question.getNbAnswers(), 3);
	}

}
