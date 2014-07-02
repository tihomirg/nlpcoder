package search.nlp.parser2;

import nlp.parser.Token;
import search.nlp.parser.ComplexWordDecomposer;

public class ParserForComplexTokens implements IParser{

	private ComplexWordDecomposer decomposer;

	public ParserForComplexTokens(ComplexWordDecomposer decomposer) {
		this.decomposer = decomposer;
	}

	@Override
	public Input parse(Input input) {
		for (Sentence sentence : input.getSentences()) {
			for (RichToken richToken : sentence.getRichTokens()) {
				Token token = richToken.getOriginalToken();
				richToken.setTokenDecompositions(decomposer.decomposeTokenIfNeeded(token));
			}
		}

		return input;
	}
}
