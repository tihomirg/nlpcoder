package search;

import nlp.parser.Token;

public class WToken {
	private Token token;
	private double score;
	
	public WToken(Token token, double score) {
		this.token = token;
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
	
	
}
