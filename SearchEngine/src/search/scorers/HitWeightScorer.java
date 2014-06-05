package search.scorers;

import java.util.Map;
import java.util.PriorityQueue;

import nlp.parser.Token;
import search.RichDeclarationStatistics;
import search.Slot;
import search.WToken;

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
		Slot[][] slotss = rds.getSlots();
		
		double score = 0;
		
		for (Slot[] slots : slotss) {
			for (Slot slot : slots) {
				score += slot.getScore();
			}
		}
		
		return factor * score;
	}
}
