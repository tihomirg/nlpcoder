package search;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import search.scorers.Score;
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
	
	public RichDeclaration(Declaration decl, double declProb, ScorerPipeline scorer, ScoreListener listener, int[][] indexScores) {
		this.decl = decl;
		this.scorer = scorer;
		this.listener = listener;
		this.statistics = new RichDeclarationStatistics(decl, declProb, indexScores);
		this.posToLemmaToToken = new HashMap<String, Map<String,List<Token>>>();
		this.tokens = new LinkedList<Token>();
		
		//First group
		addAll(decl.getSimpleNameTokens());
		
		//Second group
		addAll(decl.getReceiverTokens());
		addAll(decl.getArgTokens());
		addAll(decl.getClazzTokens());
		addAll(decl.getAdditionalReceiverTokens());
	}

	private void addAll(List<List<Token>> argTokens) {
		for (List<Token> list : argTokens) {
			addAll(list);
		}
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
		addToTokens(token);
	}

	private void addToTokens(Token token) {
		this.tokens.add(token);
	}
	
	public void hit(WToken wtoken) {
		statistics.hit(wtoken);
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
		this.statistics.clear();
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

	@Override
	public String toString() {
		return "score=" + score + ", decl=" + decl;
	}
}
