package selection.scorers;

import selection.RichDeclarationStatistics;

public class HitScorer implements RichDeclarationScorer {

	private double factor;

	public HitScorer() {
		this(1.0);
	}
	
	public HitScorer(double factor) {
		this.factor = factor;
	}
	
	@Override
	public double calculate(RichDeclarationStatistics rds) {
		return factor * rds.getHits().size();
	}

}
