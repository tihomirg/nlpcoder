package sequences.one.exprs;

import selection.types.Type;

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
	
}
