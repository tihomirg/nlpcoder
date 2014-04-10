package sequences.trees;

import selection.types.Type;

public class Literal extends Expression{

	private String literal;

	public Literal(String literal, Type type) {
		super(type);
		this.literal = literal;
	}
	
}
