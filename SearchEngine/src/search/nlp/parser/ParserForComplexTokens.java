package search.nlp.parser;

import nlp.parser.Token;

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
