package statistics.posttrees;

import java.util.List;

import statistics.Names;
import synthesis.handlers.Handler;
import synthesis.handlers.HandlerFactory;
import types.Type;

public class BooleanLiteral extends Expr {

	private boolean value;
	private Type type;
	
	public BooleanLiteral(Type type) {
		this.type = type;
	}
	
	@Override
	public void addArgs(List<Expr> args) {
		String string = args.get(0).getString();
		value = Boolean.parseBoolean(string);
	}

	@Override
	protected String shortRep() {
		return Names.BooleanLiteral;
	}

	@Override
	protected String argsRep() {
		return Boolean.toString(value);
	}

	@Override
	public String getPrefix() {
		return Names.BooleanLiteral;
	}
	
	@Override
	public boolean isLiteral() {
		return true;
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
	public List<Expr> getArgs() {
		return Expr.EMPTY_EXPR_LIST;
	}

	@Override
	public synthesis.trees.Expr createRep(List<Integer> ids) {
		return new synthesis.trees.BooleanLitera(type);
	}

	@Override
	public Handler getHandler(HandlerFactory hf) {
		return hf.getBooleanLiteralHandler();
	}
}
