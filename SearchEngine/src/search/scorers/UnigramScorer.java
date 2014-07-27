package search.scorers;

import search.DeclarationSelectionEntry;
import search.nlp.parser2.RichToken;

public class UnigramScorer implements RichDeclarationScorer {

	private double factor;

	public UnigramScorer(double factor) {
		this.factor = factor;
	}

	@Override
	public double calculate(DeclarationSelectionEntry rd, RichToken richToken) {
		return this.factor * rd.getUnigramScore();
	}

}
