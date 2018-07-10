package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest 
{
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
	@Before
	public void setUp() throws Exception 
	{
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() 
	{
		assertEquals((Integer) 10000, SEK100.getAmount());
	}

	@Test
	public void testGetCurrency() 
	{
		assertSame(EUR, EUR10.getCurrency());
		assertNotSame(EUR, SEK100.getCurrency());
	}

	@Test
	public void testToString() 
	{
		assertTrue(SEK100.toString().equals("100.0 SEK"));
		assertFalse(SEK100.toString().equals(EUR10.toString()));
	}

	@Test
	public void testUniversalValue()	// testGlobalValue --> testUniversalValue. The same for CurrencyTest. 
	{
		assertEquals((Integer)3000, SEK200.universalValue());
	}

	@Test
	public void testEquals()	// testEqualsMoney --> testEquals 
	{
		assertTrue(SEK200.equals(EUR20));
		assertFalse(SEK200.equals(SEK100));
	}

	@Test
	public void testAdd() 
	{
		assertTrue(SEK100.equals(SEK100.add(SEK0)));
		assertTrue(SEK200.equals(SEK100.add(EUR10)));
		// We have to convert otherMoney currency to this currency first. Currencies has to agree after adding.
		assertEquals(SEK200.getCurrency(), SEK100.add(EUR10).getCurrency());
	}

	@Test
	public void testSub()	// Opposite operations to addition
	{
		assertTrue(SEK100.equals(SEK100.sub(SEK0)));
		assertTrue(SEK100.equals(SEK200.sub(EUR10)));
		assertEquals(SEK100.getCurrency(), SEK200.sub(EUR10).getCurrency());
	}

	@Test
	public void testIsZero() 
	{
		assertFalse(EUR10.isZero());
		assertTrue(EUR0.isZero());
	}

	@Test
	public void testNegate() 
	{
		// From class definition: "if the amount is 10.0 SEK the negation returns -10.0 SEK"
		assertTrue(SEKn100.toString().equals(SEK100.negate().toString()));
		assertTrue(SEK100.toString().equals(SEKn100.negate().toString()));
	}

	@Test
	public void testCompareTo() 
	{
		assertTrue(SEK200.compareTo(EUR10) > 0);
		assertTrue(SEK100.compareTo(SEKn100) > 0);
		assertFalse(SEKn100.compareTo(SEK100) > 0);
		
		assertTrue(EUR10.compareTo(SEK200) < 0);
		assertTrue(SEKn100.compareTo(SEK100) < 0);
		assertFalse(SEK100.compareTo(SEKn100) < 0);
		
		assertTrue(EUR0.compareTo(SEK0) == 0);
		assertTrue(SEK200.compareTo(EUR20) == 0);
		assertTrue(SEK200.compareTo(SEK200) == 0);
		assertFalse(SEK200.compareTo(EUR10) == 0);
	}
}
