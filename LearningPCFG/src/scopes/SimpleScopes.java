package scopes;

import symbol.Symbol;
import config.Config;

public class SimpleScopes extends Scopes<Symbol> {

	public void add(String name){
		add(Config.getFactory().getScopeSymbol(name));
	}
	
	public boolean contains(String name){
		return contains(Config.getFactory().getScopeSymbol(name));
	}
	
}
