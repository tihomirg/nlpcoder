package tests;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.naming.directory.DirContext;

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
	
	public void print(PrintStream out){
		for(Entry<IWordID, Set<BigSet>> entry: map.entrySet()){	
			IWord word2 = dict.getWord(entry.getKey());
			out.print("["+word2.getLemma()+" "+word2.getLexicalID()+"] ");
			for(BigSet set: entry.getValue()){
				Set<ISynsetID> synsets = set.getSynsets();
				for(ISynsetID synset: synsets){
					ISynset synset2 = dict.getSynset(synset);
					out.print("[");
					for(IWord word:synset2.getWords()){
						out.print(" "+word.getLemma()+" "+word.getLexicalID());
					}
					out.print("] ");
				}
				//out.print(Arrays.toString(set.getSynsets().toArray()));
			}
			out.println();
		}
	}
	
}
