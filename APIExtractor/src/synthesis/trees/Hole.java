package synthesis.trees;

import types.Type;

public class Hole extends Expr {

	private Type type;

	public Hole(Type type) {
		this.type = type;
	}

}
