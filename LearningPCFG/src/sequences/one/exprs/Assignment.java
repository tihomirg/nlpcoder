package sequences.one.exprs;

import org.eclipse.jdt.core.dom.Assignment.Operator;

public class Assignment extends Expr {

	private Expr lexp;
	private Expr rexp;
	private Operator op;

	public Assignment(Operator operator, Expr leftExp, Expr rightExp) {
		super(leftExp.getType());

		this.op = operator;
		this.lexp = leftExp;
		this.rexp = rightExp;
		
	}

	@Override
	public String toString() {
		return lexp +" "+op+" "+ rexp;
	}

	@Override
	public String shortRep() {
		return ExprConsts.Assignment+"("+op+")";
	}

	@Override
	protected String representation() {
		return shortReps(lexp, rexp);
	}
	
}
