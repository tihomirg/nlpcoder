package statistics.posttrees;

import java.util.List;

import types.Type;

public class CastExpr extends Expr {

	private Type type;
	private Expr expr;

	public CastExpr(Type type) {
		this.type = type;
	}

	@Override
	public void addArgs(List<Expr> args) {
		this.expr = args.get(0);
	}

}
