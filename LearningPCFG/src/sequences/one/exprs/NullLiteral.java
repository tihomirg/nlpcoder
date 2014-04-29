package sequences.one.exprs;

import selection.types.Type;

public class NullLiteral extends Expr {

	public NullLiteral(Type type) {
		super(type);
	}

	@Override
	public String toString() {
		return ExprConsts.NullLiteral;
	}

	@Override
	public String shortRep() {
		return ExprConsts.NullLiteral;
	}

	@Override
	protected String representation() {
		return ExprConsts.NullLiteral;
	}
}
