package selection;

public class WordIndex {
	private String lemma;
	private int groupIndex;
	
	public WordIndex(String lemma) {
		this.lemma = lemma;
	}
	
	public WordIndex(String lemma, int groupIndex) {
		this.lemma = lemma;
		this.groupIndex = groupIndex;
	}
	
	public String getLemma() {
		return lemma;
	}
	public void setLemma(String lemma) {
		this.lemma = lemma;
	}
	public int getGroupIndex() {
		return groupIndex;
	}
	public void setGroupIndex(int groupIndex) {
		this.groupIndex = groupIndex;
	}

}
