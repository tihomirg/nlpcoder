package statistics.posttrees;

import java.util.LinkedList;
import java.util.List;

import statistics.Names;
import statistics.handlers.Handler;
import statistics.handlers.HandlerFactory;
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
		return new synthesis.trees.PostfixOperator(op, type, ids.get(0));
	}

	@Override
	public Handler getHandler(HandlerFactory hf) {
		return hf.getPostfixOperatorHandler();
	}	
}
