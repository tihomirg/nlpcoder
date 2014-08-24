package nlp.parser;

import edu.mit.jwi.item.POS;

public class Token {

	private String text;
	private String lemma;
	private String pos;
	
	public Token(){}
	
	public Token(String text, String lemma, String pos) {
		this.text = text;
		this.lemma = lemma.toLowerCase();
		this.pos = Character.isLetter(pos.charAt(0)) ? Character.toString(pos.charAt(0)) : pos;
	}

	public void setLemma(String lemma) {
		this.lemma = lemma;
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

	public boolean isVerb() {
		return this.pos.startsWith("V");
	}
	
	public boolean isArticle() {
		return this.pos.equals("D") && (this.lemma.equals("a") || this.lemma.equals("A") || this.lemma.equals("an") || this.lemma.equals("An") || this.lemma.equals("the") || this.lemma.equals("The"));
	}
	
	public boolean shouldSkipAsNeighbour(){
		return isVerb() || isArticle();
	}
	
	public boolean isGoodNeighbour() {
		return this.pos.equals("N") || this.pos.equals("J") || this.pos.equals("R");
	}
	
	public boolean isNormal(){
		return this.pos.equals("V") || isGoodNeighbour();
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
	
	public POS toWordNetPos(){
		if(pos.equals("N")){
			return POS.NOUN;
		} else if(pos.equals("V")){
			return POS.VERB;
		} else if(pos.equals("J")) {
			return POS.ADJECTIVE;
		} else if (pos.equals("R")){
			return POS.ADVERB;
		} else {
			return POS.NOUN;
		}
	}

	public boolean equalsByPosAndLemma(Token thatToken) {
		return this.pos.equals(thatToken.pos) && this.lemma.equals(thatToken.lemma);
	}
	
	public boolean equalsByLemma(Token thatToken) {
		return this.lemma.equals(thatToken.lemma);
	}
	
}
