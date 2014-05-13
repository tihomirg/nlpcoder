package statistics.posttrees;

import java.util.List;

import statistics.Names;
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

	@Override
	protected String shortRep() {
		return Names.CastExpr+"("+type+")";
	}

	@Override
	protected String argsRep() {
		return shortReps(expr);
	}

}
