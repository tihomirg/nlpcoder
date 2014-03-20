package symbol.temp;

import java.util.Set;

import definitions.Declaration;

import selection.types.Type;
import symbol.Symbol;

public class DeclarationSet extends Symbol {

	public Set<Declaration> decls;
	
	public DeclarationSet(Set<Declaration> decls) {
		this.decls = decls;
	}

	@Override
	public String head() {
		return null;
	}

	public void setDecls(Set<Declaration> decls) {
		this.decls = decls;
	}

	public Set<Declaration> getDecls() {
		return decls;
	}
	
	public boolean isTemp() {
		return true;
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
	public boolean hasDecls() {
		return true;
	}
	
}
