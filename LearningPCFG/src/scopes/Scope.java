package scopes;

import java.util.LinkedList;
import java.util.List;

import symbol.Symbol;

public class Scope {
	private List<Symbol> symbols = new LinkedList<Symbol>();

	public void add(Symbol symbol){
		symbols.add(symbol);
	}
	
	public boolean contains(Symbol symbol){
		return symbols.contains(symbol);
	}
	
	public List<Symbol> getSymbols() {
		return symbols;
	}

	public void setSymbols(List<Symbol> symbols) {
		this.symbols = symbols;
	}
}