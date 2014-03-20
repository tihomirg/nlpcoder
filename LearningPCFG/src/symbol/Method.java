package symbol;

import java.util.Set;

import selection.types.Type;
import definitions.Declaration;

public class Method extends SymbolWithReceiver {
	private Declaration decl;
	private Symbol[] args;
	
	public Method(Declaration decl, Symbol receiver, Symbol[] args) {
		super(receiver);
		this.decl = decl;
		this.args = args;
	}

	public Method(Declaration decl) {
		this(decl, null, null);
	}
	
	public Declaration getDecl() {
		return decl;
	}

	public Symbol[] getArgs() {
		return args;
	}

	private String argsToString(){
		return symbolHeadsToString(this.args);
	}

	@Override
	public String toString() {
		return receiverToString()+decl.getFullName()+"("+argsToString()+")";
	}

	@Override
	public String head() {
		return "Method("+decl.getFullName()+","+decl.getArgNum()+")";
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
