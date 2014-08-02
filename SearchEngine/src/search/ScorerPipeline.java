package search;

import search.nlp.parser.RichToken;
import search.scorers.RichDeclarationScorer;
import search.scorers.Score;

public class ScorerPipeline {

	private RichDeclarationScorer[] scorers;
	private Double[] coefs;
	
	public ScorerPipeline(RichDeclarationScorer[] scorers, Double[] coefs) {
		assert this.scorers.length == coefs.length;
		this.scorers = scorers;
		this.coefs = coefs;
	}
	
	public Score calculate(DeclarationSelectionEntry rd, RichToken richToken) {
		Score score = new Score(coefs);
		
		for (RichDeclarationScorer scorer : scorers) {
			score.add(scorer.calculate(rd, richToken));
		}
		
		return score;
	}

}
