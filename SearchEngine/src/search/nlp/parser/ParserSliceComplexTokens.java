package search.nlp.parser;

import search.nlp.parser.IParser;
import nlp.parser.Token;

public class ParserSliceComplexTokens implements IParser{

	private ComplexWordDecomposer decomposer;

	public ParserSliceComplexTokens(ComplexWordDecomposer decomposer) {
		this.decomposer = decomposer;
	}

	@Override
	public Input parse(Input input) {
		for (Sentence sentence : input.getSentences()) {
			for (Group group : sentence.getGroups()) {
				Token token = group.getToken();
				group.setTokenDecompositions(decomposer.decomposeTokenIfNeeded(token));
			}
		}

		return input;
	}
}
