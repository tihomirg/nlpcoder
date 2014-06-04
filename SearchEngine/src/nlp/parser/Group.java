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
		return this.wtokens;
	}
	
	public void setWtokens(List<WToken> wtokens) {
		this.wtokens = wtokens;
	}
}
