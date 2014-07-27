package search;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nlp.parser.Token;

public class Table {

	private static final String ALL = "All";
	private Map<String, Map<String, Set<DeclarationSelectionEntry>>> posToLemmaToRDs = new HashMap<String, Map<String, Set<DeclarationSelectionEntry>>>();
	
	public void add(DeclarationSelectionEntry rd){
		for (Token token : rd.getTokens()) {
			add(token, rd);
		}
	}

	private void add(Token token, DeclarationSelectionEntry rd) {
		String pos = token.getPos();
		
		Map<String, Set<DeclarationSelectionEntry>> lemmaToRDs = null;
		
		if(!posToLemmaToRDs.containsKey(pos)){
			lemmaToRDs = new HashMap<String, Set<DeclarationSelectionEntry>>();
			posToLemmaToRDs.put(pos, lemmaToRDs);
		} else {
			lemmaToRDs = posToLemmaToRDs.get(pos);
		}
		
		String lemma = token.getLemma();
		
		Set<DeclarationSelectionEntry> rds = null;
		if(!lemmaToRDs.containsKey(lemma)){
			rds = new HashSet<DeclarationSelectionEntry>();
			lemmaToRDs.put(lemma, rds);
		} else {
			rds = lemmaToRDs.get(lemma);
		}
		
		rds.add(rd);
	}

	public void search(WToken searchKey) {
		Token token = searchKey.getToken();
		Map<String, Set<DeclarationSelectionEntry>> lemmaToRDs = posToLemmaToRDs.get(token.getPos());
		
		if (lemmaToRDs != null){
			Set<DeclarationSelectionEntry> rds = lemmaToRDs.get(token.getLemma());
			
			if(rds != null){
				for (DeclarationSelectionEntry rd : rds) {
					rd.hit();
				}
			}
		}
	}
}
