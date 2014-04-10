package sequences.trees;

import selection.types.Type;

public class InfixOperator extends Expression{
	private String op;
	private Expression lexp;
	private Expression rexp;
	
	public InfixOperator(String op, Expression lexp, Expression rexp, Type type) {
		super(type);
		this.op = op;
		this.lexp = lexp;
		this.rexp = rexp;
	}
	
	
}
