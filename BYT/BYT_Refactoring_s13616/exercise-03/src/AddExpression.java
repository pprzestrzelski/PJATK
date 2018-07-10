public class AddExpression extends Expression {

	public AddExpression(Expression left, Expression right) 
	{
		super(left, right);
	}

	@Override
	public int accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
