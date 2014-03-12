package scopes;

import java.util.Stack;

public class ScopesKeyValue<K,V> {

	private Stack<ScopeKeyValue<K,V>> scopes = new Stack<ScopeKeyValue<K,V>>();
	
	public void push(){
		scopes.push(new ScopeKeyValue<K,V>());
	}
	
	public ScopeKeyValue<K,V> peek(){
		return scopes.peek();
	}
	
	public void pop(){
		scopes.pop();
	}
	
	public void put(K key, V value){
		peek().put(key, value);
	}
	
	public boolean contains(K key){
		for(ScopeKeyValue<K,V> scope: scopes){
			if (scope.containsKey(key)) return true;
		}
		return false;
	}
		
	public int size() {return scopes.size();}
	
	public String toString(){
	  String s = "";
	  for(ScopeKeyValue<K,V> scope: scopes){
		s+="---------------------------------\n";
		s+=scope;
	  }
	  return s;
	}
}
