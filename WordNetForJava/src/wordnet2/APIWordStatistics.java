package wordnet2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import api.StabileAPI;
import config.Config;
import definitions.Declaration;
import deserializers.Deserializer;
import nlp.parser.TaggedWord;
import nlp.parser.Token;
import types.NameGenerator;
import edu.mit.jwi.item.IIndexWord;

public class APIWordStatistics {
	private Map<String, Map<String, Integer>> posToLemmaToCount;
	
	public APIWordStatistics() {
		this.posToLemmaToCount = new HashMap<String, Map<String,Integer>>();
		load();
	}

	private void load() {
		NameGenerator nameGen = new NameGenerator(Config.getDeserializerVariablePrefix());
		Deserializer deserializer = new Deserializer();
		StabileAPI api = new StabileAPI(deserializer.deserialize(Config.getSecondStorageLocation()), nameGen);

		List<Declaration> uniqueDecls = api.getUniqueDecls();

		for (Declaration decl : uniqueDecls) {
			add(decl.getSimpleNameTokens());
			add(decl.getReceiverTokens());
			addAll(decl.getArgTokens());
			add(decl.getClazzTokens());
			addAll(decl.getAdditionalReceiverTokens());
			add(decl.getReturnTypeTokens());
		}
	}
	
	public boolean isAPIWord(TaggedWord word) {
		String lemma = word.getLemma();
		String pos = word.getPos();

		if(posToLemmaToCount.containsKey(pos))
			return posToLemmaToCount.get(pos).containsKey(lemma);
		else return false;
	}

	public int getCount(IIndexWord word) {
		return getCount(word.getLemma(), transformPos(word.getPOS().toString()));
	}
	
	public int getCount(TaggedWord word){
		return getCount(word.getLemma(), word.getPos());
	}

	public int getCount(String lemma, String pos) {
		if (!posToLemmaToCount.containsKey(pos)){
			return 0;
		} else {
			Map<String, Integer> lemmaToCount = posToLemmaToCount.get(pos);
			if(!lemmaToCount.containsKey(lemma)){
				return 0;
			} else {
				return lemmaToCount.get(lemma);
			}
		}
	}

	private String transformPos(String pos){
		return Character.isLetter(pos.charAt(0)) ? Character.toString(pos.charAt(0)).toUpperCase() : pos;
	}	

	private void addAll(List<List<Token>> tokenss) {
		for (List<Token> tokens : tokenss) {
			add(tokens);
		}
	}

	private void add(List<Token> tokens) {
		for (Token token : tokens) {
			add(token);
		}
	}

	private void add(Token token) {
		String pos = token.getPos();
		String lemma = token.getLemma();

		if (!posToLemmaToCount.containsKey(pos)){
			posToLemmaToCount.put(pos, new HashMap<String, Integer>());
		}

		Map<String, Integer> map = posToLemmaToCount.get(pos);

		if (!map.containsKey(lemma)){
			map.put(lemma, 1);
		} else {
			map.put(lemma, map.get(lemma) + 1);
		}
	}
	
	public void printWords() {
		int count = 0;
		for (Entry<String, Map<String, Integer>> entry : posToLemmaToCount.entrySet()) {
			String pos = entry.getKey();
			Map<String, Integer> lemmaToCount = entry.getValue();
			for (String lemma : lemmaToCount.keySet()) {
				count++;
				System.out.println("("+lemma+", "+pos+")");
			}
		}
		System.out.println("Words: "+count);
	}
}
