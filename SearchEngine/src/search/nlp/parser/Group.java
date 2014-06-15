package search.nlp.parser;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import nlp.parser.Token;
import search.Local;
import search.WToken;
import util.UtilList;

public class Group {

	private Token token;
	private List<Group> graphDchildren;
	private List<Group> graphRchildren;
	private List<Token> tokenDecompositions;
	private Local local;
	private boolean literal;
	private Token literalTypeToken;

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
			tokens.addAll(child.getTokenOrDecompositionsOrLiteralType());
		}

		return tokens;
	}	

	private List<Token> getTokenOrDecompositionsOrLiteralType() {
		if (hasTokenDecompositions() && !isLiteral()){
			return this.tokenDecompositions;
		}
		return new LinkedList<Token>(){{
			if (Group.this.isLiteral()){
				add(Group.this.literalTypeToken);	
			} else {
				add(Group.this.token);
			}
		}};
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
				"\n, Search Keys = " + getSearchKeys() +
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
		return !(isLocal() || isLiteral() || isArticl());
	}

	public Set<WToken> getSearchKeys() {
		Set<WToken> set = new HashSet<WToken>();
		if (levels != null){
			set.addAll(UtilList.flatten(levels));
		}
		return set;
	}

	public void setLevels(List<List<WToken>> levels) {
		this.levels = levels;
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

	public Set<Token> getAllGraphTokens() {
		Set<Token> tokens = new HashSet<Token>();
		
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

	public List<List<WToken>> getLevels() {
		return this.levels;
	}

	public Token getLiteralTypeToken() {
		return literalTypeToken;
	}

	public void setLiteralTypeToken(Token literalTypeToken) {
		this.literalTypeToken = literalTypeToken;
	}

	public boolean isArticl(){
		return this.token.isArticle();
	}

	public boolean isSearchKeyOrLiteral() {
		return !(this.isLocal() || this.isArticl());
	}
}
