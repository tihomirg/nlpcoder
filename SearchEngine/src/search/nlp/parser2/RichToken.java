package search.nlp.parser2;

import java.util.List;

import api.Local;
import nlp.parser.Token;

public class RichToken {

	private Token originalToken;
	private int beginPosition;
	private int endPosition;
	private Local local;
	private String stringLiteral;
	private String numberLiteral;
	private String booleanLiteral;
	private int index;
	private List<RichToken> semanticNeighbours;
	private List<RichToken> rightHandSideNeighbours;

	public RichToken(Token originalToken, int index, int beginPosition, int endPosition) {
		this.originalToken = originalToken;
		this.index = index;
		this.beginPosition = beginPosition;
		this.endPosition = endPosition;
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

	public void setStringLiteral(String string) {
		this.stringLiteral = string;
	}
	
	public boolean isStringLiteral(){
		return this.stringLiteral != null;
	}

	public void setNumberLiteral(String number) {
		this.numberLiteral = number;
	}
	
	public boolean isNumberLiteral(){
		return this.numberLiteral != null;
	}

	public void setBooleanLiteral(String bool) {
		this.booleanLiteral = bool;
	}
	
	public boolean isBooleanLiteral(){
		return this.booleanLiteral != null;
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
}
