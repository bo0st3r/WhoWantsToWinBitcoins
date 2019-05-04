package unitary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import enumerations.Round;
import exceptions.DeckUnderFilledException;
import exceptions.EmptyQuestionsListException;
import exceptions.ExceedMaxStepsException;
import exceptions.NotARoundException;
import exceptions.NotEnoughAnswersException;
import exceptions.NotEnoughQuestionsException;
import exceptions.QuestionAlreadyPresentException;
import exceptions.StatementTooShortException;
import model.Deck;
import model.Party;
import model.Question;
import utilities.Explorer;
import utilities.Serialization;

public class TestParty {
	private Party party;
	private List<Question> chosenQuestions;
	private Round actualRound;
	private int actualStep;
	private int rightAnswerIndex;
	private String rightAnswer;
	private int jokerFriendIndex;
	private List<Double> jokerPublicPercents;
	private List<Integer> joker5050Indexes;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		party = new Party(Serialization.jsonToDeck(Party.FILE_NAME));
		chosenQuestions = (List<Question>) Explorer.getField(party, "chosenQuestions");
		actualRound = (Round) Explorer.getField(party, "actualRound");
	}

	@After
	public void tearDown() throws Exception {
		party = null;
		chosenQuestions = null;
	}

	@Test
	public void testParty() throws EmptyQuestionsListException, DeckUnderFilledException, NotEnoughQuestionsException {
		Party party2 = new Party(Serialization.jsonToDeck(Party.FILE_NAME));
		List<Question> chosenQuestions = (List<Question>) Explorer.getField(party2, "chosenQuestions");
		assertTrue("Should have " + Party.NB_STEPS + " chosen questions", chosenQuestions.size() == Party.NB_STEPS);
	}

	@Test(expected = EmptyQuestionsListException.class)
	public void testPartyEmptyQuestionsListException()
			throws EmptyQuestionsListException, DeckUnderFilledException, NotEnoughQuestionsException {
		party = new Party(new Deck());
	}

	@Test(expected = DeckUnderFilledException.class)
	public void testPartyDeckUnderFilledException()
			throws EmptyQuestionsListException, DeckUnderFilledException, NotEnoughQuestionsException {

		Deck deck = new Deck();

		try {
			Question q1 = new Question("test", "testeeeeeeeeeeeeeeeeeeeeeeee", Round.FIRST_ROUND);
			Map<String, Boolean> choices = (Map<String, Boolean>) Explorer.getField(q1, "choices");
			choices.put("1", false);
			choices.put("2", false);
			choices.put("3", true);
			choices.put("4", false);
			deck.addQuestion(q1);

		} catch (QuestionAlreadyPresentException | NotEnoughAnswersException | StatementTooShortException
				| NotARoundException e) {
			e.printStackTrace();
		}

		party = new Party(deck);
	}

	@Test(expected = NotEnoughQuestionsException.class)
	public void testPartyNotEnoughQuestionsException()
			throws EmptyQuestionsListException, DeckUnderFilledException, NotEnoughQuestionsException {

		Deck deck = new Deck();
		List<Question> questions = (List<Question>) Explorer.getField(deck, "questions");

		Question q1;
		try {
			q1 = new Question("test", "testeeeeeeeeeeeeeeeeeeeeeeee", Round.FIRST_ROUND);
			Map<String, Boolean> choices = (Map<String, Boolean>) Explorer.getField(q1, "choices");
			choices.put("1", false);
			choices.put("2", false);
			choices.put("3", true);
			choices.put("4", false);

			for (int i = 0; i <= 6; i++) {
				Question q2 = q1.clone();
				questions.add(q2);
			}
			for (int i = 0; i <= 4; i++) {
				Question q2 = q1.clone();
				q2.setRound(Round.SECOND_ROUND);
				questions.add(q2);
			}
			for (int i = 0; i <= 3; i++) {
				Question q2 = q1.clone();
				q2.setRound(Round.LAST_ROUND);
				questions.add(q2);
			}
		} catch (StatementTooShortException | NotARoundException e) {
			System.out.println("failed question constructor");
		}

		party = new Party(deck);
	}

	@Test
	public void testHasNextStep() throws ExceedMaxStepsException {
		assertTrue("Should have a next step", party.hasNextStep());
		for (int i = 0; i <= Party.NB_STEPS - 1; i++) {
			party.incrementActualStep();
		}
		assertFalse("Shouldn't have a next step", party.hasNextStep());
	}

	@Test(expected = ExceedMaxStepsException.class)
	public void testHasNextStepExceedMaxStepsException() throws ExceedMaxStepsException {
		for (int i = 0; i <= Party.NB_STEPS; i++) {
			party.incrementActualStep();
		}
	}

	@Test
	public void testGetNbQuestionsForRound() {
		for (Round r : Round.values()) {
			assertTrue("Should have " + Party.NB_STEPS_BY_ROUND + " steps for the round " + r.toString(),
					party.getNbQuestionsForRound(r) == Party.NB_STEPS_BY_ROUND);
		}
	}

	@Test
	public void testNbChosenQuestion() {
		assertTrue("Should contains " + Party.NB_STEPS + " questions", party.nbChosenQuestion() == Party.NB_STEPS);
	}

	@Test
	public void testGetQuestionNextStep() throws ExceedMaxStepsException {
		Question next = chosenQuestions.get(0);
		assertEquals("Should be equal", next, party.getQuestionNextStep());
		next = chosenQuestions.get(1);
		assertEquals("Should be equal", next, party.getQuestionNextStep());
	}

	@Test(expected = ExceedMaxStepsException.class)
	public void testGetQuestionNextStepExceedMaxStepsException() throws ExceedMaxStepsException {
		for (int i = 0; i <= Party.NB_STEPS; i++) {
			party.getQuestionNextStep();
		}
	}

	@Test
	public void testGoToNextRound() {
		Round[] rounds = Round.values();

		for (int i = 1; i <= 2; i++) {
			party.goToNextRound();
			actualRound = (Round) Explorer.getField(party, "actualRound");
			assertEquals("Actual round should be " + rounds[i], actualRound, rounds[i]);
		}
	}

	@Test
	public void testGetActualRound() {
		assertEquals("Should be " + Round.FIRST_ROUND, party.getActualRound(), actualRound);
	}

	@Test
	public void testIsNeedingNextRound() throws ExceedMaxStepsException {
		for (int i = 0; i <= 5; i++) {
			party.incrementActualStep();
		}
		assertTrue("Should neext the next round", party.isNeedingNextRound());
	}

	@Test
	public void testGetActualStep() throws ExceedMaxStepsException {
		assertTrue("Should be step 1", party.getActualStep() == 0);
		party.incrementActualStep();
		party.incrementActualStep();
		assertTrue("Should be step 1", party.getActualStep() == 2);
	}

	@Test
	public void testToString() {
		party.toString();
	}

	@Test
	public void testGetRightAnswerIndex() {
		int rightAnswerIndex = (int) Explorer.getField(party, "rightAnswerIndex");
		assertTrue("The right answer index should be " + rightAnswerIndex,
				rightAnswerIndex == party.getRightAnswerIndex());
	}

	@Test
	public void testSetRightAnswerIndex() {
		party.setRightAnswerIndex(3);
		int rightAnswerIndex = (int) Explorer.getField(party, "rightAnswerIndex");
		assertTrue("The right answer index should be 3", rightAnswerIndex == 3);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testSetRightAnswerIndexOutOfBoundsException1() {
		party.setRightAnswerIndex(Question.NB_ANSWERS + 1);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testSetRightAnswerIndexOutOfBoundsException2() {
		party.setRightAnswerIndex(-1);
	}

	@Test
	public void testGetRightAnswer() {
		String rightAnswer = (String) Explorer.getField(party, "rightAnswer");
		assertEquals("The right answer statement should be " + rightAnswer, party.getRightAnswer(), rightAnswer);
	}

	@Test
	public void testSetRightAnswer() {
		party.setRightAnswer("TEST TEST");
		String rightAnswer = (String) Explorer.getField(party, "rightAnswer");
		assertEquals("The right answer index should be 3", rightAnswer, "TEST TEST");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetRightAnswerIllegalArgumentException1() {
		party.setRightAnswer(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetRightAnswerIllegalArgumentException2() {
		party.setRightAnswer("");
	}

	@Test
	public void testGetJokerFriendIndex() {
		assertTrue("Should be equal to 0", party.getJokerFriendIndex() == -1);
	}

	@Test
	public void testSetJokerFriendIndex() {
		party.setJokerFriendIndex(3);
		int jokerFriendIndex = (int) Explorer.getField(party, "jokerFriendIndex");
		assertTrue("Should be equal to 3", jokerFriendIndex == 3);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testSetJokerFriendIndexIndexOutOfBoundsException1() {
		party.setJokerFriendIndex(Question.NB_ANSWERS + 1);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testSetJokerFriendIndexIndexOutOfBoundsException2() {
		party.setJokerFriendIndex(-1);
	}

	@Test
	public void testGetJokerPublicPercents() {
		assertTrue("Should be empty", party.getJokerPublicPercents().isEmpty());
	}

	@Test
	public void testSetJokerPublicPercents() {
		List<Double> jokerPublicPercents = new ArrayList<>();
		jokerPublicPercents.add(30.);
		jokerPublicPercents.add(35.);
		jokerPublicPercents.add(12.);
		jokerPublicPercents.add(28.);

		party.setJokerPublicPercents(jokerPublicPercents);

		List<Double> jokerPublicPercents2 = (List<Double>) Explorer.getField(party, "jokerPublicPercents");
		assertEquals("Should be equal", jokerPublicPercents, jokerPublicPercents2);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testSetJokerPublicPercentsIndexOutOfBoundsException1() throws IndexOutOfBoundsException {
		List<Double> jokerPublicPercents = new ArrayList<>();
		jokerPublicPercents.add(-1.);
		jokerPublicPercents.add(35.);
		jokerPublicPercents.add(12.);
		jokerPublicPercents.add(28.);
		party.setJokerPublicPercents(jokerPublicPercents);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testSetJokerPublicPercentsIndexOutOfBoundsException2() throws IndexOutOfBoundsException {
		List<Double> jokerPublicPercents = new ArrayList<>();
		jokerPublicPercents.add(101.);
		jokerPublicPercents.add(35.);
		jokerPublicPercents.add(12.);
		jokerPublicPercents.add(28.);
		party.setJokerPublicPercents(jokerPublicPercents);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetJokerPublicPercentsIllegalArgumentException1() throws IllegalArgumentException {
		List<Double> jokerPublicPercents = new ArrayList<>();
		jokerPublicPercents.add(101.);
		jokerPublicPercents.add(35.);
		jokerPublicPercents.add(12.);
		party.setJokerPublicPercents(jokerPublicPercents);
	}

	@Test
	public void testGetJoker5050Indexes() {
		assertTrue("Should be empty", party.getJoker5050Indexes().isEmpty());

	}

	@Test
	public void testSetJoker5050Indexes() {
		List<Integer> joker5050Indexes = new ArrayList<>();
		joker5050Indexes.add(0);
		joker5050Indexes.add(2);

		party.setJoker5050Indexes(joker5050Indexes);

		List<Integer> joker5050Indexes2 = (List<Integer>) Explorer.getField(party, "joker5050Indexes");
		assertEquals("Should be equal", joker5050Indexes, joker5050Indexes2);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testSetJoker5050IndexesIndexOutOfBoundsException1() {
		List<Integer> joker5050Indexes = new ArrayList<>();
		joker5050Indexes.add(0);
		joker5050Indexes.add(-1);
		party.setJoker5050Indexes(joker5050Indexes);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testSetJoker5050IndexesIndexOutOfBoundsException2() {
		List<Integer> joker5050Indexes = new ArrayList<>();
		joker5050Indexes.add(0);
		joker5050Indexes.add(Question.NB_ANSWERS + 1);
		party.setJoker5050Indexes(joker5050Indexes);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetJoker5050IndexesIllegalArgumentException() {
		List<Integer> joker5050Indexes = new ArrayList<>();
		joker5050Indexes.add(0);
		party.setJoker5050Indexes(joker5050Indexes);
	}

	@Test
	public void testClearJoker5050Indexes() {
		List<Integer> joker5050Indexes = (List<Integer>) Explorer.getField(party, "joker5050Indexes");
		joker5050Indexes.add(0);
		joker5050Indexes.add(2);
		party.clearJoker5050Indexes();

		assertTrue("Should be empty", party.getJoker5050Indexes().isEmpty());
	}
}
