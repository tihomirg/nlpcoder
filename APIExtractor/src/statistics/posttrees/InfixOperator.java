package statistics.posttrees;

import java.util.List;

import types.Type;

public class InfixOperator extends Expr {

	private String op;
	private Type type;
	private Expr lexp;
	private Expr rexp;

	public InfixOperator(String op, Type type) {
		this.op = op;
		this.type = type;
	}

	@Override
	public void addArgs(List<Expr> args) {
		this.lexp = args.get(0);
		this.rexp = args.get(1);
	}

}
