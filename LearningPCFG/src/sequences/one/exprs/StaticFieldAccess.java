package sequences.one.exprs;

import java.util.List;

import definitions.Declaration;
import selection.types.Type;
import util.Pair;

public class StaticFieldAccess extends Expr {

	private Declaration decl;
	
	public StaticFieldAccess(Declaration decl, Type type) {
		super(type);
		this.decl = decl;
	}

	@Override
	public String toString() {
		return decl.getClazz()+"."+decl.getName();
	}

	@Override
	public String shortRep() {
		return ExprConsts.StaticFieldAccess+"("+decl.getName()+")";
	}

	@Override
	protected String representation() {
		return "";
	}
	
	@Override
	protected void representations(List<Pair<String, String>> list) {
	}	
}
