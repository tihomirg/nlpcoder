package sequences.one.exprs;

import selection.types.PrimitiveType;

public class CharacterLiteral extends Expr {

	private char value;

	public CharacterLiteral(char value, PrimitiveType type) {
		super(type);
		this.value = value;
	}

	@Override
	public String toString() {
		return Character.toString(value);
	}

	@Override
	public String shortRep() {
		return ExprConsts.CharacterLiteral;
	}

	@Override
	protected String representation() {
		return Character.toString(value);
	}
}
