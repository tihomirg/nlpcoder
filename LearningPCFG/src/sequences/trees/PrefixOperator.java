package sequences.trees;

import selection.types.Type;

public class PrefixOperator extends Expr {
	private String op;
	private Expr exp;
	
	public PrefixOperator(String op, Expr exp, Type type) {
		super(type);
		this.op = op;
		this.exp = exp;
	}

	@Override
	public String toString() {
		return op +" "+exp;
	}
}
