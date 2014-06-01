package parser;

import java.util.List;
import java.util.Map;

import core.Local;

public class ParserIdentifyLocals implements IParser {

	private Map<String, Local> locals;

	public ParserIdentifyLocals(Map<String, Local> locals) {
		this.locals = locals;
	}

	@Override
	public Input parse(Input input) {
		List<Sentence> sentences = input.getSentences();
		
		for (Sentence sentence : sentences) {
			Map<Integer, Group> groupMap = sentence.getGroupMap();
			
			for (Group group : groupMap.values()) {
				Token token = group.getToken();
				String lemma = token.getLemma();
				
				if (locals.containsKey(lemma)){
					Local local = locals.get(lemma);
					group.setLocal(local);
				}
			}
		}
		
		return input;
	}

}
