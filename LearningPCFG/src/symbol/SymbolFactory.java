package symbol;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;

public class SymbolFactory {
	
	//maps some linearized representation of each termina/nonterminal to appropriate symbol
	private static Map<Integer, Symbol> symbols = new HashMap<Integer, Symbol>();
	
	public static Symbol getNonTerminal(ASTNode node){
		return getSymbol(new NonTerminal(node));
	}
	
	public static Symbol getTerminal(String token, ASTNode parent){
		return getSymbol(new Terminal(token, parent));
	}	
	
    public static Symbol getSymbol(Symbol symbol){
    	int index = symbol.hashCode();
    	if (!symbols.containsKey(index)){
    		symbols.put(index, symbol);
    	}
        return symbols.get(index);
    }
}
