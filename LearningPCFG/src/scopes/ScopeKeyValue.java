package scopes;

import java.util.HashMap;
import java.util.Map;

public class ScopeKeyValue<K,V> {
	private Map<K,V> variables = new HashMap<K, V>();
	
	public void put(K key, V value){
		variables.put(key, value);
	}
	
	public V get(K key) {
		return variables.get(key);
	}
	
	public boolean containsKey(K key){
		return variables.containsKey(key);
	}
	
	public String toString(){
		return variables.toString();
	}
}