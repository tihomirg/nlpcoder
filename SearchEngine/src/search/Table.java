package search;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nlp.parser.Token;

public class Table {

	private static final String ALL = "All";
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

	public void search(WToken searchKey) {
		Token token = searchKey.getToken();
		Map<String, Set<RichDeclaration>> lemmaToRDs = posToLemmaToRDs.get(token.getPos());
		
		if (lemmaToRDs != null){
			Set<RichDeclaration> rds = lemmaToRDs.get(token.getLemma());
			
			if(rds != null){
				for (RichDeclaration rd : rds) {
					rd.hit(searchKey);
				}
			}
		}
	}
}
