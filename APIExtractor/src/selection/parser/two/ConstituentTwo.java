package selection.parser.two;

import java.util.ArrayList;
import java.util.List;

import selection.parser.one.Word;

public class ConstituentTwo {
	private Wordset[] wordsets;
	private int firstImporatantIndex;
	private int lastImportantIndex;
	
	public ConstituentTwo(Wordset[] wordsets, int firstImporatantIndex, int lastImportantIndex) {
		this.wordsets = wordsets;
		this.firstImporatantIndex = firstImporatantIndex;
		this.lastImportantIndex = lastImportantIndex;
	}

	public Wordset[] getWordsets() {
		return wordsets;
	}
	public void setWordsets(Wordset[] wordsets) {
		this.wordsets = wordsets;
	}
	public int getFirstImporatantIndex() {
		return firstImporatantIndex;
	}
	public void setFirstImporatantIndex(int firstImporatantIndex) {
		this.firstImporatantIndex = firstImporatantIndex;
	}
	public int getLastImportantIndex() {
		return lastImportantIndex;
	}
	public void setLastImportantIndex(int lastImportantIndex) {
		this.lastImportantIndex = lastImportantIndex;
	}
	
	public List<Word> getWords(){
	   List<Word> words = new ArrayList<Word>();
	   
	   for(Wordset wordset: wordsets){
		   words.addAll(wordset.getWords());
	   }
	   
	   return words;
	}
}
