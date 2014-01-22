package scopes;

import java.util.LinkedList;
import java.util.List;

public class Scope<T> {
	private boolean typedecl;
	
	private List<T> symbols = new LinkedList<T>();
	
	public Scope(){}

	public Scope(boolean typedecl){
		this.typedecl = typedecl;
	}
	
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

	public boolean isTypedecl() {
		return typedecl;
	}

	public void setTypedecl(boolean typedecl) {
		this.typedecl = typedecl;
	}
	
	public T get(T index) {
		for(T symbol: symbols){
			if (symbol.equals(index)) return symbol;
		}
		return null;
	}
	
	public String toString(){
		String s = "";
		for(T symbol: symbols){
			s += symbol+"\n";
		}
		return s;
	}
}