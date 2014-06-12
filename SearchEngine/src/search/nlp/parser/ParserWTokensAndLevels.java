package search.nlp.parser;

import java.util.LinkedList;
import java.util.List;

import search.nlp.parser.IParser;
import nlp.parser.Token;
import search.WToken;

public class ParserWTokensAndLevels implements IParser{

	@Override
	public Input parse(Input input) {
		for (Sentence sentence : input.getSentences()) {
			for (Group group: sentence.getSearchKeyAndLiteralGroups()) {
				List<List<WToken>> levels = new LinkedList<List<WToken>>();

				levels.add(firstLevel(group));
				levels.add(secondLevel(group));
				levels.add(thirdLevel(group));
				levels.add(fourthLevel(group));

				group.setLevels(levels);
			}			
		}

		return input;
	}

	private List<WToken> fourthLevel(Group group) {
		return group.isLiteral() ? new LinkedList<WToken>() : getWTokens(group.getAllGraphTokenRelatedTokens());
	}

	private List<WToken> thirdLevel(Group group) {
		return group.isLiteral() ? new LinkedList<WToken>() : getWTokens(group.getAllGraphTokens());
	}

	private List<WToken> secondLevel(Group group) {
		return group.isLiteral() ? new LinkedList<WToken>() : getWTokens(group.getTokenRelatedTokens());
	}

	private List<WToken> firstLevel(Group group) {
		List<WToken> wtokens = new LinkedList<WToken>();

		if(group.isLiteral()){
			wtokens.add(getWToken(group.getLiteralTypeToken()));
		} else {
			if(group.hasTokenDecompositions()){
				wtokens.addAll(getWTokens(group.getTokenDecompositions()));
			} else {
				wtokens.add(getWToken(group.getToken()));
			}
		}

		return wtokens;
	}

	private WToken getWToken(Token token) {
		return new WToken(token);
	}

	private List<WToken> getWTokens(List<Token> tokens) {
		List<WToken> wtokens = new LinkedList<WToken>();
		for (Token token : tokens) {
			wtokens.add(getWToken(token));
		}
		return wtokens;
	}
}
