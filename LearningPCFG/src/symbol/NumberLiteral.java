package symbol;

import java.util.Set;

import selection.types.Type;
import definitions.Declaration;

public class NumberLiteral extends Symbol {

	private String number;
	
	public NumberLiteral(String number) {
		this.number = number;
	}

	@Override
	public String head() {
		return number;
	}

	@Override
	public String toString() {
		return number;
	}

	@Override
	public Type retType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasRetType() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isVariable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<Declaration> getDecls() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasDecls() {
		// TODO Auto-generated method stub
		return false;
	}

}
