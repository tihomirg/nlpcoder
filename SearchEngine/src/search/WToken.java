package search;

import nlp.parser.Token;

public class WToken {
	private Token token;

	//whether it belongs primary or a secondary word.
	private int importanceIndex;
	
	//An index of subgroup in a group of words.
	private int subgroupIndex;
	private double importanceWeight;
	private double relatednessWeight;
	
	public WToken(Token token){
		this(token, 0);
	}
	
	public WToken(Token token, int importanceIndex) {
		this(token, importanceIndex, 0.0);
	}	
	
	public WToken(Token token, int importanceIndex, double importanceWeight) {
		this.token = token;
		this.importanceIndex = importanceIndex;
		this.importanceWeight = importanceWeight;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "WToken [token=" + token
				+ ", importanceIndex=" + importanceIndex 
				+ ", subgroupIndex="+ subgroupIndex + "]";
	}

	public int getImportanceIndex() {
		return this.importanceIndex;
	}

	public void setImportanceIndex(int index) {
		this.importanceIndex = index;
	}
	
	public void setImportanceWeight(double importanceWeight) {
		this.importanceWeight = importanceWeight;
	}
	
	public int getSubgroupIndex() {
		return subgroupIndex;
	}
	
	public void setSubgroupIndex(int subgroupIndex) {
		this.subgroupIndex = subgroupIndex;
	}
	
	public boolean equalsByPosAndLemma(WToken thatWToken){
		return this.token.equalsByPosAndLemma(thatWToken.token);
	}

	public double getImportanceWeight() {
		return importanceWeight;
	}

	public double getRelatednessWeight() {
		return relatednessWeight;
	}
}
