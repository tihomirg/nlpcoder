package lexicalized.symbol;

import lexicalized.info.LexicalizedInfo;

import org.eclipse.jdt.core.dom.ASTNode;

import symbol.NonTerminal;

public class LexicalizedNonTerminal extends NonTerminal {

	private LexicalizedInfo info;
	
	public LexicalizedNonTerminal(ASTNode node, LexicalizedInfo info) {
		super(node);
		this.info = info;
		// TODO Auto-generated constructor stub
	}

	protected String toStringNaive(){
		return "L"+super.toStringNaive()+this.info;
	}	
}
