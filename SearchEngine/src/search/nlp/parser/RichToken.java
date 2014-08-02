package search.nlp.parser;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import search.WToken;
import util.UtilList;
import api.Local;
import nlp.parser.Token;

public class RichToken {

	private Token originalToken;
	private List<Token> decompositions;

	private int index;
	private int beginPosition;
	private int endPosition;
	
	private Local local;
	private String stringLiteral;
	private String numberLiteral;
	private String booleanLiteral;
	
	private List<RichToken> semanticNeighbours;
	private List<RichToken> rightHandSideNeighbours;
	
	private List<List<WToken>> relatedWTokens;
	private List<WToken> leadingWTokens;
	private List<WToken> secondaryWTokens;
	private List<DisjointSubgroups> disjointSubgroups;
	
	public RichToken(Token originalToken, int index, int beginPosition, int endPosition) {
		this.originalToken = originalToken;
		this.index = index;
		this.beginPosition = beginPosition;
		this.endPosition = endPosition;
	}

	public List<Token> getDecompositions() {
		return decompositions;
	}

	public boolean hasDecompositions() {
		return !decompositions.isEmpty();
	}

	public Token getOriginalToken() {
		return originalToken;
	}

	public int getBeginPosition() {
		return beginPosition;
	}

	public int getEndPosition() {
		return endPosition;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public boolean isLocal() {
		return this.local != null;
	}
	
	public Local getLocal() {
		return local;
	}

	public void setStringLiteral(String string) {
		this.stringLiteral = string;
	}

	public boolean isStringLiteral(){
		return this.stringLiteral != null;
	}
	
	public String getStringLiteral() {
		return stringLiteral;
	}

	public void setNumberLiteral(String number) {
		this.numberLiteral = number;
	}

	public boolean isNumberLiteral(){
		return this.numberLiteral != null;
	}
	
	public String getNumberLiteral() {
		return numberLiteral;
	}

	public void setBooleanLiteral(String bool) {
		this.booleanLiteral = bool;
	}

	public boolean isBooleanLiteral(){
		return this.booleanLiteral != null;
	}
	
	public String getBooleanLiteral() {
		return booleanLiteral;
	}

	public boolean isLiteral(){
		return isStringLiteral() || isNumberLiteral() || isBooleanLiteral();
	}

	public boolean isSpecial(){
		return isLiteral() || isLocal();
	}

	//Means they are good to be a leading word
	public boolean isNormal() {
		return !isSpecial() && originalToken.isNormal();
	}

	public int getIndex() {
		return index;
	}

	public void setSemanticNeighbours(List<RichToken> semanticNeighbours) {
		this.semanticNeighbours = semanticNeighbours;
	}

	public boolean isGoodNeighbour(){
		return this.originalToken.isGoodNeighbour();
	}

	public void setRightHandSideNeighbours(List<RichToken> rightHandSideNeighbours) {
		this.rightHandSideNeighbours = rightHandSideNeighbours;
	}

	public void setTokenDecompositions(List<Token> decompositions) {
		this.decompositions = decompositions;
	}

	private String semanticNeighboursToString(){
		String s = "";
		for (RichToken rt : this.semanticNeighbours) {
			s+= rt.getOriginalToken()+" ";
		}

		return s;
	}

	private String rightHandSideNeighboursToString(){
		String s = "";
		for (RichToken rt : this.rightHandSideNeighbours) {
			s+= rt.getOriginalToken()+" ";
		}

		return s;
	}

	public List<Token> getLeadingTokens() {
		if(hasDecompositions()) return this.decompositions;
		else return new LinkedList<Token>(){{add(RichToken.this.originalToken);}}; 
	}
	
	public void setRelatedWTokens(List<List<WToken>> relatedWTokens) {
		this.relatedWTokens = relatedWTokens;
	}
	
	public List<List<WToken>> getRelatedWTokens() {
		return relatedWTokens;
	}
	
	public List<WToken> getLeadingWTokens() {
		return leadingWTokens;
	}
	
	public void setLeadingWTokens(List<WToken> leadingWTokens) {
		this.leadingWTokens = leadingWTokens;
	}
	
	@Override
	public String toString() {
		return "RichToken ["
				+ "\noriginalToken=" + originalToken
				+ "\ndecompositions="+ decompositions
				+ "\nbeginPosition="+ beginPosition 
				+ "\nendPosition=" + endPosition
				+ "\nlocal="+ local
				+ "\nstringLiteral=" + stringLiteral
				+ "\nnumberLiteral=" + numberLiteral
				+ "\nbooleanLiteral="+ booleanLiteral 
				+ "\nindex=" + index
				+ "\nsemanticNeighbours="+ semanticNeighboursToString()
				+ "\nrightHandSideNeighbours=" + rightHandSideNeighboursToString() 
				+"\nleadingTokens="+ this.leadingWTokens
				+"\nrelatedTokens="+ this.relatedWTokens
				+"\nsecondaryTokens="+ this.secondaryWTokens 
				+"]\n";
	}
	
	public String tokensToString(){
		return "RichToken [" 
				+"\nleadingTokens="+ this.leadingWTokens
				+"\nrelatedTokens="+ this.relatedWTokens
				+"\nsecondaryTokens="+ this.secondaryWTokens 
				+"]";		
	}

	public List<WToken> getSecondaryWTokens() {
		return secondaryWTokens;
	}

	public void setSecondaryWTokens(List<WToken> secondaryWTokens) {
		this.secondaryWTokens = secondaryWTokens;
	}
	
	public List<Token> getSecondaryTokens(){
		Set<RichToken> rTokens = new HashSet<RichToken>();
		rTokens.addAll(this.semanticNeighbours);
		rTokens.addAll(this.rightHandSideNeighbours);
		
		List<Token> tokens = new LinkedList<Token>();
		
		for (RichToken rToken : rTokens) {
			tokens.addAll(rToken.getLeadingTokens());
		}
		
		return tokens;
	}
	
	public List<WToken> getAllTokens(){
		return UtilList.merge(UtilList.merge(this.leadingWTokens, UtilList.flatten(this.relatedWTokens)), this.secondaryWTokens);
	}

	//TODO: Implement this phase of parsing
	public List<DisjointSubgroups> getDisjointSubgroups() {
		return this.disjointSubgroups;
	}
	
	public void setDisjointSubgroups(List<DisjointSubgroups> disjointSubgroups) {
		this.disjointSubgroups = disjointSubgroups;
	}
}