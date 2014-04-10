package sequences.trees;

import selection.types.Type;

public class InstanceMethodInvocation extends Expression{

	private String name;
	private Expression exp;
	private Expression[] args;
	
	public InstanceMethodInvocation(String name, Expression exp, Expression[] args, Type type) {
		super(type);
		this.name = name;
		this.exp = exp;
		this.args = args;
	}
	
	
	
}
