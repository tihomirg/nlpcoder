package search.nlp.parser;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import search.nlp.parser.IParser;
import nlp.parser.Token;

public class ParserExtractLiterals implements IParser {

	@Override
	public Input parse(Input input) {
		List<Sentence> sentences = input.getSentences();

		for (Sentence sentence : sentences) {
			List<Group> groups = extractStringLiterals(sentence);
			
			sentence.setStringLiterals(groups);
		}

		return input;
	}

	private List<Group> extractStringLiterals(Sentence sentence) {
		List<Group> groups = transformToGroups(sentence.getGroupMap(), extractStrings(sentence.getTokens()));
		
		for (Group group : groups) {
			group.setLiteral(true);
			group.setLiteralTypeToken(new Token("String", "string", "NN", 0));
		}
		return groups;
	}

	private List<Group> transformToGroups(Map<Integer, Group> groupMap, List<Token> extractStrings) {
		List<Group> groups = new LinkedList<Group>();
		for (Token token : extractStrings) {
			groups.add(groupMap.get(token.getIndex()));
		}
		return groups;
	}

	private List<Token> extractStrings(List<Token> tokens) {
		List<Token> strings = new LinkedList<Token>();
		
		int size = tokens.size();

		int i = 0;
		while(i < size - 2) {
			Token first = tokens.get(i);
			Token last = tokens.get(i+2);
			
			if (first.isBeginingOfString() && last.isEndOfString()){
				strings.add(tokens.get(i+1));
				i+=3;
			} else {
				i++;
			}
		}
		
		return strings;
	}

}
