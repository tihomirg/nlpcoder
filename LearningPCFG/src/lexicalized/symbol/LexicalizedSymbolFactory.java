package lexicalized.symbol;

import lexicalized.info.LexicalizedInfo;

import org.eclipse.jdt.core.dom.ASTNode;

import symbol.NonTerminal;
import symbol.Symbol;
import symbol.SymbolFactory;

public class LexicalizedSymbolFactory extends SymbolFactory {

	public static Symbol getLexicalizedNonTerminal(ASTNode node, LexicalizedInfo info){
		return getSymbol(new LexicalizedNonTerminal(node, info));
	}
}
