package statistics.posttrees;

import java.util.LinkedList;
import java.util.List;

import statistics.Names;
import synthesis.handlers.Handler;
import synthesis.handlers.HandlerFactory;
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
	protected String shortReadableRep() {
		return Names.PrefixOperator+"("+op+")"+"("+type+")";
	}
	
	@Override
	protected String shortRep() {
		return Names.PrefixOperator+"("+op+")"+"("+type+")";
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

	@Override
	public List<Expr> getArgs() {
		return new LinkedList<Expr>(){{add(exp);}};
	}

	@Override
	public synthesis.trees.Expr createRep(List<Integer> ids) {
		return new synthesis.trees.PrefixOperator(op, type, ids.get(0));
	}

	@Override
	public Handler getHandler(HandlerFactory hf) {
		return hf.getPrefixOperatorHandler();
	}	
}
