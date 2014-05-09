package statistics.posttrees;

import java.util.List;

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
		this.exp = args.remove(0);
		this.args = args;
	}

}
