package selection.trees;

import selection.shares.IShare;
import edu.mit.jwi.item.POS;

public class Word extends WordKey {
	
	private int wordIndex;
	
	public Word(String lemma, POS pos, int wordIndex){
		this(lemma, pos, wordIndex, null);
	}
	
	public Word(String lemma, POS pos, int wordIndex, IShare share){
		this(lemma, pos, wordIndex, share, 0);
	}
	
	public Word(String lemma, POS pos, int wordIndex, IShare share, int groupIndex) {
		super(lemma, pos, share, groupIndex);
		this.wordIndex = wordIndex;
	}
	
	public int getWordIndex() {
		return wordIndex;
	}

	public void setWordIndex(int wordIndex) {
		this.wordIndex = wordIndex;
	}

	@Override
	public String toString() {
		return "Word [lemma=" + getLemma() + ", pos=" + getPos()
				+ ", prob=" + getProbability() + "]";
	}
}
