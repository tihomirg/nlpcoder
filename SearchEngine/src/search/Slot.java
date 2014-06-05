package search;

import java.util.LinkedList;
import java.util.List;

import nlp.parser.Token;

public class Slot {
	private WToken wtoken;
	private List<Token> tokens;
	private int[] indexScores;

	public Slot(int[] indexScores) {
		this.indexScores = indexScores;
		this.tokens = new LinkedList<Token>();
	}
	
	public boolean fits(WToken wtoken){
		Token thatToken = wtoken.getToken();
		
		for (Token token : tokens) {
			if (token.equalsByPosAndLemma(thatToken)) return true;
		}
		
		return false;
	}

	public void setWToken(WToken token) {
		this.wtoken = token;
	}
	
	public WToken getWToken() {
		return wtoken;
	}

	public boolean isOccupied(){
		return this.wtoken != null;
	}
	
	public double getScore(){
		if(!isOccupied()) return 0.0;
		else return getScore(this.wtoken);
	}
	
	public double getScore(WToken wtoken){
		return wtoken.getScore() * 	indexScores[wtoken.getIndex()];
	}

	public WToken substitute(WToken wtoken) {
		WToken temp = this.wtoken;
		this.wtoken = wtoken;
		return temp;
	}

	public void clear() {
		this.wtoken = null;
	}

	public int isOccupiedToInt() {
		return isOccupied() ? 1 : 0;
	}

	public void addToken(Token token) {
		this.tokens.add(token);
	}

	@Override
	public String toString() {
		return "Slot [score = "+getScore()+", wtoken=" + wtoken + "]";
	}
}
