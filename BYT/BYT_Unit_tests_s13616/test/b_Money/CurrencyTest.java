package b_Money;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CurrencyTest 
{
	Currency SEK, DKK, NOK, EUR;
	double deltaRate = 0.01;
	
	@Before
	public void setUp() throws Exception 
	{
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() 
	{
		assertEquals("SEK", SEK.getName());
		assertNotEquals("SEK", DKK.getName());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetNameZeroLengthIllegalArgumentException()
	{
		EUR.setName("");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetNameWrongLengthIllegalArgumentExceptionRaised()
	{
		EUR.setName("EURO");
		EUR.setName("EU");
	}
	
	@Test
	public void testSetName()
	{
		EUR.setName("eur");
		assertTrue(EUR.getName().equals("EUR"));
		SEK.setName("SeK");
		assertTrue(SEK.getName().equals("SEK"));
	}
	
	@Test
	public void testGetRate() 
	{
		assertEquals((Double)0.20, DKK.getRate(), deltaRate);
		assertNotEquals((Double)0.15, EUR.getRate(), deltaRate);
	}
	
	/*
	 * TODO: DISABLE possibility to set rate <= 0.0 (throw Exception)
	 * */
	@Test
	public void testSetRate() 
	{
		double rateToSet = 0.05;
		assertNotEquals(rateToSet, EUR.getRate(), deltaRate);
		EUR.setRate(rateToSet);
		assertEquals(rateToSet, EUR.getRate(), deltaRate);
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetRateIllegalArguentExceptionRaised()
	{
		EUR.setRate(-1.0);
		EUR.setRate(-0.0);	// specific case of different bit patterns for 0.0 and -0.0
	}
	
	@Test
	public void testUniversalValue() 
	{
		assertEquals((Integer)20, DKK.universalValue(100));
	}
	
	@Test
	public void testValueInThisCurrency() 
	{
		assertEquals((Integer)1333, SEK.valueInThisCurrency(1000, DKK));
	}

}
