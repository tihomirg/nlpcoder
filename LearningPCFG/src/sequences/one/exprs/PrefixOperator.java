package sequences.one.exprs;

import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression.Operator;


public class PrefixOperator extends Expr {
	private Operator op;
	private Expr exp;
	
	public PrefixOperator(PrefixExpression.Operator operator, Expr expr) {
		super(expr.getType());
		this.op = operator;
		this.exp = expr;
	}

	@Override
	public String toString() {
		return op +" "+exp;
	}
}
