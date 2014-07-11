package search.nlp.parser2;

import java.util.LinkedList;
import java.util.List;

import search.WToken;

public class ParserForIndexes implements IParser {

	@Override
	public Input parse(Input input) {
		for (Sentence sentence : input.getSentences()) {
			for (RichToken richToken : sentence.getRichTokens()) {
				List<WToken> relatedWTokens = richToken.getRelatedWTokens();
				List<WToken> leadingWTokens = richToken.getLeadingWTokens();
				List<WToken> secondaryWTokens = richToken.getSecondaryWTokens();
				
				List<WToken> primaryWTokens = new LinkedList<WToken>();				
				primaryWTokens.addAll(leadingWTokens);
				primaryWTokens.addAll(relatedWTokens);
				
				setImportanceIndex(primaryWTokens, 0);
				setImportanceIndex(secondaryWTokens, 1);
				
				//Subgroup index
			}
		}

		return input;
	}

	private void setImportanceIndex(List<WToken> wTokens, int index) {
		for (WToken wToken : wTokens) {
			wToken.setImportanceIndex(index);
		}
	}
}
