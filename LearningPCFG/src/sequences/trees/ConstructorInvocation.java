package sequences.trees;

import java.util.Arrays;

import selection.types.Type;

public class ConstructorInvocation extends Expr{
	private Expr[] args;
	
	public ConstructorInvocation(Type type, Expr[] args) {
		super(type);
		this.args = args;
	}

	@Override
	public String toString() {
		return "new " + type + "("+ Arrays.toString(args) + ")";
	}	
}
