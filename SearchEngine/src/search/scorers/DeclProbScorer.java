package search.scorers;

import search.RichDeclarationStatistics;

public class DeclProbScorer implements RichDeclarationScorer {

	private double factor;

	public DeclProbScorer(double factor) {
		this.factor = factor;
	}
	
	@Override
	public double calculate(RichDeclarationStatistics rds) {
		return factor * rds.getLogProb();
	}

}
