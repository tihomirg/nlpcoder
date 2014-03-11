package scopes;

import java.util.Stack;

public class Scopes<K,V> {

	private Stack<Scope<K,V>> scopes = new Stack<Scope<K,V>>();
	
	public void push(){
		scopes.push(new Scope<K,V>());
	}
	
	public Scope<K,V> peek(){
		return scopes.peek();
	}
	
	public void pop(){
		scopes.pop();
	}
	
	public void put(K key, V value){
		peek().put(key, value);
	}
	
	public boolean contains(K key){
		for(Scope<K,V> scope: scopes){
			if (scope.containsKey(key)) return true;
		}
		return false;
	}
		
	public int size() {return scopes.size();}
	
	public String toString(){
	  String s = "";
	  for(Scope<K,V> scope: scopes){
		s+="---------------------------------\n";
		s+=scope;
	  }
	  return s;
	}
}
