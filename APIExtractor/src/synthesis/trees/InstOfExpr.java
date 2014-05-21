package synthesis.trees;

import synthesis.Representation;
import types.Type;

public class InstOfExpr extends Expr {

	private Type type;
	private Type argType;
	private int argId;

	public InstOfExpr(Type type, Type argType, int argId) {
		this.type = type;
		this.argType = argType;
		this.argId = argId;
	}

	@Override
	public String toString(Representation rep) {
		return rep.toString(argId) +" instanceof "+type;
	}

}
