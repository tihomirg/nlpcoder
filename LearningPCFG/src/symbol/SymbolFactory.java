package symbol;

import java.util.HashMap;
import java.util.Map;

import util.SymbolIndex;

public class SymbolFactory {
	
	//maps some linearized representation of each termina/nonterminal to appropriate symbol
	private static Map<SymbolIndex, Symbol> symbols = new HashMap<SymbolIndex, Symbol>();
	
    public static Symbol getSymbol(SymbolIndex index){
    	//String linearized = Linerizer.linearize(node);
    	if (!symbols.containsKey(index)){
    		symbols.put(index, index.getSymbol());
    	}
        return symbols.get(index);
    }
}
