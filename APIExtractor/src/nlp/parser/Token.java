package nlp.parser;

public class Token {

	private String text;
	private String lemma;
	private String pos;
	private int index;
	
	public Token(){}
	
	public Token(String text, String lemma, String pos, int index) {
		this.text = text;
		this.lemma = lemma.toLowerCase();
		this.pos = Character.isLetter(pos.charAt(0)) ? Character.toString(pos.charAt(0)) : pos;
		this.index = getIndex(index);
	}

	public void setLemma(String word) {
		this.lemma = word;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	@Override
	public String toString() {
		return "Token [text = "+text+" ,lemma = " + lemma + ", pos = " + pos+"]";
	}

	public String getLemma() {
		return this.lemma;
	}
	
	public int getIndex() {
		return index;
	}

	private int getIndex(int index) {
		return index - 1;
	}

	public boolean isVerb() {
		return this.pos.startsWith("V");
	}
	
	public boolean isArticle() {
		return this.pos.equals("DT") && (this.lemma.equals("a") || this.lemma.equals("A") || this.lemma.equals("an") || this.lemma.equals("An") || this.lemma.equals("the") || this.lemma.equals("The"));
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
	
	public String getPos() {
		return pos;
	}
	
	public String getText() {
		return text;
	}

	public boolean equalsByPosAndLemma(Token thatToken) {
		return this.pos.equals(thatToken.pos) && this.lemma.equals(thatToken.lemma);
	}
}
