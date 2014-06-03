package selection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nlp.parser.Token;

public class Table {

	private Map<String, Map<String, Set<RichDeclaration>>> posToLemmaToRDs = new HashMap<String, Map<String, Set<RichDeclaration>>>();
	
	public void add(RichDeclaration rd){
		for (Token token : rd.getTokens()) {
			add(token, rd);
		}
	}

	private void add(Token token, RichDeclaration rd) {
		String pos = token.getPos();
		
		Map<String, Set<RichDeclaration>> lemmaToRDs = null;
		
		if(!posToLemmaToRDs.containsKey(pos)){
			lemmaToRDs = new HashMap<String, Set<RichDeclaration>>();
			posToLemmaToRDs.put(pos, lemmaToRDs);
		} else {
			lemmaToRDs = posToLemmaToRDs.get(pos);
		}
		
		String lemma = token.getLemma();
		
		Set<RichDeclaration> rds = null;
		if(!lemmaToRDs.containsKey(lemma)){
			rds = new HashSet<RichDeclaration>();
			lemmaToRDs.put(lemma, rds);
		} else {
			rds = lemmaToRDs.get(lemma);
		}
		
		rds.add(rd);
	}
}
