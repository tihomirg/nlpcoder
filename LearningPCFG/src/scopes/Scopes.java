package scopes;

import java.util.Stack;

public class Scopes<T> {

	private Stack<Scope<T>> scopes = new Stack<Scope<T>>();
	
	public void push(){
		scopes.push(new Scope<T>());
	}
	
	public void pushTD(){
		scopes.push(new Scope<T>(true));
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
	
	public boolean containsTD(T symbol){
		for(Scope<T> scope: scopes){
			if (scope.isTypedecl() && scope.contains(symbol)) return true;
		}
		return false;
	}
	
	public T get(T symbol){
		for(Scope<T> scope: scopes){
			T a = scope.get(symbol);
			if (a != null) return a;
		}
		return null;		
	}
	
	public T getTD(T symbol){
		for(Scope<T> scope: scopes){
			if (scope.isTypedecl()){
			  T a = scope.get(symbol);
			  if (a != null) return a;
			}
		}
		return null;		
	}
	
	public String toString(){
	  String s = "";
	  for(Scope<T> scope: scopes){
		s+="---------------------------------\n";  
		s+= (scope.isTypedecl()? "TD\n":"");
		s+=scope;
	  }
	  return s;
	}
}
