package sequences.one.exprs;

import org.eclipse.jdt.core.dom.PostfixExpression.Operator;

public class PostfixOperator extends Expr {

	private Expr expr;
	private Operator op;
	
	public PostfixOperator(Operator operator, Expr expr) {
		super(expr.getType());
		this.expr = expr;
		this.op = operator;
	}

	@Override
	public String toString() {
		return expr + " " + op;
	}

	@Override
	public String shortRep() {
		return ExprConsts.PostfixOperator+"("+op+")";		
	}

	@Override
	protected String representation() {
		return expr.shortRep();
	}
}
