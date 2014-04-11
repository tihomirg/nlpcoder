package sequences.trees;

import selection.types.Type;

public class InfixOperator extends Expr{
	private String op;
	private Expr lexp;
	private Expr rexp;
	
	public InfixOperator(String op, Expr lexp, Expr rexp, Type type) {
		super(type);
		this.op = op;
		this.lexp = lexp;
		this.rexp = rexp;
	}

	@Override
	public String toString() {
		return lexp + " " + op + " " + rexp;
	}
	
	
}
