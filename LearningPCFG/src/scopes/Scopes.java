package scopes;

import java.util.Stack;

import config.Config;
import symbol.Symbol;

public class Scopes {

	private Stack<Scope> scopes = new Stack<Scope>();
	
	public void push(){
		scopes.push(new Scope());
	}
	
	public Scope peek(){
		return scopes.peek();
	}
	
	public void pop(){
		scopes.pop();
	}
	
	public void add(Symbol symbol){
		peek().add(symbol);
	}
	
	public boolean contains(Symbol symbol){
		for(Scope scope: scopes){
			if (scope.contains(symbol)) return true;
		}
		return false;
	}
	
	
	public void add(String name){
		add(Config.getFactory().getScopeSymbol(name));
	}
	
	public boolean contains(String name){
		return contains(Config.getFactory().getScopeSymbol(name));
	}	
}
