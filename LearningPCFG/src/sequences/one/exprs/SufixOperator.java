package sequences.one.exprs;

import selection.types.Type;

public class SufixOperator extends Expr{

	private String op;
	private Expr exp;
	
	public SufixOperator(String op, Expr exp, Type type) {
		super(type);
		this.op = op;
		this.exp = exp;
	}

	@Override
	public String toString() {
		return exp +" "+ op;
	}	
}
