package search;

import search.scorers.RichDeclarationScorer;
import search.scorers.Score;

public class ScorerPipeline {

	private RichDeclarationScorer[] scorers;
	
	public ScorerPipeline(RichDeclarationScorer[] scorers) {
		this.scorers = scorers;
	}
	
	public Score calculate(RichDeclarationStatistics rds) {
		Score score = new Score();
		
		for (RichDeclarationScorer scorer : scorers) {
			score.add(scorer.calculate(rds));
		}
		
		return score;
	}

}
