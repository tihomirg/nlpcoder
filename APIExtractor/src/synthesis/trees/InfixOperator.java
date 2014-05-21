package synthesis.trees;

import synthesis.Representation;
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

	@Override
	public String toString(Representation rep) {
		return rep.toString(lexpId) +" "+op+" "+rep.toString(rexpId);
	}
}
