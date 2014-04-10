package sequences.trees;

import selection.types.Type;

public class InstanceFieldAccess extends Expression{
	private String name;
	private Expression exp;
	
	public InstanceFieldAccess(String name, Expression exp, Type type) {
		super(type);
		this.name = name;
		this.exp = exp;
	}

}
