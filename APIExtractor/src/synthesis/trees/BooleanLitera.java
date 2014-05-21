package synthesis.trees;

import synthesis.Representation;
import types.Type;

public class BooleanLitera extends Expr {

	private Type type;

	public BooleanLitera(Type type) {
		this.type = type;
	}

	@Override
	public String toString(Representation rep) {
		return "false";
	}
}
