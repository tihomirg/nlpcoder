package statistics.posttrees;

import java.util.List;

import types.Type;

public class PostfixOperator extends Expr {

	private String op;
	private Type type;
	private Expr exp;

	public PostfixOperator(String op, Type type) {
		this.op = op;
		this.type = type;
	}

	@Override
	public void addArgs(List<Expr> args) {
		this.exp = args.get(0);
	}

}
