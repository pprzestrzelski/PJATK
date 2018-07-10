package zad1;

public class UnknownCalculation implements CalculationChain {
	
	@SuppressWarnings("unused")
	private CalculationChain chain;
	
	@Override
	public void setNextChain(CalculationChain next) {
		chain = next;	
	}
	
	@Override
	public void calculate(RPN rpn) {
		System.out.println("Unknon operation!");
	}
	
}
