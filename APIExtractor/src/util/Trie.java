package util;

import java.util.HashMap;
import java.util.LinkedList;

public abstract class Trie<K, V> {
	protected TrieNode<K, V> root = new TrieNode<K, V>();
	
	public void add(K key, V val){
		root.add(split(key), val);
	}
	
	public abstract LinkedList<K> split(K key);
	
	public abstract void print();
	
	public static class TrieNode<K, V> {
		
		private V val;
		
		private HashMap<K, TrieNode<K, V> > map = new HashMap<K, TrieNode<K, V>>();

		public void add(LinkedList<K> key, V val) {
			if (key.size() > 0){
				K internalKey = key.remove();
				if (getMap().containsKey(internalKey)){
					TrieNode<K, V> node = map.get(internalKey);
					node.add(key, val);
				} else {
					TrieNode<K, V> node = new TrieNode<K,V>();
					node.add(key, val);
					map.put(internalKey, node);
				}
			} else this.val = val;
		}
		
		public void print(){
		  if (val != null) System.out.println(val);
		}

		public HashMap<K, TrieNode<K, V> > getMap() {
			return map;
		}
	}	
}


