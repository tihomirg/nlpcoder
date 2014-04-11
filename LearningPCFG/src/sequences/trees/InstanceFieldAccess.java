package sequences.trees;

import selection.types.Type;

public class InstanceFieldAccess extends Expr{
	private String name;
	private Expr exp;
	
	public InstanceFieldAccess(String name, Expr exp, Type type) {
		super(type);
		this.name = name;
		this.exp = exp;
	}

	@Override
	public String toString() {
		return exp +"."+name;
	}
	
	
}
