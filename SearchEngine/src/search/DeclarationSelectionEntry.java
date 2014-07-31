package search;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import search.scorers.Score;
import nlp.parser.Token;
import definitions.Declaration;

public class DeclarationSelectionEntry implements Cloneable {
	private Declaration decl;
	private SelectListener listener;
	private boolean hit;
	private double unigramScore;
	private List<WToken> primaryWTokens;
	private List<WToken> secondaryWTokens;
	private LinkedList<Token> tokens;

	public DeclarationSelectionEntry(Declaration decl, double unigramScore, SelectListener listener, int primaryIndex, double initialPrimaryWeight, int secondaryIndex, double initialSecondaryWeight) {
		this.decl = decl;
		this.listener = listener;
		this.tokens = new LinkedList<Token>();
		this.primaryWTokens = new LinkedList<WToken>();
		this.secondaryWTokens = new LinkedList<WToken>();
		this.unigramScore  = unigramScore;
		
		//First group
		addAllToPrimary(decl.getSimpleNameTokens());

		//Second group
		addAllToSecondary(decl.getReceiverTokens());
		addAllToSecondary(decl.getArgTokens());
		addAllToSecondary(decl.getClazzTokens());
		addAllToSecondary(decl.getAdditionalReceiverTokens());
		addAllToSecondary(decl.getReturnTypeTokens());
		
		//Setting additional parameters
		setIndexesAndWeights(primaryIndex, initialPrimaryWeight, secondaryIndex, initialSecondaryWeight);
	}

	private void setIndexesAndWeights(int primaryIndex, double initialPrimaryWeight, int secondaryIndex, double initialSecondaryWeight) {
		int primarySize = this.primaryWTokens.size();
		int secondarySize = this.secondaryWTokens.size();
		
		double initialWeight = initialPrimaryWeight + initialSecondaryWeight;
		double primaryWeight = initialPrimaryWeight / (initialWeight * primarySize);
		double secondaryWeight = initialSecondaryWeight / (initialWeight * secondarySize);
		
		setIndexAndWeight(this.primaryWTokens, primaryIndex, primaryWeight);
		setIndexAndWeight(secondaryWTokens, secondaryIndex, secondaryWeight);
	}

	private void setIndexAndWeight(List<WToken> wTokens, int importanceIndex, double weight) {		
		for (WToken wToken : wTokens) {
			wToken.setImportanceIndex(importanceIndex);
			wToken.setImportanceWeight(weight);
		}
	}

	private void addAllToPrimary(List<List<Token>> argTokens) {
		for (List<Token> list : argTokens) {
			addAllToPrimary(list);
		}
	}	

	private void addAllToSecondary(List<List<Token>> argTokens) {
		for (List<Token> list : argTokens) {
			addAllToSecondary(list);
		}
	}

	public void addAllToPrimary(Collection<Token> declTokens) {
		for (Token token : declTokens) {
			addWToken(new WToken(token));
			addToken(token);
		}
	}

	public void addAllToSecondary(Collection<Token> declTokens) {
		for (Token token : declTokens) {
			addWToken(new WToken(token));
			addToken(token);
		}
	}

	private void addToken(Token token) {
		this.tokens.add(token);
	}

	private void addWToken(WToken wToken) {
		this.primaryWTokens.add(wToken);
	}

	public void hit() {
		if (!hit){
			notifyListener();
			this.hit = true;
		}
	}
	
	public LinkedList<Token> getTokens() {
		return tokens;
	}

	private void notifyListener() {
		listener.notify(this);
	}

	public boolean isHit() {
		return hit;
	}

	public void setHit(boolean hit) {
		this.hit = hit;
	}

	@Override
	public String toString() {
		return decl.toString();
	}
	
	public Declaration getDecl() {
		return this.decl;
	}

	public void clear() {
		this.hit = false;
	}
	
	public double getUnigramScore() {
		return unigramScore;
	}

	public List<WToken> getWTokens() {
		return this.primaryWTokens;
	}
}
