package synthesis.trees;

import types.Type;

public class CharacterLiteral extends Expr {

	private Type type;

	public CharacterLiteral(Type type) {
		this.type = type;
	}

}
