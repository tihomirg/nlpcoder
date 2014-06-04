package nlp.parser;

import java.util.LinkedList;
import java.util.List;

public class ParserRelatedWords implements IParser{

	@Override
	public Input parse(Input input) {
		List<Sentence> sentences = input.getSentences();
		
		for (Sentence sentence : sentences) {
			for (Group group: sentence.getSearchKeyGroups()) {
				
				//TODO: Implement the connection with WordNet
				group.setTokenRelatedTokens(new LinkedList<Token>());
				group.setAllGraphTokenRelatedTokens(new LinkedList<Token>());
			}
		}
		
		return input;
	}

}
