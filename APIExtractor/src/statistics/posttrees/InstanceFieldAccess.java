package statistics.posttrees;

import java.util.List;

import statistics.Names;

import definitions.Declaration;

public class InstanceFieldAccess extends Expr {

	private Declaration decl;
	private Expr exp;

	public InstanceFieldAccess(Declaration decl) {
		this.decl = decl;
	}

	@Override
	public void addArgs(List<Expr> args) {
		this.exp = args.remove(0);
	}

	@Override
	protected String shortRep() {
		return Names.InstanceFieldAccess+"("+decl.getLongName()+")";
	}

	@Override
	protected String argsRep() {
		return shortReps(exp);
	}

}
