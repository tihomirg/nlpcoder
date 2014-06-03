package selection.scorers;

import java.util.Map;
import java.util.PriorityQueue;

import nlp.parser.Token;
import selection.RichDeclarationStatistics;
import selection.WToken;

public class HitWeightScorer implements RichDeclarationScorer {

	private double factor;
	
	public HitWeightScorer() {
		this(1.0);
	}

	public HitWeightScorer(double factor) {
		this.factor = factor;
	}
	
	@Override
	public double calculate(RichDeclarationStatistics rds) {
		Map<Token, PriorityQueue<WToken>> hits = rds.getHits();
		
		double score = 0;
		
		for (PriorityQueue<WToken> pq : hits.values()) {
			WToken best = pq.element();
			score += best.getScore();
		}
		
		return factor * score;
	}
}
