package search;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import search.scorers.Score;
import nlp.parser.Token;
import definitions.Declaration;

public class RichDeclaration {
	private Declaration decl;
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
		this.tokens = new LinkedList<Token>();

		//First group
		addAll(decl.getSimpleNameTokens());

		//Second group
		addAll(decl.getReceiverTokens());
		addAll(decl.getArgTokens());
		addAll(decl.getClazzTokens());
		addAll(decl.getAdditionalReceiverTokens());
		addAll(decl.getReturnTypeTokens());
	}

	private void addAll(List<List<Token>> argTokens) {
		for (List<Token> list : argTokens) {
			addAll(list);
		}
	}

	public void addAll(Collection<Token> declTokens) {
		for (Token token : declTokens) {
			tryAdd(token);
		}
	}

	private boolean inTokens(Token thatToken) {
		for (Token token: this.tokens) {
			if (token.equalsByPosAndLemma(thatToken)) return true;
		}
		return false;
	}

	public void addAll(Token[] declTokens) {
		for (Token token : declTokens) {
			tryAdd(token);
		}	
	}

	private void tryAdd(Token token) {
		if (!inTokens(token)){
			this.tokens.add(token);
		}
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
		this.score = null;
		this.hit = false;
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
		return "score=" + score + ", decl=" + decl+"\n"+
			   "slots= "+slotsToString();
	}

	private String slotsToString() {
		StringBuffer sb = new StringBuffer();
		for (Slot[] slots: this.statistics.getSlots()) {
			for (Slot slot : slots) {
				sb.append(slot+" ");
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}

	public Declaration getDecl() {
		return this.decl;
	}
}
