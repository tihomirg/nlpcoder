package selection;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import selection.scorers.Score;
import nlp.parser.Token;
import definitions.Declaration;

public class RichDeclaration {
	private Declaration decl;
	private Map<String, Map<String, List<Token>>> posToLemmaToToken;
	private List<Token> tokens;
	private RichDeclarationStatistics statistics;
	private ScoreListener listener;
	private ScorerPipeline scorer;
	private Score score;
	
	private boolean hit;
	
	public RichDeclaration(Declaration decl, double declProb, ScorerPipeline scorer, ScoreListener listener) {
		this.decl = decl;
		this.scorer = scorer;
		this.listener = listener;
		this.statistics = new RichDeclarationStatistics(declProb);
		this.posToLemmaToToken = new HashMap<String, Map<String,List<Token>>>();
		this.tokens = new LinkedList<Token>();
		addAll(decl.getSimpleNameTokens());
		addAll(decl.getReceiverTokens());
		addAll(decl.getRemainderTokens());
		addAll(decl.getAdditionalReceiverTokens());
	}

	public void addAll(Collection<Token> declTokens) {
		for (Token token : declTokens) {
			add(token);
		}
	}
	
	public void addAll(Token[] declTokens) {
		for (Token token : declTokens) {
			add(token);
		}	
	}
	
	public void add(Token token){
		addToMap(token);
		addToTokens(token);
		statistics.addToMissed(token);
	}

	private void addToTokens(Token token) {
		this.tokens.add(token);
	}

	private void addToMap(Token token) {
		String pos = token.getPos();
		if (!this.posToLemmaToToken.containsKey(pos)){
			this.posToLemmaToToken.put(pos, new HashMap<String, List<Token>>());
		}
		
		Map<String, List<Token>> lemmaToToken = this.posToLemmaToToken.get(pos);
		
		String lemma = token.getLemma();
		if(lemmaToToken.containsKey(lemma)){
			lemmaToToken.put(lemma, new LinkedList<Token>());
		}
		
		lemmaToToken.get(lemma).add(token);
	}
	
	public void hit(String pos, String word, WToken wtoken) {
		statistics.hit(wtoken, posToLemmaToToken.get(pos).get(word));
		calculateScore();
		notifyListener();
	}

	private void calculateScore() {
		this.score = scorer.calculate(statistics);
	}

	private void notifyListener() {
		listener.notify(this);
	}
	
	public void clear(){
		this.statistics.clear(this.tokens);
	}
	
	public boolean isHit() {
		return hit;
	}
	
	public Score getScore() {
		return score;
	}
	
	public List<Token> getTokens() {
		return tokens;
	}

	public void setHit(boolean hit) {
		this.hit = hit;
	}
}
