package scopes;

import java.util.Stack;

import config.Config;
import symbol.Symbol;

public class Scopes<T> {

	private Stack<Scope<T>> scopes = new Stack<Scope<T>>();
	
	public void push(){
		scopes.push(new Scope<T>());
	}
	
	public Scope<T> peek(){
		return scopes.peek();
	}
	
	public void pop(){
		scopes.pop();
	}
	
	public void add(T symbol){
		peek().add(symbol);
	}
	
	public boolean contains(T symbol){
		for(Scope<T> scope: scopes){
			if (scope.contains(symbol)) return true;
		}
		return false;
	}
		
	public int size() {return scopes.size();}
	
}
