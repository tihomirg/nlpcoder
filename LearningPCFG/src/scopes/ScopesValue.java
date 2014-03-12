package scopes;

import java.util.Stack;

public class ScopesValue<V> {

	private Stack<ScopeValue<V>> scopes = new Stack<ScopeValue<V>>();
	
	public void push(){
		scopes.push(new ScopeValue<V>());
	}
	
	public ScopeValue<V> peek(){
		return scopes.peek();
	}
	
	public void pop(){
		scopes.pop();
	}
	
	public void put(V value){
		peek().put(value);
	}
	
	public boolean contains(V value){
		for(ScopeValue<V> scope: scopes){
			if (scope.contains(value)) return true;
		}
		return false;
	}
		
	public int size() {return scopes.size();}
	
	public String toString(){
	  String s = "";
	  for(ScopeValue<V> scope: scopes){
		s+="---------------------------------\n";
		s+=scope;
	  }
	  return s;
	}
}
