package unitary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Earning;
import model.Party;
import utilities.Explorer;

public class TestEarning {
	private Earning earning;
	private List<Integer> amounts;
	private int a1;
	private int a2;
	private int a3;
	private int a4;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		earning = new Earning();
		amounts = (List<Integer>) Explorer.getField(earning, "amounts");
		a1 = 50;
		a2 = 100;
		a3 = 200;
		a4 = 500;
	}

	@After
	public void tearDown() throws Exception {
		earning = null;
		amounts = null;
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testEarning() {
		assertEquals("Amounts is not empty", amounts.size(), 0);
		assertNotNull("Amounts field is null", amounts);

		// With JSON constructor
		Earning earningJSON = new Earning(Earning.FILE_NAME);
		List<Integer> amountsJSON = (List<Integer>) Explorer.getField(earningJSON, "amounts");
		assertEquals("Amounts is not empty", amountsJSON.size(), Party.NB_STEPS);
		assertNotNull("Amounts field is null", amountsJSON);

		// With main JSON earning file
		Earning earningJSON2 = Earning.earning();
		List<Integer> amountsJSON2 = (List<Integer>) Explorer.getField(earningJSON2, "amounts");
		assertEquals("Amounts is not empty", amountsJSON2.size(), Party.NB_STEPS);
		assertNotNull("Amounts field is null", amountsJSON2);
	}

	@Test
	public void testAddAmount() {
		// 0 and negative elements
		earning.addAmount(0);
		earning.addAmount(-1);
		assertEquals("Amounts should be empty", amounts.size(), 0);

		// Max elements
		for (int i = 0; i <= Party.NB_STEPS; i++) {
			earning.addAmount((i + 1) * 30);
		}
		assertEquals("Amounts should have such element as Party NB_STEPS field", amounts.size(), Party.NB_STEPS);
	}

	@Test
	public void testSetAmount() {
		amounts.add(a1);
		amounts.add(a2);
		amounts.add(a3);
		amounts.add(a4);

		// 0 index
		earning.setAmount(0, a2 - 1);
		assertTrue("Index 0 should have changed", amounts.get(0) == (a2 - 1));

		// positive index
		earning.setAmount(2, a3 + 1);
		assertTrue("Index 2 should have changed", amounts.get(2) == (a3 + 1));

		// max index
		earning.setAmount(3, a4 + 1);

	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testSetAmountIndexOutOfBoundsException() {
		earning.setAmount(-1, 5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetAmountIndex0IllegalArgumentException() {
		amounts.add(a1);
		amounts.add(a2);
		amounts.add(a3);
		amounts.add(a4);

		earning.setAmount(0, a2 + 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetAmountIndexMaxIllegalArgumentException() {
		amounts.add(a1);
		amounts.add(a2);
		amounts.add(a3);
		amounts.add(a4);

		earning.setAmount(3, a2 - 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetAmountIndexPositiveIllegalArgumentException() {
		amounts.add(a1);
		amounts.add(a2);
		amounts.add(a3);
		amounts.add(a4);

		earning.setAmount(2, a2 - 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetAmountIndexPositiveIllegalArgumentException2() {
		amounts.add(a1);
		amounts.add(a2);
		amounts.add(a3);
		amounts.add(a4);

		earning.setAmount(2, a4 + 1);
	}

	@Test
	public void testRemoveAmount() {
		amounts.add(a1);
		amounts.add(a2);
		amounts.add(a3);
		amounts.add(a4);

		earning.removeAmount(1);
		assertFalse("Element 1 should have been removed", amounts.get(1) == a2);
		assertTrue("Size should be 3", amounts.size() == 3);
	}

	@Test
	public void testGetAmount() {
		amounts.add(a1);
		amounts.add(a2);
		amounts.add(a3);
		amounts.add(a4);

		assertTrue("Should have get the value " + a2, earning.getAmount(1) == a2);
		assertTrue("Should have get the value " + a4, earning.getAmount(3) == a4);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetAmountNegativeIndex() {
		amounts.add(a1);
		earning.getAmount(-1);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetAmountOverMaxIndex() {
		amounts.add(a1);
		earning.getAmount(1);
	}

	@Test
	public void testToString() {
		amounts.add(3);
		earning.toString();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testEqualsObject() {
		amounts.add(a1);
		amounts.add(a2);
		amounts.add(a3);
		amounts.add(a4);

		Earning earn = new Earning();
		List<Integer> earnAmounts = (List<Integer>) Explorer.getField(earn, "amounts");
		earnAmounts.add(a1);
		earnAmounts.add(a2);
		earnAmounts.add(a3);
		earnAmounts.add(a4);

		assertEquals("Should be equal cause of " + "same fields", earning, earn);

		earn.removeAmount(0);
		assertNotEquals("Shouldn' be equal cause of different fields", earning, earn);

		assertEquals("Should be equal cause of same reference", earning, earning);

		assertNotEquals("Shouldn't be equal cause of null", earning, null);
		assertNotEquals("Shouldn't be equal cause of noot same class", earning, new Object());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testHashCode() {
		Earning earn = new Earning();
		List<Integer> earnAmounts = (List<Integer>) Explorer.getField(earn, "amounts");
		earnAmounts.add(a1);
		earnAmounts.add(a2);

		amounts.add(a1);
		amounts.add(a2);

		assertTrue("Should have the same hashcode", earning.hashCode() == earn.hashCode());

		amounts.add(a3);
		assertFalse("Shouldn't have the same hashcode", earning.hashCode() == earn.hashCode());
	}
}
