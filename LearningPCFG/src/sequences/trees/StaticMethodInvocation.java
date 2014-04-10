package sequences.trees;

import selection.types.Type;

public class StaticMethodInvocation extends Expression{
	
	private String name;
	private String className;
	private Expression[] args;
	
	public StaticMethodInvocation(String name, String className, Expression[] args, Type type) {
		super(type);
		this.name = name;
		this.className = className;
		this.args = args;
	}
	
	
}
