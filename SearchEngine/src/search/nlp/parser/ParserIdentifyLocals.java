package search.nlp.parser;

import java.util.List;
import java.util.Map;

import search.nlp.parser.IParser;
import nlp.parser.Token;
import search.Local;

public class ParserIdentifyLocals implements IParser {

	private Map<String, Local> locals;

	public ParserIdentifyLocals(Map<String, Local> locals) {
		this.locals = locals;
	}

	@Override
	public Input parse(Input input) {
		List<Sentence> sentences = input.getSentences();
		
		for (Sentence sentence : sentences) {
			
			for (Group group : sentence.getGroups()) {
				Token token = group.getToken();
				String text = token.getText();
				
				if (locals.containsKey(text)){
					Local local = locals.get(text);
					group.setLocal(local);
				}
			}
		}
		
		return input;
	}

}
