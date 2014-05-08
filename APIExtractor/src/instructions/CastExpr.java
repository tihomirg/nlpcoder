package instructions;

import java.util.List;

import types.Type;
import util.Pair;

public class CastExpr extends Expr {

	private Expr exp;

	public CastExpr(Type type, Expr exp) {
		super(type);
		this.exp = exp;
	}

	@Override
	public String toString() {
		return "("+type+")"+ exp;
	}

	@Override
	public String shortRep() {
		return ExprConsts.CastExpr+"("+type.getPrefix()+")";
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
