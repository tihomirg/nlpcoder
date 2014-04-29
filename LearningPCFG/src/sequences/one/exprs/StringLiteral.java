package sequences.one.exprs;

import selection.types.Type;

public class StringLiteral extends Expr {

	private String value;

	public StringLiteral(String value, Type type) {
		super(type);
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	public String shortRep() {
		return ExprConsts.StringLiteral;
	}

	@Override
	protected String representation() {
		return "("+value+")";
	}
}
