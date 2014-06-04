package search.scorers;

import search.RichDeclarationStatistics;

public class DeclProbScorer implements RichDeclarationScorer{

	@Override
	public double calculate(RichDeclarationStatistics rds) {
		return rds.getDeclProb();
	}

}
