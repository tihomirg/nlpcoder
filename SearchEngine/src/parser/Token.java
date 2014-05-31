package parser;

import core.Local;
import edu.stanford.nlp.ling.CoreLabel;

public class Token {

	private CoreLabel token;
	private String lemma;
	private String pos;

	public Token(CoreLabel token) {
		this.token = token;
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
		return getIndex(this.token.index());
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
