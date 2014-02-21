package selection;

public class Word extends WordIndex {

	private int wordIndex;
	private double value;
	
	public Word(String lemma) {
		super(lemma);
	}
	
	public Word(String lemma, int groupIndex) {
		super(lemma, groupIndex);
	}
	
	public Word(String lemma, int wordIndex, int groupIndex){
		this(lemma, groupIndex);
		this.wordIndex = wordIndex;
	}	
	
	public Word(String lemma, int wordIndex, int groupIndex, double value) {
		this(lemma, groupIndex);
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

}
