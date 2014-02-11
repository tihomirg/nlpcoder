import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import util.Trie;
import util.Trie.TrieNode;


public class URLScanner {

	private LinkedList<JarPair> jars = new LinkedList<JarPair>();	
	
	private static final int MAX_JARS = 500;
	
	private int jar_count = 0;
	
	public void scan(String link) throws Throwable {
		URL url;
		try {
			url = new URL(link);

		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = org.apache.commons.io.IOUtils.toString(in, encoding);
		
		String[] splits = body.split("<A HREF=\"");
		
		in.close();
		
		LinkedList<String> folders = new LinkedList<String>();		
		
		if(splits.length >= 2){
			
			for(int i=2; i<splits.length; i++){
				String s = splits[i].substring(0, splits[i].indexOf("\""));
				
				if (s.endsWith(".jar") && 
					!s.endsWith("-sources.jar") &&
				    !s.endsWith("-javadoc.jar")){
					
				 JarPair jar = new JarPair(link, s);	
				 jars.add(jar);
				 jar_count++;
				 
				 System.out.println(jar);
				 
				 if (jar_count >= MAX_JARS){
					 throw new Exception();
				 }
				} else {
				 if (s.endsWith("/")){
					 folders.add(link+s);
				 }		 
				}
			}
		}
		
		scan(folders);
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void scan(LinkedList<String> folders) throws Throwable {
		for(String folder: folders){
			scan(folder);
		}
		
	}
	
	public JarTrie processJars(){
		JarTrie trie = new JarTrie();
		for(JarPair jar: jars){
			trie.add(jar.getName(), jar);
		}
		return trie;
	}

//	public List<String> processJars() {
//		Map<String, List<List<String>>> groups = new HashMap<String, List<List<String>>>();
//		
//		for(String jar: jars) {
//			char[] cjar = jar.toCharArray();
//			List<String> rep = new LinkedList<String>();
//			String word = "";
//			boolean isNumber = false;
//			for(char c: cjar){
//				if (Character.isDigit(c)){
//					if (isNumber){
//						word += c;
//					} else {
//						rep.add(word);
//						word = Character.toString(c);
//					}
//					
//					isNumber = true;
//				} else {
//					if (!isNumber){
//						word += c;
//					} else {
//						rep.add(word);
//						word = Character.toString(c);
//					}
//					
//					isNumber = false;
//				}
//			}
//			
//			rep.add(word);
//			
//			String prefix = rep.get(0);
//			
//			if (groups.containsKey(prefix)){
//				List<List<String>> list = groups.get(prefix);
//				list.add(rep);
//			} else{
//				List<List<String>> list = new LinkedList<List<String>>();
//				list.add(rep);
//				groups.put(prefix, list);
//			}
//			
//			
////			String[] splits = jar.split("[-,.]");
////			
////			List<String> splits2 = new LinkedList<String>();
////			
////			for(String split : splits){
////				splits2.add(split);
////			}
////			
////			String prefix = splits[0];
////			
////			if (groups.containsKey(prefix)){
////			  List<List<String>> list = groups.get(prefix);
////			  list.add(splits2);
////		    } else{
////			  List<List<String>> list = new LinkedList<List<String>>();
////			  list.add(splits2);
////			  groups.put(prefix, list);
////		    }
//		}
//		
//		List<String> result = new LinkedList<String>(); 
//		
//		for (List<List<String>> group : groups.values()){
//			result.add(listToString(processGroup(group)));
//		}
//		
//		return result;
//		
//	}
//
//	private String listToString(List<String> result2) {
//		String s ="";
//		for (String s1 : result2){
//			s+=s1;
//		}
//		
//		return s;
//	}
//
//	//This should return only one candidate per group.
//	private List<String> processGroup(List<List<String>> group) {
//		int currentWord = 0;
//		List<List<String>> ws = new LinkedList<List<String>>();
//		int max = 0;
//		
//		for(List<String> rep: group){
//			if (max < rep.size())
//			  max = rep.size();
//			
//			ws.add(rep);
//		}
//		
//		while(currentWord < max || ws.size() > 1){
//			List<List<String>> remaining = new LinkedList<List<String>>();
//			
//			int index = findMaxIndex(ws, currentWord);
//			
//			String maxVal = ws.get(index).get(currentWord);
//			
//			int maxValNumber = checkIfNumber(maxVal);
//			
//			for (int i=index; i < ws.size(); i++){
//				List<String> currRep = ws.get(i);
//				
//				String currWord = currRep.get(currentWord);
//				int currNumber = checkIfNumber(currWord);
//				
//				if (currNumber != -1){
//					if (currNumber > maxValNumber){
//					   remaining.clear();
//					   remaining.add(currRep);
//					   
//					   maxVal = currWord;
//					   maxValNumber = currNumber;
//					} else if (currNumber == maxValNumber){
//						remaining.add(currRep);
//					}
//					
//				} else {
//					//TODO: Maybe improve this one.
//					if (maxValNumber == -1){
//						remaining.add(currRep);
//					}
//				}
//				
//				
//			}
//		
//			currentWord++;
//			
//			if (remaining.size() == 0){
//				remaining.add(ws.get(0));
//			}
//			
//			ws = remaining;
//		}
//		
//		return ws.get(0);
//	}
//
//	private int findMaxIndex(List<List<String>> ws, int currentWord) {
//		for(int i = 0; i < ws.size(); i++){
//			if (ws.get(i).size() >= currentWord + 1){
//				return i;
//			}
//		}
//		
//		return -1;
//	}
//
//	private int checkIfNumber(String currWord) {
//		int number = -1;
//		try {
//		  number = Integer.parseInt(currWord);
//		} catch(NumberFormatException ex){
//		}
//		
//		return number;
//	}
//	
}

class JarPair {
	private String link;
	private String name;
	
	public JarPair(String link, String name) {
		this.link = link;
		this.name = name;
	}

	public String toString(){
		return link+name;
	}
	
	public String getLink() {
		return link;
	}

	public String getName() {
		return name;
	}	
}

class JarTrie extends Trie<String, JarPair>{

	@Override
	public LinkedList<String> split(String key) {
		String[] splits = key.split("[-._](\\d)+[-._]");
		
		String prefix = splits[0];
		
		LinkedList<String> list = new LinkedList<String>();
		
		list.add(prefix);
		
		if (splits.length > 1){
		  splits = key.substring(prefix.length()+1).split("[-._]");
		  int length = splits.length - 1;
		  for(int i=0; i<length; i++){
			String split = splits[i];
			int number = checkIfNumber(split);
			if (number == -1) list.add(split);
			else list.add(Integer.toString(number));
		   }			
		}
		
		return list;
	}

	private void printGroup(TrieNode<String, JarPair> firstNode) {
		LinkedList<TrieNode<String, JarPair>> ws = new LinkedList<Trie.TrieNode<String,JarPair>>();
		ws.add(firstNode);
		
		while(!ws.isEmpty()){
			TrieNode<String, JarPair> node = ws.remove();
			
			HashMap<String, TrieNode<String, JarPair>> map = node.getMap();
			
			if (map.isEmpty()) node.print();
			else {
				ws.addAll(select(map));
			}
		}
	}

	private Collection<TrieNode<String, JarPair>> select(HashMap<String, TrieNode<String, JarPair>> map) {
		List<Integer> numbers = findNumbers(map.keySet());
		if(numbers.isEmpty()) return map.values();
		else {
			List<TrieNode<String, JarPair>> list = new LinkedList<TrieNode<String, JarPair>>();
			
			int max = findMax(numbers);
			
			TrieNode<String, JarPair> maxNode = map.get(Integer.toString(max));
			
			//System.out.print("MaxNode:  "+ max +"   ");
			//maxNode.print();
			
			list.add(maxNode);
			return list;
		}
	}

	private int findMax(List<Integer> numbers) {
		int max = numbers.get(0);
		int length = numbers.size();
		
		for (int i=1; i< length; i++){
		  int curr = numbers.get(i);
		  if (max < curr){
			  max = curr;
		  }
		}
		
		return max;
	}

	private List<Integer> findNumbers(Set<String> keySet) {
		List<Integer> list = new LinkedList<Integer>();
		
		for(String word: keySet){
			int number = checkIfNumber(word);
			if (number != -1){
				list.add(number);
			}
		}
		return list;
	}
	
	private int checkIfNumber(String currWord) {
		int number = -1;
		try {
		  number = Integer.parseInt(currWord);
		} catch(NumberFormatException ex){
		}
		
		return number;
	}	

	@Override
	public void print() {
		for(TrieNode<String, JarPair> group: root.getMap().values()){
			printGroup(group);
		}
	}
}
