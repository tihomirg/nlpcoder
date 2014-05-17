package statistics.posttrees;

import java.util.List;

import statistics.Names;
import statistics.handlers.Handler;
import statistics.handlers.HandlerFactory;
import types.Type;

public class CharacterLiteral extends Expr {

	private String value;
	private Type type;

	public CharacterLiteral(Type type) {
		this.type = type;
	}
	
	@Override
	public void addArgs(List<Expr> args) {
		this.value = args.get(0).getString();
	}

	@Override
	protected String shortRep() {
		return Names.CharacterLiteral;
	}

	@Override
	protected String argsRep() {
		return value;
	}

	@Override
	public String getPrefix() {
		return Names.CharacterLiteral;
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
		return new synthesis.trees.CharacterLiteral(type);
	}

	@Override
	public Handler getHandler(HandlerFactory hf) {
		return hf.getCharacterLiteralHandler();
	}	
}
