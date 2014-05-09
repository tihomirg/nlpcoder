package statistics.posttrees;

import java.util.List;

public class CondExpr extends Expr {

	private Expr expr;
	private Expr thenExpr;
	private Expr elseExpr;

	@Override
	public void addArgs(List<Expr> args) {
		this.expr = args.get(0);
		this.thenExpr = args.get(1);
		this.elseExpr = args.get(2);
	}

}
