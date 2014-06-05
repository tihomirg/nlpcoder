package search.scorers;

import search.RichDeclarationStatistics;
import search.Slot;

public class HitCountScorer implements RichDeclarationScorer {

	private double factor;

	public HitCountScorer() {
		this(1.0);
	}
	
	public HitCountScorer(double factor) {
		this.factor = factor;
	}
	
	@Override
	public double calculate(RichDeclarationStatistics rds) {
		Slot[][] slotss = rds.getSlots();
		
		double score = 0;
		
		for (Slot[] slots : slotss) {
			for (Slot slot : slots) {
				score += slot.isOccupiedToInt();
			}
		}
		
		return factor * score;
	}

}
