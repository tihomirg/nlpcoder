package statistics.posttrees;

import java.util.List;

import types.Type;

public class InstOfExpr extends Expr {

	private Type type;
	private Expr exp;

	public InstOfExpr(Type type) {
		this.type = type;
	}

	@Override
	public void addArgs(List<Expr> args) {
		this.exp = args.get(0);
	}

}
