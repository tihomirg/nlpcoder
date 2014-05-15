package statistics.posttrees;

import java.util.LinkedList;
import java.util.List;

import statistics.Names;
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
	protected String shortRep() {
		return Names.CastExpr+"("+type+")("+argType+")";
	}

	@Override
	protected String argsRep() {
		return shortReps(expr);
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
}
