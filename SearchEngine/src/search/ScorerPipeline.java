package search;

import java.util.LinkedList;
import java.util.List;

import search.scorers.RichDeclarationScorer;
import search.scorers.Score;
import nlp.parser.Token;

public class ScorerPipeline {

	private List<RichDeclarationScorer> scorers;
	
	public ScorerPipeline() {
		this.scorers = new LinkedList<RichDeclarationScorer>();
	}
	
	public Score calculate(RichDeclarationStatistics rds) {
		Score score = new Score();
		
		for (RichDeclarationScorer scorer : scorers) {
			score.add(scorer.calculate(rds));
		}
		
		return score;
	}

}
