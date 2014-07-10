package search.nlp.parser2;

import java.util.List;

import search.WToken;

public class ParserForScore implements IParser {

	@Override
	public Input parse(Input input) {
		for (Sentence sentence : input.getSentences()) {
			for (RichToken richToken : sentence.getRichTokens()) {
				List<WToken> relatedWords = richToken.getRelatedWTokens();
				List<WToken> tokens = richToken.getLeadingWTokens();
				
				//TODO: Set scores
			}
		}

		return input;
	}
}
