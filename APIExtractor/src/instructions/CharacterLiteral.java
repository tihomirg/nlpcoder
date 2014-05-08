package instructions;

import java.util.List;

import selection.types.PrimitiveType;
import util.Pair;

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
	
	@Override
	protected void representations(List<Pair<String, String>> list) {
	}	
}
