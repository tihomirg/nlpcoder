package statistics.posttrees;

import java.util.LinkedList;
import java.util.List;

import statistics.Names;
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
	protected String shortRep() {
		return Names.CondExpr+"("+retType+")";
	}

	@Override
	protected String argsRep() {
		return shortReps(expr, thenExpr, elseExpr);
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
}
