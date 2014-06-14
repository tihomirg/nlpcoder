package synthesis.handlers;

import statistics.posttrees.Expr;
import types.Type;

public class SearchKey {
	
	private Expr expr;
	private Type type;
	
	public SearchKey(Expr expr) {
		this(expr, null);
	}

	public SearchKey(Expr expr, Type type) {
		this.type = type;
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

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}
