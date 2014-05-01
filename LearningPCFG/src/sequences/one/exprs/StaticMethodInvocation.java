package sequences.one.exprs;

import java.util.Arrays;
import java.util.List;

import definitions.Declaration;

import selection.types.Type;
import util.Pair;

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
	
	@Override
	protected void representations(List<Pair<String, String>> list) {
		for (Expr arg: args) {
			list.addAll(arg.longReps());
		}
	}	
}
