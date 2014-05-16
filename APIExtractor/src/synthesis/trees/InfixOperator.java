package synthesis.trees;

import types.Type;

public class InfixOperator extends Expr {

	private String op;
	private Type type;
	private int lexpId;
	private int rexpId;

	public InfixOperator(String op, Type type, int lexpId, int rexpId) {
		this.op = op;
		this.type = type;
		this.lexpId = lexpId;
		this.rexpId = rexpId;
	}

}
