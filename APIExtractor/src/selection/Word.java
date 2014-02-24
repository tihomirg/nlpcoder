package selection;

import edu.mit.jwi.item.POS;

public class Word extends WordIndex {

	private int wordIndex;
	private double value;
	private POS pos;
	
	public POS getPos() {
		return pos;
	}

	public Word(String lemma, POS pos) {
		this(lemma, pos, 0, 0);
	}	
	
	public Word(String lemma, POS pos, int wordIndex) {
		this(lemma, pos, wordIndex, 0, 0.0);
	}
	
	public Word(String lemma, POS pos, int wordIndex, int groupIndex){
		this(lemma, pos, wordIndex, groupIndex, 0.0);
	}	
	
	public Word(String lemma, POS pos, int wordIndex, int groupIndex, double value) {
		super(lemma, groupIndex);
		this.pos = pos;
		this.wordIndex = wordIndex;
		this.value = value;
	}
	
	public int getWordIndex() {
		return wordIndex;
	}

	public void setWordIndex(int wordIndex) {
		this.wordIndex = wordIndex;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Word [lemma=" + getLemma() + ", pos=" + pos + ", groupIndex="
				+ getGroupIndex() + ", wordIndex=" + wordIndex + ", value="
				+ value + "]";
	}

}
