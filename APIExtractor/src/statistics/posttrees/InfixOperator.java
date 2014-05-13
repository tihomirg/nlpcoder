package statistics.posttrees;

import java.util.List;

import statistics.Names;
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

	@Override
	protected String shortRep() {
		return Names.InfixOperator+"("+op+")"+"("+type+")";
	}

	@Override
	protected String argsRep() {
		return shortReps(lexp, rexp);
	}

}
