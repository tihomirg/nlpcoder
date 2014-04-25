package sequences.trees;

import definitions.Declaration;
import selection.types.Type;

public class InstanceFieldAccess extends Expr{
	private Declaration field;
	private Expr exp;
	
	public InstanceFieldAccess(Declaration field, Expr exp, Type type) {
		super(field.getRetType());
		this.field = field;
		this.exp = exp;
	}

	@Override
	public String toString() {
		return exp +"."+field;
	}
	
	
}
