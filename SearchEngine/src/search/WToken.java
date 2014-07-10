package search;

import nlp.parser.Token;

public class WToken {
	private Token token;
	private double score;
	
	//whether it belongs to a leading word or not.
	private int importanceIndex;
	
	//An index of subgroup in a group of words.
	private int subgroupIndex;
	
	public WToken(Token token) {
		this(token, 0);
	}
	
	public WToken(Token token, int index) {
		this(token, index, 0);
	}	
	
	public WToken(Token token, int index, double score) {
		this.token = token;
		this.importanceIndex = index;
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
		return "WToken [token=" + token + ", score=" + score + ", index="+ importanceIndex + "]";
	}

	public int getImportanceIndex() {
		return this.importanceIndex;
	}

	public void setImportanceIndex(int index) {
		this.importanceIndex = index;
	}
	
	public int getSubgroupIndex() {
		return subgroupIndex;
	}
	
	public void setSubgroupIndex(int subgroupIndex) {
		this.subgroupIndex = subgroupIndex;
	}
}
