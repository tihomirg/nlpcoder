package sequences.trees;

import selection.types.Type;

public class Cast extends Expression {

	private Expression exp;

	public Cast(Type type, Expression exp) {
		super(type);
		this.exp = exp;
	}
}
