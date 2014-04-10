package sequences.trees;

import selection.types.Type;

public class ConstructorCall extends Expression{
	private String name;
	private Expression[] args;
	
	public ConstructorCall(String name, Expression[] args, Type type) {
		super(type);
		this.name = name;
		this.args = args;
	}
	
	
}
