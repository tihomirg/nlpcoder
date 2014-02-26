package selection.parser.one;

import java.util.Arrays;

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

	@Override
	public String toString() {
		return "ConstituentOne [index=" + index + ", words="
				+ Arrays.toString(words) + "]\n";
	}

}
