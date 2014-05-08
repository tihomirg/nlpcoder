package instructions;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.Assignment.Operator;

import util.Pair;

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

	@Override
	protected void representations(List<Pair<String, String>> list) {
		list.addAll(lexp.longReps());
		list.addAll(rexp.longReps());
	}
	
}
