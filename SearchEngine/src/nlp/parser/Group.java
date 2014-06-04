package nlp.parser;

import java.util.LinkedList;
import java.util.List;

import search.WToken;
import core.Local;

public class Group {

	private Token token;
	private List<Group> graphDchildren;
	private List<Group> graphRchildren;
	private List<Token> tokenDecompositions;
	private Local local;
	private boolean literal;
	private List<WToken> wtokens;
	
	private List<List<WToken>> levels;
	private List<Token> tokenRelatedTokens;
	private List<Token> allGraphTokenRelatedTokens;

	public Group(Token token) {
		this.token = token;
	}

	public void addGraphDChildren(List<Group> graphDchildren) {
		this.graphDchildren = graphDchildren;
	}

	public List<Token> graphDTokens(){
		return graphTokens(this.graphDchildren);
	}
	
	public List<Token> graphRTokens(){
		return graphTokens(this.graphRchildren);
	}

	private List<Token> graphTokens(List<Group> children) {
		List<Token> tokens = new LinkedList<Token>();
		
		for (Group child : children) {
			tokens.add(child.getToken());
		}
		
		return tokens;
	}	
	
	public Token getToken() {
		return this.token;
	}

	@Override
	public String toString() {
		return "Group [token= " + token + " decompositions"+ this.tokenDecompositions +
				"\n, Local = "+ local +
				"\n, Literal = "+ literal +				
				"\n, D-Tokens = " + graphDTokens() + 
				"\n, R-Tokens = " + graphRTokens() + 
				"]\n\n";
	}

	public void addRightHandSideNeighbours(List<Group> graphRchildren) {
		this.graphRchildren = graphRchildren;
	}

	public void setTokenDecompositions(List<Token> tokenDecompositions) {
		this.tokenDecompositions = tokenDecompositions;
	}

	public void setLocal(Local local) {
		this.local = local;
	}
	
	public boolean isLocal() {
		return this.local != null;
	}
	
	public void setLiteral(boolean literal) {
		this.literal = literal;
	}
	
	public boolean isLiteral() {
		return literal;
	}

	public boolean isSearchKey() {
		return !isLocal() && !isLiteral();
	}

	public List<WToken> getSearchKeys() {
		return flatten(levels);
	}
	
	public void setLevels(List<List<WToken>> levels) {
		this.levels = levels;
	}

	private <T> List<T> flatten(List<List<T>> lists) {
		List<T> rlist = new LinkedList<T>();
		
		for (List<T> list : lists) {
			rlist.addAll(list);
		}
		
		return rlist;
	}

	public boolean shouldSkipAsNeighbour() {
		return token.shouldSkipAsNeighbour();
	}

	public boolean hasTokenDecompositions() {
		return !this.tokenDecompositions.isEmpty();
	}
	
	public List<Token> getTokenDecompositions() {
		return tokenDecompositions;
	}

	public List<Token> getTokenRelatedTokens() {
		return this.tokenRelatedTokens;
	}
	
	public void setTokenRelatedTokens(List<Token> tokenRelatedTokens) {
		this.tokenRelatedTokens = tokenRelatedTokens;
	}

	public List<Token> getAllGraphTokens() {
		List<Token> tokens = new LinkedList<Token>();
		
		tokens.addAll(graphDTokens());
		tokens.addAll(graphRTokens());
		
		return tokens;
	}

	public List<Token> getAllGraphTokenRelatedTokens() {
		return this.allGraphTokenRelatedTokens;
	}
	
	public void setAllGraphTokenRelatedTokens(List<Token> allGraphTokenRelatedTokens) {
		this.allGraphTokenRelatedTokens = allGraphTokenRelatedTokens;
	}
}
