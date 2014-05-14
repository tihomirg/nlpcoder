package statistics.posttrees;

import java.util.List;

import statistics.Names;
import definitions.Declaration;

public class InstanceMethodInvocation extends Expr {

	private Declaration decl;
	private Expr exp;
	private List<Expr> args;	
	
	public InstanceMethodInvocation(Declaration decl) {
		this.decl = decl;
	}

	@Override
	public void addArgs(List<Expr> args) {
		this.exp = decl.isStatic() ? null : args.remove(0);
		this.args = args;
	}

	@Override
	protected String shortRep() {
		return Names.InstanceMethodInvocation+"("+decl.getLongName()+")";
	}

	@Override
	protected String argsRep() {
		Expr[] exprs = null;
		if (decl.isStatic()){
			exprs = args.toArray(new Expr[args.size()]);
		} else {
			exprs = new Expr[args.size()+1];
			exprs[0] = exp;
			System.arraycopy(args.toArray(new Expr[args.size()]), 0, exprs, 1, args.size());			
		}
		return shortReps(exprs);
	}
	
	@Override
	public String getPrefix() {
		return Names.InstanceMethodInvocation;
	}
	
	public Declaration getDecl() {
		return decl;
	}

}
