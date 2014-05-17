package statistics.posttrees;

import java.util.LinkedList;
import java.util.List;

import statistics.Names;
import statistics.handlers.Handler;
import statistics.handlers.HandlerFactory;
import types.Type;

public class Assignment extends Expr {

	public String op;
	private Type type;
	
	private Expr lexp;
	private Expr rexp;
	
	public Assignment(String op, Type type) {
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
		return Names.Assignment+"("+op+")("+type+")";
	}
	
	@Override
	protected String argsRep() {
		return shortReps(lexp, rexp);
	}

	@Override
	public String getPrefix() {
		return Names.Assignment;
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

	@Override
	public List<Expr> getArgs() {
		List<Expr> args = new LinkedList<Expr>();
		args.add(lexp);
		args.add(rexp);
		return args;
	}

	@Override
	public synthesis.trees.Expr createRep(List<Integer> ids) {
		return new synthesis.trees.Assignment(op, type, ids.get(0), ids.get(1));
	}

	@Override
	public Handler getHandler(HandlerFactory hf) {
		return hf.getAssignmentHandler();
	}
	
}
