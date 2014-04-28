package sequences.trees;

import selection.types.Type;

public class InstOfExpr extends Expr {

	private Expr exp;
	private Type checkType;

	public InstOfExpr(Expr exp, Type checkType, Type type) {
		super(type);
		this.exp = exp;
		this.checkType = checkType;
	}

	public Expr getExp() {
		return exp;
	}

	public void setExp(Expr exp) {
		this.exp = exp;
	}

	@Override
	public String toString() {
		return exp +" instanceof "+checkType;
	}
}
