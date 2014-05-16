package definitions;

import statistics.posttrees.Expr;

public class SearchKey {
	
	private Expr expr;
	
	public SearchKey(Expr expr) {
		this.expr = expr;
	}

	public String getHandlerName() {
		return this.expr.getPrefix();
	}

	public Expr getExpr() {
		return this.expr;
	}
}
