package nlp.parser;

import java.util.LinkedList;
import java.util.List;

import search.WToken;

public class ParserWTokensAndLevels implements IParser{

	@Override
	public Input parse(Input input) {
		for (Sentence sentence : input.getSentences()) {
			for (Group group: sentence.getSearchKeyGroups()) {
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
		return getWTokens(group.getAllGraphTokenRelatedTokens());
	}

	private List<WToken> thirdLevel(Group group) {
		return getWTokens(group.getAllGraphTokens());
	}

	private List<WToken> secondLevel(Group group) {
		return getWTokens(group.getTokenRelatedTokens());
	}

	private List<WToken> firstLevel(Group group) {
		List<WToken> wtokens = new LinkedList<WToken>();
		
		if(group.hasTokenDecompositions()){
			wtokens.addAll(getWTokens(group.getTokenDecompositions()));
		} else {
			wtokens.add(getWToken(group.getToken()));
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
