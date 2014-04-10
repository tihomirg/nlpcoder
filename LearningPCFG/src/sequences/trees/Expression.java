package sequences.trees;

import selection.types.Type;

public abstract class Expression {
	private Type type;
	
	public Expression(Type type) {
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	
}
