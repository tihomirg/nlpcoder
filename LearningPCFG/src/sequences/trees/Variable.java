package sequences.trees;

import selection.types.Type;

public class Variable extends Expression {
	private String name;

	public Variable(String name, Type type) {
		super(type);
		this.name = name;
	}	
}
