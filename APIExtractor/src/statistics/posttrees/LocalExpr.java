package statistics.posttrees;

import java.util.List;

import synthesis.handlers.Handler;
import synthesis.handlers.HandlerFactory;
import types.Type;

public class LocalExpr extends Expr {

	private String name;
	private Type type;
	
	public LocalExpr(String name, Type type) {
		this.name = name;
		this.type = type;
	}
	
	@Override
	public void addArgs(List<Expr> args) {
	}

	@Override
	public List<Type> getArgTypes() {
		return Type.EMPTY_TYPE_LIST;
	}

	@Override
	public Type getReturnType() {
		return type;
	}

	@Override
	public String shortReadableRep() {
		return null;
	}

	@Override
	protected String shortRep() {
		return null;
	}

	@Override
	public String getPrefix() {
		return name;
	}

	@Override
	public List<Expr> getArgs() {
		return EMPTY_EXPR_LIST;
	}

	@Override
	public synthesis.trees.Expr createRep(List<Integer> ids) {
		return new synthesis.trees.LocalExpr(name);
	}

	@Override
	public Handler getHandler(HandlerFactory hf) {
		return null;//hf.getLocalHandler();
	}
}
