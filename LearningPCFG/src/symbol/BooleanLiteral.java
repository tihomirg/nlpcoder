package symbol;

import java.util.Set;

import definitions.Declaration;
import selection.types.Type;
import selection.types.TypeFactory;

public class BooleanLiteral extends Symbol {

	private boolean value;
	private Type retType;
	
	public BooleanLiteral(boolean value, TypeFactory factory) {
		this.value = value;
		this.retType = factory.createConst("java.lang.Boolean");
	}

	@Override
	public String head() {
		return "Boolean("+value+")";
	}

	@Override
	public String toString() {
		return Boolean.toString(value);
	}

	@Override
	public Type retType() {
		return retType;
	}

	@Override
	public boolean hasRetType() {
		return true;
	}

	@Override
	public boolean isVariable() {
		return false;
	}

	@Override
	public Set<Declaration> getDecls() {
		return null;
	}

	@Override
	public boolean hasDecls() {
		return false;
	}
}
