package symbol;

import java.util.HashMap;
import java.util.Map;

import lexicalized.info.LexicalizedInfo;
import lexicalized.symbol.LexicalizedNonTerminal;

import org.eclipse.jdt.core.dom.ASTNode;

import scopes.ScopeSymbol;

public class SymbolFactory {
	
	//maps some linearized representation of each termina/nonterminal to appropriate symbol
	private Map<Integer, Symbol> symbols = new HashMap<Integer, Symbol>();
	
	public Symbol getNonTerminal(ASTNode node){
		return getSymbol(new NonTerminal(node));
	}
	
	public Symbol getTerminal(String token){
		return getSymbol(new Terminal(token));
	}
	
	public Symbol getScopeSymbol(String token){
		return getSymbol(new ScopeSymbol(token));
	}
	
	public Symbol getLexicalizedNonTerminal(ASTNode node, LexicalizedInfo info){
		return getSymbol(new LexicalizedNonTerminal(node, info));
	}	
	
    public Symbol getSymbol(Symbol symbol){
    	int index = symbol.hashCode();
    	if (!symbols.containsKey(index)){
    		symbols.put(index, symbol);
    	}
        return symbols.get(index);
    }
}
