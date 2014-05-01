package sequences.one.exprs;

import java.util.List;

import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression.Operator;

import util.Pair;


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

	@Override
	public String shortRep() {
		return ExprConsts.PrefixOperator+"("+op+")";
	}

	@Override
	protected String representation() {
		return exp.shortRep();
	}

	@Override
	protected void representations(List<Pair<String, String>> list) {
		list.addAll(exp.longReps());
	}	
}
