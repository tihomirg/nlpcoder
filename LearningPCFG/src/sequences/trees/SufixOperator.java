package sequences.trees;

import selection.types.Type;

public class SufixOperator extends Expression{

	private String op;
	private Expression exp;
	
	public SufixOperator(String op, Expression exp, Type type) {
		super(type);
		this.op = op;
		this.exp = exp;
	}
	
	
}
