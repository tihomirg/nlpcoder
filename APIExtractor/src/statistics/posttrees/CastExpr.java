package statistics.posttrees;

import java.util.LinkedList;
import java.util.List;

import statistics.Names;
import synthesis.handlers.Handler;
import synthesis.handlers.HandlerFactory;
import types.Type;

public class CastExpr extends Expr {

	private Type type;
	private Expr expr;
	private Type argType;

	public CastExpr(Type type, Type argType) {
		this.type = type;
		this.argType = argType;
	}

	@Override
	public void addArgs(List<Expr> args) {
		this.expr = args.get(0);
	}

	@Override
	public String shortReadableRep() {
		return Names.CastExpr+"("+type+")("+argType+")";
	}
	
	@Override
	protected String shortRep() {
		return Names.CastExpr+"("+type+")("+argType+")";
	}	

	@Override
	public String getPrefix() {
		return Names.CastExpr;
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
		return new LinkedList<Expr>(){{add(expr);}};
	}

	@Override
	public synthesis.trees.Expr createRep(List<Integer> ids) {
		return new synthesis.trees.CastExpr(argType, type, ids.get(0));
	}

	@Override
	public Handler getHandler(HandlerFactory hf) {
		return hf.getCastExprHandler();
	}
}
