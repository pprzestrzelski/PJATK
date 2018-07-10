package zad1;

import java.util.LinkedList;

// Reverse Polish Notation
public class RPN {

	private LinkedList<Double> stackNumbers = new LinkedList<>();
	private LinkedList<String> stackOperators = new LinkedList<>();
	
	private boolean isValid = false;
	
	public RPN(String expression) 
	{ 
		handleInput(expression);
		if (stackSize() > 1)
		{
			isValid = true;
		}
	}
	
	public boolean isValid()
	{
		return isValid;
	}
	
	public int stackSize()
	{
		return stackNumbers.size();
	}
	
	public double getSolution() throws Exception
	{
		if (stackSize() > 1 || stackSize() == 0)
		{
			throw new Exception("Something went wrong during calculations :(");
		}
		
		return stackNumbers.pop();
	}
	
	public double getNumber()
	{
		return stackNumbers.pop();
	}
	
	public void pushNumber(Double n)
	{
		stackNumbers.addLast(n);
	}
	
	public String getOperation()
	{
		return stackOperators.pop();
	}
	
	public String peekOperation()
	{
		return stackOperators.peek();
	}
	
	private void handleInput(String expr)
	{
		String[] splittedExpr = expr.split("\\s+");
		for (String e : splittedExpr)
		{
			// TODO: Ugly solution, try to change it
			try
			{
				stackNumbers.add(Double.parseDouble(e));
			}
			catch (NumberFormatException nfe)
			{
				stackOperators.add(e);
			}
		}
	}
	
}
