package statistics.parsers;

import statistics.posttrees.Expr;

public class SingleResult extends Result {
	private Expr expr;	

	public SingleResult() {
	}
	
	public SingleResult(String rest) {
		super(rest);
	}	
	
	public SingleResult(Expr expr, String rest) {
		super(rest);
		this.expr = expr;
	}
	
	public Expr getExpr() {
		return expr;
	}
	public void setExpr(Expr expr) {
		this.expr = expr;
	}
}
