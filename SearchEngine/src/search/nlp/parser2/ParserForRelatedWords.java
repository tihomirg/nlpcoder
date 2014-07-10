package search.nlp.parser2;

import nlp.parser.Token;
import search.nlp.parser.ComplexWordDecomposer;

public class ParserForRelatedWords implements IParser{

	public ParserForRelatedWords() {
	}

	@Override
	public Input parse(Input input) {
		for (Sentence sentence : input.getSentences()) {
			for (RichToken richToken : sentence.getRichTokens()) {
				Token token = richToken.getOriginalToken();
				
			}
		}

		return input;
	}
}
