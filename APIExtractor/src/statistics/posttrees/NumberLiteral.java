package statistics.posttrees;

import java.util.List;

import statistics.Names;
import types.Type;

public class NumberLiteral extends Expr {

	private String value;
	private Type type;

	public NumberLiteral(Type type) {
		this.type = type;
	}
	
	@Override
	public void addArgs(List<Expr> args) {
		this.value = args.get(0).getString();		
	}

	@Override
	protected String shortRep() {
		return Names.NumberLiteral;
	}

	@Override
	protected String argsRep() {
		return value;
	}
	@Override
	public String getPrefix() {
		return Names.NumberLiteral;
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
}
