package zad1;

public class DiffCalculation implements CalculationChain {

	private CalculationChain chain;
	
	@Override
	public void setNextChain(CalculationChain next) {
		chain = next;	
	}
	
	@Override
	public void calculate(RPN rpn) {
		if (rpn.peekOperation().equals("-"))
		{
			rpn.getOperation();
			double n2 = rpn.getNumber();
			double n1 = rpn.getNumber();
			rpn.pushNumber(n1 - n2);
		}
		else
		{
			chain.calculate(rpn);
		}
	}

}
