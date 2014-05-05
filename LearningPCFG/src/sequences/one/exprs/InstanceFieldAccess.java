package sequences.one.exprs;

import java.util.List;

import definitions.Declaration;
import selection.types.Type;
import util.Pair;

public class InstanceFieldAccess extends Expr{
	private Declaration field;
	private Expr exp;
	
	public InstanceFieldAccess(Declaration field, Expr exp) {
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

	@Override
	protected void representations(List<Pair<String, String>> list) {
		list.addAll(exp.longReps());
	}
}
