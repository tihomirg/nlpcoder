package util;

import org.eclipse.jdt.core.dom.ASTNode;

import symbol.NonTerminal;
import symbol.Symbol;

public class NonTerminalIndex extends SymbolIndex {

	private ASTNode node;

	public NonTerminalIndex(ASTNode node){
		this.node = node;
	}

	protected String naiveName(){
		return node.getClass().getSimpleName();
	}

	@Override
	protected ASTNode getParent() {
		return node.getParent();
	}
	
	@Override
	public Symbol getSymbol() {
		return new NonTerminal(node);
	}	
}
