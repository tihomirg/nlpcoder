package synthesis.trees;

import types.Type;

public class NumberLiteral extends Expr {

	private Type type;

	public NumberLiteral(Type type) {
		this.type = type;
	}

}
