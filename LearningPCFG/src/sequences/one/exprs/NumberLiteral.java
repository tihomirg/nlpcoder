package sequences.one.exprs;

import selection.types.Type;

public class NumberLiteral extends Expr {

	private String number;

	public NumberLiteral(String number, Type type) {
		super(type);
		this.number = number;
	}

	@Override
	public String toString() {
		return number;
	}

	@Override
	public String shortRep() {
		return ExprConsts.NumberLiteral;
	}

	@Override
	protected String representation() {
		return number;
	}	
}
