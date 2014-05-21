package synthesis.trees;

import synthesis.Representation;
import types.Type;

public class Hole extends Expr {

	private Type type;

	public Hole(Type type) {
		this.type = type;
	}

	@Override
	public String toString(Representation rep) {
		return "Hole";
	}
}
