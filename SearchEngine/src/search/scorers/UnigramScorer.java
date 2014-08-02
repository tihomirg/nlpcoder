package search.scorers;

import search.DeclarationSelectionEntry;
import search.nlp.parser.RichToken;

public class UnigramScorer implements RichDeclarationScorer {

	@Override
	public double calculate(DeclarationSelectionEntry rd, RichToken richToken) {
		return rd.getUnigramProbability();
	}

}
