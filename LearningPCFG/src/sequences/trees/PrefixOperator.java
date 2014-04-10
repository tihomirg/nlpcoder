package sequences.trees;

import selection.types.Type;

public class PrefixOperator extends Expression {
	private String op;
	private Expression exp;
	
	public PrefixOperator(String op, Expression exp, Type type) {
		super(type);
		this.op = op;
		this.exp = exp;
	}
	
	
}
