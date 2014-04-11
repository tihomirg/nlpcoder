package sequences.trees;

import selection.types.Type;

public class Cast extends Expr {

	private Expr exp;

	public Cast(Type type, Expr exp) {
		super(type);
		this.exp = exp;
	}

	@Override
	public String toString() {
		return "("+type+")"+ exp;
	}
	
	
}
