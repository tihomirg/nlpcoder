package scopes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ScopeValue<V> {
	private Set<V> variables = new HashSet<V>();
	
	public void put(V value){
		variables.add(value);
	}
	
	public boolean contains(V value){
		return variables.contains(value);
	}
	
	public String toString(){
		return variables.toString();
	}
}
