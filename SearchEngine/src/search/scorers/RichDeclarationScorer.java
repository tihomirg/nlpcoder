package search.scorers;

import search.DeclarationSelectionEntry;
import search.nlp.parser2.RichToken;

public interface RichDeclarationScorer {

	public double calculate(DeclarationSelectionEntry rd, RichToken richToken);
	
}

