package statistics.posttrees;

import java.util.LinkedList;
import java.util.List;

import statistics.Names;
import synthesis.handlers.Handler;
import synthesis.handlers.HandlerFactory;
import types.Type;

public class InstOfExpr extends Expr {

	private Type type;
	private Expr exp;
	private Type argType;

	public InstOfExpr(Type type, Type argType) {
		this.type = type;
		this.argType = argType;
	}

	@Override
	public void addArgs(List<Expr> args) {
		this.exp = args.get(0);
	}

	@Override
	protected String shortReadableRep() {
		return Names.InfixOperator+"("+type+")("+argType+")";
	}
	
	@Override
	protected String shortRep() {
		return Names.InfixOperator+"("+type+")("+argType+")";
	}	

	@Override
	public String getPrefix() {
		return Names.InstOfExpr;
	}

	@Override
	public List<Type> getArgTypes() {
		return new LinkedList<Type>(){{add(argType);}};
	}

	@Override
	public Type getReturnType() {
		return type;
	}

	@Override
	public List<Expr> getArgs() {
		return  new LinkedList<Expr>(){{add(exp);}};
	}

	@Override
	public synthesis.trees.Expr createRep(List<Integer> ids) {
		return new synthesis.trees.InstOfExpr(type, argType, ids.get(0));
	}

	@Override
	public Handler getHandler(HandlerFactory hf) {
		return hf.getInstOfExprHandler();
	}	
}
