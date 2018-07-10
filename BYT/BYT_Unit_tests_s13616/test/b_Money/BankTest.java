package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest 
{
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception 
	{
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() 
	{
		assertEquals("SweBank", SweBank.getName());
		assertNotEquals("DanskeBank", SweBank.getName());
	}

	@Test
	public void testGetCurrency() 
	{
		assertEquals(SEK, Nordea.getCurrency());
		assertNotEquals(SEK, DanskeBank.getCurrency());
	}

	@Test (expected=AccountExistsException.class)
	public void testOpenAccountWhichExists() throws AccountExistsException
	{
		SweBank.openAccount("Ulrika");
	}
	
	@Test
	public void testOpenAccount() throws AccountExistsException/*, AccountDoesNotExistException*/	// Second Exception is ever thrown in this method?
	{
		SweBank.openAccount("Kinga");
		assertTrue(SweBank.accountExists("Kinga"));
	}

	@Test (expected=AccountDoesNotExistException.class)
	public void testDepositToNonExistingAccount() throws AccountDoesNotExistException
	{
		SweBank.deposit("Bob2", new Money(100, SEK));
	}
	
	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		Integer amountToDeposit = 500000;
		SweBank.deposit("Ulrika", new Money(amountToDeposit, SEK));
		assertEquals(amountToDeposit, SweBank.getBalance("Ulrika"));
	}

	@Test (expected=AccountDoesNotExistException.class)
	public void testWithdrawFromNotExistingAccount() throws AccountDoesNotExistException
	{
		SweBank.withdraw("Ula", new Money(0, SEK));
	}
	
	@Test
	public void testWithdraw() throws AccountDoesNotExistException 
	{
		Integer amountToWithdraw = 500000;
		SweBank.withdraw("Ulrika", new Money(amountToWithdraw, SEK));
		assertEquals((Integer) (amountToWithdraw * -1), SweBank.getBalance("Ulrika"));
	}
	
	@Test (expected=AccountDoesNotExistException.class)
	public void testGetBalanceFromNotExistingAccount() throws AccountDoesNotExistException
	{
		SweBank.getBalance("Bob2");
	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		assertEquals((Integer) 0, SweBank.getBalance("Bob"));
		// Alternatywnie, do metody testGetBalanceFromNotExistingAccount(),
		// mozna testowac rzucanie wyjatku w ten sposob:
		/*
		try
		{
			SweBank.getBalance("Bob2");
			fail("You should not see this message. Something went wrong!");
		}
		catch (AccountDoesNotExistException e)
		{
		}
		*/
	}
	
	@Test (expected=AccountDoesNotExistException.class)
	public void testTransferFromWrongAccount() throws AccountDoesNotExistException 
	{
		SweBank.transfer("Bob2", "Ulrika", new Money(0, SEK));
	}
	
	@Test (expected=AccountDoesNotExistException.class)
	public void testTransferToWrongAccount() throws AccountDoesNotExistException 
	{
		SweBank.transfer("Bob", "Ula", new Money(0, SEK));
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException 
	{
		Integer amount = 100;
		Money money = new Money(amount, SEK);
		SweBank.transfer("Bob", "Ulrika", money);
		
		// expected:<-100> but was:<0>
		assertEquals((Integer)(-amount), SweBank.getBalance("Bob"));
		assertEquals(amount, SweBank.getBalance("Ulrika"));
		
		DanskeBank.transfer("Gertrud", SweBank, "Bob", money);
		assertEquals((Integer) (-75), DanskeBank.getBalance("Gertrud"));
		assertEquals((Integer) 0, SweBank.getBalance("Bob"));
	}
	
	@Test (expected=AccountDoesNotExistException.class)
	public void testTimedPaymentAddToNotExistingAccount() throws AccountDoesNotExistException
	{
		// Nie jest rzucany zaden wyjatek. A powinien?
		SweBank.addTimedPayment("Ula", "services", 0, 0, new Money(0, SEK), SweBank, "Bob");
	}
	
	@Test (expected=AccountDoesNotExistException.class)
	public void testTimedPaymentRemoveFromNonExistingAccount() throws AccountDoesNotExistException
	{
		// j.w.
		SweBank.removeTimedPayment("Ula", "services");
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException 
	{
		Integer amount = 100;
		Money money = new Money(amount, SEK);
		SweBank.addTimedPayment("Bob", "service", 0, 0, money, SweBank, "Ulrika");
		
		SweBank.tick(); SweBank.tick();
		
		assertEquals((Integer)(amount * -2), SweBank.getBalance("Bob"));
		assertEquals((Integer)(amount * 2), SweBank.getBalance("Ulrika"));
		SweBank.removeTimedPayment("Bob", "service");
	}
}
