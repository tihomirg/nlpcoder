package synthesis.trees;

import synthesis.Representation;
import types.Type;

public class PostfixOperator extends Expr {

	private String op;
	private Type type;
	private int argId;

	public PostfixOperator(String op, Type type, int argId) {
		this.op = op;
		this.type = type;
		this.argId = argId;
	}

	@Override
	public String toString(Representation rep) {
		return rep.toString(argId)+op;
	}

}
