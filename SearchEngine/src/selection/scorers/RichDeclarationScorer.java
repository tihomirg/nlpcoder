package selection.scorers;

import selection.RichDeclarationStatistics;

public interface RichDeclarationScorer {

	public double calculate(RichDeclarationStatistics rds);
}

