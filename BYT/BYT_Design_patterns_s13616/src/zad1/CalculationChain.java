package zad1;

public interface CalculationChain {
	
	void setNextChain(CalculationChain next);
	
	void calculate(RPN rpn);

}
