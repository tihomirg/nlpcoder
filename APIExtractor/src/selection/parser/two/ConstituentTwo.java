package selection.parser.two;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import selection.parser.one.Word;

public class ConstituentTwo {
	private int index;
	private Wordset[] wordsets;
	private int firstImporatantIndex;
	private int lastImportantIndex;

	public ConstituentTwo(Wordset[] wordsets, int index, int firstImporatantIndex, int lastImportantIndex) {
		this.wordsets = wordsets;
		this.index = index;
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

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "ConstituentTwo [index=" + index + ", firstImporatantIndex="
				+ firstImporatantIndex + ", lastImportantIndex="
				+ lastImportantIndex + ", wordsets="
				+ Arrays.toString(wordsets) + "]\n";
	}
}
