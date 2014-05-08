package instructions;

import java.util.List;

import types.Type;
import util.Pair;

public class InstOfExpr extends Expr {

	private Expr exp;
	private Type checkType;

	public InstOfExpr(Expr exp, Type checkType, Type type) {
		super(type);
		this.exp = exp;
		this.checkType = checkType;
	}

	public Expr getExp() {
		return exp;
	}

	public void setExp(Expr exp) {
		this.exp = exp;
	}

	@Override
	public String toString() {
		return exp +" instanceof "+checkType;
	}

	@Override
	public String shortRep() {
		return ExprConsts.InstOfExpr+"("+checkType.getPrefix()+")";
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
