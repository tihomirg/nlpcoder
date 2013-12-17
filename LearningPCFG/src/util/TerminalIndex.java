package util;

import org.eclipse.jdt.core.dom.ASTNode;

import symbol.Symbol;
import symbol.Terminal;

public class TerminalIndex extends SymbolIndex {

	private String token;
	private ASTNode parent;
	
	public TerminalIndex(String token, ASTNode parent){
		this.token = token;
		this.parent = parent;
	}
	
	@Override
	public Symbol getSymbol() {
		return new Terminal(token, parent);
	}

	@Override
	protected ASTNode getParent() {
		return parent;
	}

	@Override
	protected String naiveName() {
		return token;
	}

}
