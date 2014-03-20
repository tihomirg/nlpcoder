package symbol;

import java.util.Set;

import selection.types.Type;
import definitions.Declaration;

public class Null extends Symbol {

	@Override
	public String head() {
		return "null";
	}

	@Override
	public String toString() {
		return "null";
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
