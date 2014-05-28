package parser;

import edu.stanford.nlp.ling.CoreLabel;

public class Token {

	private CoreLabel token;
	private String word;
	private String pos;

	public Token(CoreLabel token) {
		this.token = token;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

}
