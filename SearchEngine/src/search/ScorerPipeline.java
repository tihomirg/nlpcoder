package search;

import search.config.SearchConfig;
import search.nlp.parser.RichToken;
import search.scorers.RichDeclarationScorer;
import search.scorers.Score;

public class ScorerPipeline {

	private RichDeclarationScorer[] scorers;
	
	public ScorerPipeline(RichDeclarationScorer[] scorers) {
		this.scorers = scorers;
	}
	
	public Score calculate(DeclarationSelectionEntry rd, RichToken richToken) {
		Score score = new Score(SearchConfig.getDeclarationScorerCoefs());
		
		for (RichDeclarationScorer scorer : scorers) {
			score.add(scorer.calculate(rd, richToken));
		}
		
		return score;
	}

}
