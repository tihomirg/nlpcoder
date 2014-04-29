package sequences.one.exprs;

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
		return exp +"."+field.getName();
	}

	@Override
	public String shortRep() {
		return ExprConsts.InstanceFieldAccess+"("+field.getName()+")";
	}

	@Override
	protected String representation() {
		return exp.shortRep();
	}
	
}
