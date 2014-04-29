package sequences.trees;

import org.eclipse.jdt.core.dom.PostfixExpression.Operator;

public class PostfixOperator extends Expr {

	private Expr expr;
	private Operator operator;
	
	public PostfixOperator(Operator operator, Expr expr) {
		super(expr.getType());
		this.expr = expr;
		this.operator = operator;
	}

	@Override
	public String toString() {
		return expr + " " + operator;
	}
}
