package selection.scorers;

import selection.RichDeclarationStatistics;

public class HitScorer implements RichDeclarationScorer {

	private double factor;

	public HitScorer(double factor) {
		this.factor = factor;
	}
	
	@Override
	public double calculate(RichDeclarationStatistics rds) {
		return Math.log(factor * rds.getHits().size());
	}

}
