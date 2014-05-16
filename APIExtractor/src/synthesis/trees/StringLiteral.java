package synthesis.trees;

import types.Type;

public class StringLiteral extends Expr {

	private Type type;

	public StringLiteral(Type type) {
		this.type = type;
	}

}
