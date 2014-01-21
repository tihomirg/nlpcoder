package scopes;

import java.util.LinkedList;
import java.util.List;

import symbol.Symbol;

public class Scope<T> {
	private List<T> symbols = new LinkedList<T>();

	public void add(T symbol){
		symbols.add(symbol);
	}
	
	public boolean contains(T symbol){
		return symbols.contains(symbol);
	}
	
	public List<T> getSymbols() {
		return symbols;
	}

	public void setSymbols(List<T> symbols) {
		this.symbols = symbols;
	}
}