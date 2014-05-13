package statistics.posttrees;

import java.util.List;

import statistics.Names;

import definitions.Declaration;

public class ConstructorInvocation extends Expr {

	private Declaration decl;
	private List<Expr> args;

	public ConstructorInvocation(Declaration decl) {
		this.decl = decl;
	}

	@Override
	public void addArgs(List<Expr> args) {
		this.args = args;
	}

	@Override
	protected String shortRep() {
		return Names.ConstructorInvocation+"("+decl.getLongName()+")";
	}

	@Override
	protected String argsRep() {
		return shortReps(args.toArray(new Expr[args.size()]));
	}

}
