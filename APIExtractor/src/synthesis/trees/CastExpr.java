package synthesis.trees;

import types.Type;

public class CastExpr extends Expr {

	private Type argType;
	private Type type;
	private int argId;

	public CastExpr(Type argType, Type type, int argId) {
		this.argType = argType;
		this.type = type;
		this.argId = argId;
	}

}
