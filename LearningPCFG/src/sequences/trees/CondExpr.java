package sequences.trees;

public class CondExpr extends Expr {

	private Expr exp;
	private Expr thenExp;
	private Expr elseExp;

	public CondExpr(Expr exp, Expr thenExp, Expr elseExp) {
		super(thenExp.getType());

		this.exp = exp;
		this.thenExp = thenExp;
		this.elseExp = elseExp;
	}

	public Expr getExp() {
		return exp;
	}

	public void setExp(Expr exp) {
		this.exp = exp;
	}

	public Expr getThenExp() {
		return thenExp;
	}

	public void setThenExp(Expr thenExp) {
		this.thenExp = thenExp;
	}

	public Expr getElseExp() {
		return elseExp;
	}

	public void setElseExp(Expr elseExp) {
		this.elseExp = elseExp;
	}

	@Override
	public String toString() {
		return exp + " ? " + thenExp + " : "+ elseExp;
	}
}
