package sequences.trees;

import selection.types.Type;

public class Assignment extends Expr {

	private Expr lexp;
	private Expr rexp;
	private String op;
	
	public Assignment(String op, Expr lexp, Expr rexp, Type type) {
		super(type);
		this.op = op;
		this.lexp = lexp;
		this.rexp = rexp;
	}

	@Override
	public String toString() {
		return lexp +" "+op+" "+ rexp;
	}
}
