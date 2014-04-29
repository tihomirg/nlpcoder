package sequences.one.exprs;

import selection.types.Type;

public class BooleanLiteral extends Expr {

	private boolean value;

	public BooleanLiteral(boolean value, Type type) {
		super(type);
		this.value = value;
	}

	@Override
	public String toString() {
		return Boolean.toString(value);
	}

	@Override
	public String shortRep() {
		return ExprConsts.BooleanLiteral;
	}

	@Override
	protected String representation() {
		return Boolean.toString(this.value);
	}
	
}
