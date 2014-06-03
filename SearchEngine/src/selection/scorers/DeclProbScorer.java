package selection.scorers;

import selection.RichDeclarationStatistics;

public class DeclProbScorer implements RichDeclarationScorer{

	@Override
	public double calculate(RichDeclarationStatistics rds) {
		return rds.getDeclProb();
	}

}
