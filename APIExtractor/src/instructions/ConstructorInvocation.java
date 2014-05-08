package instructions;

import java.util.Arrays;
import java.util.List;

import definitions.Declaration;

import selection.types.Type;
import util.Pair;

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

	@Override
	public String shortRep() {
		return ExprConsts.ConstructorInvocation+"("+cons.getLongName()+")";
	}
	
	@Override
	protected String representation() {
		return shortReps(args);
	}
	
	@Override
	protected void representations(List<Pair<String, String>> list) {
		for (Expr arg : args) {
			list.addAll(arg.longReps());	
		}
	}	
}
