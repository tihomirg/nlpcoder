package synthesis.trees;

import synthesis.Representation;
import types.Type;

public class CharacterLiteral extends Expr {

	private Type type;

	public CharacterLiteral(Type type) {
		this.type = type;
	}

	@Override
	public String toString(Representation rep) {
		return "c?";
	}

}
