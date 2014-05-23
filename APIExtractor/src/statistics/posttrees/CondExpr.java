package statistics.posttrees;

import java.util.LinkedList;
import java.util.List;

import statistics.Names;
import synthesis.handlers.Handler;
import synthesis.handlers.HandlerFactory;
import types.Type;

public class CondExpr extends Expr {

	private Expr expr;
	private Expr thenExpr;
	private Expr elseExpr;
	private Type condType;
	private Type retType;

	public CondExpr(Type condType, Type retType) {
		this.condType = condType;
		this.retType = retType;
	}
	
	@Override
	public void addArgs(List<Expr> args) {
		this.expr = args.get(0);
		this.thenExpr = args.get(1);
		this.elseExpr = args.get(2);
	}

	@Override
	protected String shortReadableRep() {
		return Names.CondExpr+"("+retType+")";
	}
	
	@Override
	protected String shortRep() {
		return Names.CondExpr+"("+retType+")";
	}	

	@Override
	public String getPrefix() {
		return Names.CondExpr;
	}

	@Override
	public List<Type> getArgTypes() {
		return new LinkedList<Type>(){{add(condType); add(retType); add(retType);}};
	}

	@Override
	public Type getReturnType() {
		return this.retType;
	}

	@Override
	public List<Expr> getArgs() {
		return new LinkedList<Expr>(){{add(expr); add(thenExpr); add(elseExpr);}};
	}

	@Override
	public synthesis.trees.Expr createRep(List<Integer> ids) {
		return new synthesis.trees.CondExpr(condType, retType, ids.get(0), ids.get(1), ids.get(2));
	}

	@Override
	public Handler getHandler(HandlerFactory hf) {
		return hf.getCondExprHandler();
	}	
}
