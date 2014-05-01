package sequences.one.exprs;

import java.util.Arrays;
import java.util.List;

import definitions.Declaration;

import selection.types.Type;
import util.Pair;

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
		return exp+"."+method.getName()+ "("+Arrays.toString(args) + ")";
	}

	@Override
	public String shortRep() {
		return ExprConsts.InstanceMethodInvocation+"("+method.getName()+")";
	}

	@Override
	protected String representation() {
		Expr[] exprs = new Expr[args.length+1];
		exprs[0] = exp;
		System.arraycopy(args, 0, exprs, 1, args.length);
		return shortReps(exprs);
	}
	
	@Override
	protected void representations(List<Pair<String, String>> list) {
		list.addAll(exp.longReps());
		
		for (Expr arg : args) {
			list.addAll(arg.longReps());
		}
	}	
}
