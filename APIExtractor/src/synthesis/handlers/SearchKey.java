package synthesis.handlers;

import statistics.posttrees.Expr;

public class SearchKey {
	
	private Expr expr;
	
	public SearchKey(Expr expr) {
		this.expr = expr;
	}

	public Expr getExpr() {
		return this.expr;
	}

	public Handler getHandler(HandlerFactory hf) {
		return expr.getHandler(hf);
	}
	
	@Override
	public String toString() {
		return expr.shortReadableRep();
	}
}