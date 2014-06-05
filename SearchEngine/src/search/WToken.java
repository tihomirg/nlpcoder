package search;

import nlp.parser.Token;

public class WToken {
	private Token token;
	private double score;
	private int index;
	
	public WToken(Token token) {
		this(token, 0);
	}
	
	public WToken(Token token, int index) {
		this(token, index, 0);
	}	
	
	public WToken(Token token, int index, double score) {
		this.token = token;
		this.index = index;
		this.score = score;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
	
	@Override
	public String toString() {
		return "WToken [token=" + token + ", score=" + score + ", index="+ index + "]";
	}

	public int getIndex() {
		return this.index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
