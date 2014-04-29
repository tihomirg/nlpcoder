package sequences.one.exprs;

import java.util.Arrays;

import definitions.Declaration;

import selection.types.Type;

public class StaticMethodInvocation extends Expr{
	
	private Declaration method;
	private Expr[] args;
	
	public StaticMethodInvocation(Declaration method, Expr[] args, Type type) {
		super(type);
		this.method = method;
		this.args = args;
	}

	@Override
	public String toString() {
		return method.getClazz()+"."+method.getName()+ "("+Arrays.toString(args) + ")";
	}
	
	@Override
	public String shortRep() {
		return ExprConsts.StaticMethodInvocation+"("+method.getName()+")";
	}

	@Override
	protected String representation() {
		return shortReps(args);
	}	
}
