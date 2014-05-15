package statistics.posttrees;

import java.util.LinkedList;
import java.util.List;

import statistics.Names;
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
	protected String shortRep() {
		return Names.InfixOperator+"("+type+")("+argType+")";
	}

	@Override
	protected String argsRep() {
		return shortReps(exp);
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
}
