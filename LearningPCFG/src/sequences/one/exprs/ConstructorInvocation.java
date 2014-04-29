package sequences.one.exprs;

import java.util.Arrays;

import definitions.Declaration;

import selection.types.Type;

public class ConstructorInvocation extends Expr{
	private Expr[] args;
	private Declaration cons;
	
	public ConstructorInvocation(Declaration cons, Expr[] args) {
		super(cons.getRetType());
		this.cons = cons;
		this.args = args;
	}

	@Override
	public String toString() {
		return "new " + type + "("+ Arrays.toString(args) + ")";
	}

	//TODO: later we will change this with 
	@Override
	public String shortRep() {
		return ExprConsts.ConstructorInvocation+"("+cons.getName()+")";
	}
	
	@Override
	protected String representation() {
		return shortReps(args);
	}	
}
