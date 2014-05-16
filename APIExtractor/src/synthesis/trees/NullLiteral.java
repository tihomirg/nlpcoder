package synthesis.trees;

import types.Type;

public class NullLiteral extends Expr {

	private Type type;

	public NullLiteral(Type type) {
		this.type = type;
	}

}
