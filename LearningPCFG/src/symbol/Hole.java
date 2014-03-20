package symbol;

import java.util.Set;

import selection.types.Type;
import definitions.Declaration;

public class Hole extends Symbol {
	@Override
	public String head() {
		return "HOLE";
	}

	@Override
	public String toString() {
		return "HOLE";
	}

	@Override
	public Type retType() {
		return null;
	}

	@Override
	public boolean hasRetType() {
		return false;
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
