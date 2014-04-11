package sequences.trees;

import selection.types.Type;

public abstract class Expr {
	protected Type type;
	
	public Expr(Type type) {
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}
