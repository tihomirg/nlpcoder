package statistics.posttrees;

import java.util.List;

import statistics.Names;
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

	@Override
	protected String shortRep() {
		return Names.PostfixOperator+"("+op+")"+"("+type+")";
	}

	@Override
	protected String argsRep() {
		return shortReps(exp);
	}

	@Override
	public String getPrefix() {
		return Names.PostfixOperator;
	}
	
	@Override
	public boolean hasType() {
		return true;
	}
	
	public Type getType() {
		return type;
	}
	
	@Override
	public String getOperator() {
		return op;
	}	
}
