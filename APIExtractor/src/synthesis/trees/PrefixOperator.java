package synthesis.trees;

import types.Type;

public class PrefixOperator extends Expr {

	private int argId;
	private Type type;
	private String op;

	public PrefixOperator(String op, Type type, int argId) {
		this.op = op;
		this.type = type;
		this.argId = argId;
	}

}
