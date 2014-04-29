package sequences.one.exprs;

import java.util.Arrays;

import definitions.Declaration;

import selection.types.Type;

public class InstanceMethodInvocation extends Expr{

	private Declaration method;
	private Expr exp;
	private Expr[] args;
	
	public InstanceMethodInvocation(Declaration method, Expr exp, Expr[] args) {
		super(method.getRetType());
		this.method = method;
		this.exp = exp;
		this.args = args;
	}

	@Override
	public String toString() {
		return exp+"."+method+ "("+Arrays.toString(args) + ")";
	}
}
