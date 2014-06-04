package search.scorers;

import search.RichDeclarationStatistics;

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
		return factor * rds.getHits().size();
	}

}
