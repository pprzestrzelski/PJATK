package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest 
{
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception 
	{
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}
	
	@Test
	public void testAddRemoveTimedPayment() 
	{
		String id = "services";
		testAccount.addTimedPayment(id, 1, 0, new Money(25000, SEK), SweBank, "Alice");
		assertTrue(testAccount.timedPaymentExists(id));
		testAccount.removeTimedPayment(id);
		assertTrue(!testAccount.timedPaymentExists(id));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException 
	{
		int counter = 5;
		Integer amount = 100000;
		Integer initBalance = testAccount.getBalance().getAmount();
		Integer testAccountExpectedBalance = initBalance - amount * counter;
		Integer aliceAccountExpectedBalance = SweBank.getBalance("Alice") + amount * counter;
		Money money = new Money(amount, SEK);
		
		testAccount.addTimedPayment("taxes", 0, 0, money, SweBank, "Alice");
		for (int i = 0; i < counter; ++i)
		{
			testAccount.tick();
		}
		
		// TODO: java.lang.AssertionError: expected:<9500000> but was:<9000000> - 2 x za duzo zabralo
		assertEquals(testAccountExpectedBalance, testAccount.getBalance().getAmount());
		assertEquals(aliceAccountExpectedBalance, SweBank.getBalance("Alice"));	// tutaj dodalo dwa razy za duzo.
	}
	
	@Test
	public void testTimedPaymentToWrongAccountName()
	{
		Integer expectedBalance = testAccount.getBalance().getAmount();
		testAccount.addTimedPayment("???", 0, 0, new Money(100, SEK), SweBank, "WRONG_NAME");
		testAccount.tick();
		assertEquals(expectedBalance, testAccount.getBalance().getAmount());
	}

	@Test
	public void testDepositeWithdraw() 	// testAddWithdraw() --> testDepositeWithdraw
	{
		Integer savingsAmount = 10000;
		Money savings = new Money(savingsAmount, SEK);
		Integer initBalance = testAccount.getBalance().getAmount();
		
		testAccount.deposit(savings);
		assertEquals((Integer)(initBalance + savingsAmount), testAccount.getBalance().getAmount());
		
		testAccount.withdraw(savings);
		assertEquals((Integer) initBalance, testAccount.getBalance().getAmount());
		
		testAccount.withdraw(new Money(initBalance, SEK));	// should be 0
		testAccount.deposit(new Money(100, SEK));
		assertEquals((Integer) 100, testAccount.getBalance().getAmount());
	}
	
	@Test
	public void testGetBalance() 
	{
		Money compareMe = new Money(10000000, SEK);
		assertTrue(compareMe.equals(testAccount.getBalance()));
		compareMe = compareMe.sub(new Money(100, SEK));
		assertFalse(compareMe.equals(testAccount.getBalance()));
	}
}
