package scopes;

import java.util.HashMap;
import java.util.Map;

public class ScopeKeyValue<K,V> {
	private Map<K,V> variables = new HashMap<K, V>();
	
	public void put(K key, V value){
		getVariables().put(key, value);
	}
	
	public V get(K key) {
		return getVariables().get(key);
	}
	
	public boolean containsKey(K key){
		return getVariables().containsKey(key);
	}
	
	public String toString(){
		return getVariables().toString();
	}

	public Map<K,V> getVariables() {
		return variables;
	}

	public void setVariables(Map<K,V> variables) {
		this.variables = variables;
	}
}