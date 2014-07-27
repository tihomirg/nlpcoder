package search;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import search.scorers.Score;
import nlp.parser.Token;
import definitions.Declaration;

public class DeclarationSelectionEntry implements Cloneable {
	private static final int PrimaryIndex = 0;
	private static final int SecondaryIndex = 1;
	private Declaration decl;
	private SelectListener listener;
	private boolean hit;
	private double unigramScore;
	private List<WToken> wtokens;
	private LinkedList<Token> tokens;

	public DeclarationSelectionEntry(Declaration decl, double unigramScore, SelectListener listener) {
		this.decl = decl;
		this.listener = listener;
		this.tokens = new LinkedList<Token>();
		this.wtokens = new LinkedList<WToken>();
		this.unigramScore  = unigramScore;
		
		//First group
		addAllToPrimary(decl.getSimpleNameTokens());

		//Second group
		addAllToSecondary(decl.getReceiverTokens());
		addAllToSecondary(decl.getArgTokens());
		addAllToSecondary(decl.getClazzTokens());
		addAllToSecondary(decl.getAdditionalReceiverTokens());
		addAllToSecondary(decl.getReturnTypeTokens());
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
			addWToken(new WToken(token, PrimaryIndex));
			addToken(token);
		}
	}

	public void addAllToSecondary(Collection<Token> declTokens) {
		for (Token token : declTokens) {
			addWToken(new WToken(token, SecondaryIndex));
			addToken(token);
		}
	}

	private void addToken(Token token) {
		this.tokens.add(token);
	}

	private void addWToken(WToken wToken) {
		this.wtokens.add(wToken);
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
		return this.wtokens;
	}
}
