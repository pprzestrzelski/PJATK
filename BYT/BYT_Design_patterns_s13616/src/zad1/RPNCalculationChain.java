package zad1;

import java.util.Scanner;

// Chain of responsibility
public class RPNCalculationChain {

	private CalculationChain node;
	
	public RPNCalculationChain()
	{
		node = new AddCalculation();
		CalculationChain node2 = new DiffCalculation();
		CalculationChain node3 = new MultCalculation();
		CalculationChain node4 = new DivCalculation();
		CalculationChain node5 = new UnknownCalculation();
		
		// set nodes in a chain
		node.setNextChain(node2);
		node2.setNextChain(node3);
		node3.setNextChain(node4);
		node4.setNextChain(node5);
	}
	
	public static void main(String[] args)
	{
		RPNCalculationChain rpnCalculation = new RPNCalculationChain();
		Scanner input = new Scanner(System.in);
		String expression = input.nextLine();
		if (expression.length() == 0)
		{
			System.out.println("No expression given. Try again.");
		}
		else
		{
			RPN rpn = new RPN(expression);
			if (rpn.isValid())
			{
				while (rpn.stackSize() > 1)
				{
					rpnCalculation.node.calculate(rpn);
				}
			}
			
			try
			{
				System.out.println("Result: " + rpn.getSolution());
			}
			catch (Exception exc)
			{
				System.out.println(exc.getMessage());
			}
			
		}
		input.close();
	}
	
}
