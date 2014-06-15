package search.nlp.parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import search.nlp.parser.IParser;
import wordnet.WordNet;
import nlp.parser.Token;

public class ParserRelatedWords implements IParser{

//	private WordNet wordnet;
//	
//	public ParserRelatedWords(){
//		this.wordnet = new WordNet();
//	}
	
	@Override
	public Input parse(Input input) {
		List<Sentence> sentences = input.getSentences();
		
		for (Sentence sentence : sentences) {
			for (Group group: sentence.getSearchKeyGroups()) {
				
//				List<Token> tokenRelatedTokens = getTokenRelatedTokens(group);
//				
//				System.out.print("Related Words: ");
//				System.out.println(group.getToken());
//				System.out.println(tokenRelatedTokens);
//				System.out.println();
//				System.out.println();
//				
//				group.setTokenRelatedTokens(tokenRelatedTokens);
				
				//List<Token> allGraphTokenRelatedTokens = getAllGraphTokenRelatedTokens(group);
				//group.setAllGraphTokenRelatedTokens(allGraphTokenRelatedTokens);
				
				group.setTokenRelatedTokens(new LinkedList<Token>());
				group.setAllGraphTokenRelatedTokens(new LinkedList<Token>());				
			}
		}
		
		return input;
	}

	private List<Token> getAllGraphTokenRelatedTokens(Group group) {
		List<Token> allGraphTokenRelatedTokens = group.getAllGraphTokenRelatedTokens();
		if (allGraphTokenRelatedTokens != null ){
			return getDistinctRelatedTokens(allGraphTokenRelatedTokens);
		} else return new LinkedList<Token>();
	}

	private List<Token> getTokenRelatedTokens(Group group) {		
		List<Token> seeds = new LinkedList<Token>();
		if(group.hasTokenDecompositions()){
			seeds.addAll(group.getTokenDecompositions());
		} else {
			seeds.add(group.getToken());
		}
		
		return getDistinctRelatedTokens(seeds);
	}

	private List<Token> getDistinctRelatedTokens(List<Token> seeds) {
		List<Token> tokens = new LinkedList<Token>();		
		
		for (Token seed : seeds) {
//			tokens.addAll(wordnet.getRelatedWords(seed));
		}
		
		return fileterDistinct(tokens, seeds);
	}

	private List<Token> fileterDistinct(List<Token> tokens, List<Token> seeds) {
		List<Token> distinct = new LinkedList<Token>();
		Set<String> visited = prepareVisited(seeds);
		
		List<Token> candidates = filterStrangeLemmas(tokens);
		for (Token token : candidates) {
			String lemma = token.getLemma();
			if (!visited.contains(lemma)){
				distinct.add(token);
				visited.add(lemma);
			}
		}
		return distinct;
	}

	private Set<String> prepareVisited(List<Token> seeds) {
		Set<String> visited = new HashSet<String>();
		
		for (Token token : seeds) {
			visited.add(token.getLemma());
		}
		return visited;
	}

	private void addToVisited(Map<String, Set<String>> visited, List<Token> seeds) {
		for (Token token : seeds) {
			addToVisited(visited, token);
		}
	}

	private boolean inVisited(Map<String, Set<String>> visited, Token token) {
		String lemma = token.getLemma();
		if (!visited.containsKey(lemma)) return false;
		else return visited.get(lemma).contains(token.getPos());
	}

	private void addToVisited(Map<String, Set<String>> visited, Token token) {
		String lemma = token.getLemma();
		if (!visited.containsKey(lemma)){
			visited.put(lemma, new HashSet<String>());
		}
		visited.get(lemma).add(token.getPos());
	}

	private List<Token> filterStrangeLemmas(List<Token> tokens) {
		List<Token> candidates = new LinkedList<Token>();
		for (Token token : tokens) {
			if(!strange(token)){
				candidates.add(token);
			}
		}
		return candidates;
	}

	private boolean strange(Token token) {
		return token.getLemma().contains("-") || token.getLemma().contains("_");
	}

}
