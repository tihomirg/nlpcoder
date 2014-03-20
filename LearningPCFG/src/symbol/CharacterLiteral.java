package symbol;

import java.util.Set;

import selection.types.Const;
import selection.types.Type;
import selection.types.TypeFactory;
import definitions.Declaration;

public class CharacterLiteral extends Symbol {

	private char value;
	private Const retType;
	
	public CharacterLiteral(char value, TypeFactory factory) {
		this.value = value;
		this.retType = factory.createConst("java.lang.Character");
	}

	@Override
	public String head() {
		return "Char("+value+")";
	}

	@Override
	public String toString() {
		return Character.toString(value);
	}

	@Override
	public Type retType() {
		return this.retType;
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
