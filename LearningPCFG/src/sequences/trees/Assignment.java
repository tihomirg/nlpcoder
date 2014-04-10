package sequences.trees;

import selection.types.Type;

public class Assignment extends Expression {

	private Expression lexp;
	private Expression rexp;
	
	public Assignment(Expression lexp, Expression rexp, Type type) {
		super(type);
		this.lexp = lexp;
		this.rexp = rexp;
	}
	
	
}
