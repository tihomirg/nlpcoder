package tests;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.SynsetID;

public class BigSetMap {

	private Map<IWordID, Set<BigSet>> map;
	
	private IDictionary dict; 
	
	public BigSetMap(IDictionary dict){
		this.map = new HashMap<IWordID, Set<BigSet>>();
		this.dict = dict;
	}
	
	public void add(BigSet set){
	   	for(ISynsetID id: set.getSynsets()){
	   		ISynset synset = dict.getSynset(id);
	   		List<IWord> words = synset.getWords();
	   		for(IWord word: words){
	   			IWordID wid = word.getID();
	   			add(wid, set);
	   		}
	   		
	   	}
	}

	private void add(IWordID wid, BigSet set) {
		Set<BigSet> setb = map.get(wid);
		if (setb == null) {
			setb = new HashSet<BigSet>();
			map.put(wid, setb);
		}
		setb.add(set);
	}
	
}
