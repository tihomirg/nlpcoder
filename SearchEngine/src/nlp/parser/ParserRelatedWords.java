package nlp.parser;

import java.util.Collection;
import java.util.List;

public class ParserRelatedWords implements IParser{

	@Override
	public Input parse(Input input) {
		List<Sentence> sentences = input.getSentences();
		
		for (Sentence sentence : sentences) {
			for (Group gorup: sentence.getGroups()) {
				
			}
		}
		
		return input;
	}

}
