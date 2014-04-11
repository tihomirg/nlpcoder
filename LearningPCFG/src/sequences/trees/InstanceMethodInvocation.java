package sequences.trees;

import java.util.Arrays;

import selection.types.Type;

public class InstanceMethodInvocation extends Expr{

	private String name;
	private Expr exp;
	private Expr[] args;
	
	public InstanceMethodInvocation(String name, Expr exp, Expr[] args, Type type) {
		super(type);
		this.name = name;
		this.exp = exp;
		this.args = args;
	}

	@Override
	public String toString() {
		return exp+"."+name+ "("+Arrays.toString(args) + ")";
	}
}
