package synthesis.trees;

import synthesis.Representation;
import types.Type;

public class NumberLiteral extends Expr {

	private Type type;

	public NumberLiteral(Type type) {
		this.type = type;
	}

	@Override
	public String toString(Representation rep) {
		return "0";
	}

}
