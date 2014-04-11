package sequences.trees;

import selection.types.Type;

public class Literal extends Expr{

	private String literal;

	public Literal(String literal, Type type) {
		super(type);
		this.literal = literal;
	}

	@Override
	public String toString() {
		return literal;
	}
}
