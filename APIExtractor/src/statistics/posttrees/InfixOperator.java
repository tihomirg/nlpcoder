package statistics.posttrees;

import java.util.LinkedList;
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
	
	@Override
	public String getPrefix() {
		return Names.InfixOperator;
	}

	@Override
	public String getOperator() {
		return op;
	}
	
	@Override
	public List<Type> getArgTypes() {
		return new LinkedList<Type>(){{add(type); add(type);}};
	}

	@Override
	public Type getReturnType() {
		return type;
	}	
}
