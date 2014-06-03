package selection;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import nlp.parser.Token;

public class RichDeclarationStatistics {
	private double declProb;
	private Map<Token, PriorityQueue<WToken>> hits;
	private List<Token> missed;
	
	public RichDeclarationStatistics(double declProb) {
		this.declProb = declProb;
		this.hits = new HashMap<Token, PriorityQueue<WToken>>();
		this.missed = new LinkedList<Token>();
	}

	public void addToMissed(List<Token> tokens){
		for (Token token : tokens) {
			addToMissed(token);
		}
	}
	
	public void addToMissed(Token token){
		if (!missed.contains(token)){
			missed.add(token);
		}
	}
	
	public void removeFromMissed(List<Token> hitTokens) {
		for (Token token : hitTokens) {
			missed.remove(token);
		}
	}	

	public void hit(WToken wtoken, List<Token> hitTokens) {
		for (Token token : hitTokens) {
			addToHits(token, wtoken);
		}
	}

	private void addToHits(Token token, WToken wtoken) {
		PriorityQueue<WToken> pq = null;
		if (!hits.containsKey(token)){
			pq = new PriorityQueue<WToken>();
			hits.put(token, pq);
		} else {
			pq = hits.get(token);
		}
		pq.add(wtoken);
	}
	
	public void clear(List<Token> allTokens) {
		hits.clear();
		missed.clear();
		missed.addAll(allTokens);
	}
	
	public Map<Token, PriorityQueue<WToken>> getHits() {
		return hits;
	}
}
