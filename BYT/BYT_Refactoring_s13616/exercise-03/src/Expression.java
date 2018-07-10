//https://dzone.com/articles/design-patterns-visitor

// TODO: added Visitor pattern to ease code's extensibility
public abstract class Expression implements Visitable 
{
	private Expression left;
	private Expression right;
	private int constant;

	public Expression(int constant) 
	{
		this.constant = constant;
	}

	public Expression(Expression left, Expression right) 
	{
		this.left = left;
		this.right = right;
	}

	public Expression getLeft() 
	{
		return left;
	}

	public Expression getRight() 
	{
		return right;
	}

	public int getConstant() 
	{
		return constant;
	}
}
