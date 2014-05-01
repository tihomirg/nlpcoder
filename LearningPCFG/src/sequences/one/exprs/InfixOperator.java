package sequences.one.exprs;

import java.util.List;

import org.eclipse.jdt.core.dom.InfixExpression.Operator;

import util.Pair;

public class InfixOperator extends Expr{
	private Operator op;
	private Expr lexp;
	private Expr rexp;

	public InfixOperator(Operator operator, Expr leftExpr, Expr rightExpr) {
		super(leftExpr.getType());
		this.op = operator;
		this.lexp = leftExpr;
		this.rexp = rightExpr;
	}

	@Override
	public String toString() {
		return lexp + " " + op + " " + rexp;
	}

	@Override
	public String shortRep() {
		return ExprConsts.InfixOperator+"("+op+")";
	}

	@Override
	protected String representation() {
		return shortReps(lexp, rexp);
	}

	@Override
	protected void representations(List<Pair<String, String>> list) {
		list.addAll(lexp.longReps());
		list.addAll(rexp.longReps());
	}
}
