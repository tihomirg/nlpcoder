package sequences.trees;

import java.util.Arrays;

import selection.types.Type;

public class StaticMethodInvocation extends Expr{
	
	private String name;
	private String className;
	private Expr[] args;
	
	public StaticMethodInvocation(String name, String className, Expr[] args, Type type) {
		super(type);
		this.name = name;
		this.className = className;
		this.args = args;
	}

	@Override
	public String toString() {
		return className+"."+name+ "("+Arrays.toString(args) + ")";
	}
}
