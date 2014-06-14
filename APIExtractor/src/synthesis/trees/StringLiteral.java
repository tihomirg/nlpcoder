package synthesis.trees;

import synthesis.Representation;
import types.Type;

public class StringLiteral extends Expr {

	private Type type;

	public StringLiteral(Type type) {
		this.type = type;
	}

	@Override
	public String toString(Representation rep) {
		return "\"?\"";
	}

}
