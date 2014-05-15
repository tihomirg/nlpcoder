package statistics.posttrees;

import java.util.LinkedList;
import java.util.List;

import statistics.Names;
import types.Type;

public class PrefixOperator extends Expr {

	private String op;
	private Type type;
	private Expr exp;

	public PrefixOperator(String op, Type type) {
		this.op = op;
		this.type = type;
	}

	@Override
	public void addArgs(List<Expr> args) {
		this.exp = args.get(0);
	}

	@Override
	protected String shortRep() {
		return Names.PrefixOperator+"("+op+")"+"("+type+")";
	}

	@Override
	protected String argsRep() {
		return shortReps(exp);
	}
	
	@Override
	public String getPrefix() {
		return Names.PrefixOperator;
	}

	@Override
	public String getOperator() {
		return op;
	}
	
	@Override
	public List<Type> getArgTypes() {
		return new LinkedList<Type>(){{add(type);}};
	}

	@Override
	public Type getReturnType() {
		return type;
	}	
}
