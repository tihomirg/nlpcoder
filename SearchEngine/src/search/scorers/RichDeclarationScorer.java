package search.scorers;

import search.DeclarationSelectionEntry;
import search.nlp.parser.RichToken;

public interface RichDeclarationScorer {

	public double calculate(DeclarationSelectionEntry rd, RichToken richToken);
	
}

