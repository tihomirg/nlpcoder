package statistics.posttrees;

import java.util.List;

import types.Type;

public class PrefixOperator extends Expr {

	private String op;
	private Type type;
	private Expr expr;

	public PrefixOperator(String op, Type type) {
		this.op = op;
		this.type = type;
	}

	@Override
	public void addArgs(List<Expr> args) {
		this.expr = args.get(0);
	}

}
