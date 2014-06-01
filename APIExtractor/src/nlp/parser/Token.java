package nlp.parser;

public class Token {

	private String lemma;
	private String pos;
	private int index;
	
	public Token(){}
	
	public Token(String lemma, String pos, int index) {
		this.lemma = lemma;
		this.pos = pos;
		this.index = index;
	}

	public void setLemma(String word) {
		this.lemma = word;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	@Override
	public String toString() {
		return "Token [lemma = " + lemma + ", pos = " + pos+"]";
	}

	public String getLemma() {
		return this.lemma;
	}
	
	public int getIndex() {
		return getIndex(index);
	}

	private int getIndex(int index) {
		return index - 1;
	}

	public boolean isVerb() {
		return this.pos.startsWith("V");
	}
	
	public boolean isArticle() {
		return this.pos.equals("DT") && (this.lemma.equals("a") || this.lemma.equals("A") || this.lemma.equals("the") || this.lemma.equals("The"));
	}
	
	public boolean shouldSkipAsNeighbour(){
		return isVerb() || isArticle();
	}

	public boolean isBeginingOfString() {
		return this.pos.equals("``");
	}

	public boolean isEndOfString() {
		return this.pos.equals("''");
	}
}
