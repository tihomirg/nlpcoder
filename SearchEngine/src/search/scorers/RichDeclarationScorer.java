package search.scorers;

import search.RichDeclarationStatistics;

public interface RichDeclarationScorer {

	public double calculate(RichDeclarationStatistics rds);
}

