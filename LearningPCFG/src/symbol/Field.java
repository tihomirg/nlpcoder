package symbol;

import java.util.Set;

import selection.types.Type;
import definitions.Declaration;

public class Field extends SymbolWithReceiver {

	private Declaration decl;

	public Field(Declaration decl) {
		this(decl, null);
	}	
	
	public Field(Declaration decl, Symbol reciever) {
		super(reciever);
		this.decl = decl;
	}

	@Override
	public String head() {
		return "Field("+decl.getFullName()+")";
	}
	
	@Override
	public String toString() {
		return receiverToString()+decl.getFullName();
	}

	@Override
	public Type retType() {
		return decl.getRetType();
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
