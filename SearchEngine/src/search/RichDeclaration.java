package search;

import search.scorers.Score;
import definitions.Declaration;

public class RichDeclaration {

	private Declaration decl;
	private Score score;
	private int ordinal;
	private int maxRD;

	public RichDeclaration(Declaration decl, Score score) {
		this.decl = decl;
		this.score = score;
	}

	public Score getScore() {
		return score;
	}

	public Declaration getDecl() {
		return decl;
	}

	public void setOrdinlaNum(int ordinal) {
		this.ordinal = ordinal;
	}
	
	public void setMaxRD(int maxRD) {
		this.maxRD = maxRD;
	}

	public double getOrdinalScore(){
		return ((double)(this.maxRD - this.ordinal)) / this.maxRD;
	}

	@Override
	public String toString() {
		return "Score = " + score + "  " +decl+ "]";
	}

}
