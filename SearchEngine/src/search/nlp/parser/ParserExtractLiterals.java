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
			List<Group> strings = extractStringLiterals(sentence);
			sentence.setStringLiterals(strings);

			List<Group> numbers = extractNumberLiterals(sentence);
			sentence.setNumberLiterals(numbers);			
		}

		return input;
	}


	private List<Group> extractNumberLiterals(Sentence sentence) {
		List<Group> groups = transformToGroups(sentence.getGroupMap(), extractNumbers(sentence.getTokens()));

		for (Group group : groups) {
			group.setLiteral(true);
			group.setLiteralTypeToken(new Token("Integer", "integer", "NN", 0));
		}
		return groups;
	}

	//TODO: Check other number types as well.
	private List<Token> extractNumbers(List<Token> tokens) {
		List<Token> numbers = new LinkedList<Token>();

		for (Token token : tokens) {
			String text = token.getText();
			if(isInteger(text)){
				numbers.add(token);
			}
		}

		return numbers;
	}


	private boolean isInteger(String text) {
		try{
			Integer.parseInt(text);
			return true;
		} catch(NumberFormatException nfe) {
			return false;
		}
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
			int index = token.getIndex();
			if (index == -1){
				groups.add(new Group(token));
			} else {
				groups.add(groupMap.get(index));
			}
		}
		return groups;
	}

	private List<Token> extractStrings(List<Token> tokens) {
		List<Token> strings = new LinkedList<Token>();

		int size = tokens.size();

		int i = 0;
		while(i < size - 2) {
			Token first = tokens.get(i);

			if (first.isBeginingOfString()){
				Token second = tokens.get(i+1);			
				if (second.isEndOfString()){
					strings.add(new Token("", "", "NN", 0));
					i+=2;
				} else {
					Token third = tokens.get(i+2);
					if (third.isEndOfString()){
						strings.add(tokens.get(i+1));
						i+=3;
					} else {
						i++;
					}
				}
			} else {
				i++;
			}
		}

		return strings;
	}

}
