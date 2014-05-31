package parser;

import java.util.List;

public class ParserExtractSpecialSymbols implements IParser {

	@Override
	public Input parse(Input input) {
		List<Sentence> sentences = input.getSentences();
		
		for (Sentence sentence : sentences) {
			List<Token> tokens = sentence.getTokens();
			
			for (Token token : tokens) {
				
			}
		}
		
		return input;
	}

}
