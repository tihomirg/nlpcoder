package selection.parser.one;

import java.util.Arrays;


//TODO: remove words, since we do not need them in this phase
// but propagate the info on the smallest and the largest indexes 
public class ConstituentOne {

	private int index;
	private Word[] words;

	public ConstituentOne(Word[] words, int index) {
		this.index = index;
		this.words = words;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Word[] getWords() {
		return words;
	}

	public void setWords(Word[] words) {
		this.words = words;
	}
	
	public int smallestIndex(){
		return words[0].getIndex();
	}
	
	public int largestIndex(){
		return words[words.length-1].getIndex();
	}

	@Override
	public String toString() {
		return "ConstituentOne [index=" + index + ", words="
				+ Arrays.toString(words) + "]\n";
	}

}
