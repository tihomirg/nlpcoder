package search;

import search.scorers.Score;
import definitions.Declaration;

public class RichDeclaration {

	private Declaration decl;
	private Score score;

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

	@Override
	public String toString() {
		return "Score = " + score + "  " +decl+ "]";
	}
}
