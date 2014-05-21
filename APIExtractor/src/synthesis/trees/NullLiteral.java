package synthesis.trees;

import synthesis.Representation;
import types.Type;

public class NullLiteral extends Expr {

	private Type type;

	public NullLiteral(Type type) {
		this.type = type;
	}

	@Override
	public String toString(Representation rep) {
		return "null";
	}

}
