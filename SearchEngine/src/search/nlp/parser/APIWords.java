package search.nlp.parser;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nlp.parser.Token;
import definitions.Declaration;
import api.StabileAPI;

public class APIWords {

	public Set<String> words;
	
	public APIWords(StabileAPI api) {
		this.words = new HashSet<String>();
		load(api);
	}

	private void load(StabileAPI api) {
		// TODO Auto-generated method stub
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

	private void addAll(List<List<Token>> tokenss) {
		for (List<Token> tokens : tokenss) {
			add(tokens);
		}
	}

	private void add(List<Token> tokes) {
		for (Token token : tokes) {
			add(token);
		}
	}

	private void add(Token token) {
		words.add(token.getLemma());
	}
	
	public boolean isAPIWord(String lemma){
		return words.contains(lemma);
	}
}
