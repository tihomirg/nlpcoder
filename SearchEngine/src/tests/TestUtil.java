package tests;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import nlp.parser.RelatedWordsMap;
import nlp.parser.Token;
import search.WToken;
import search.nlp.parser.Subgroup;

public class TestUtil {

	public static Token createNounT(String lemma) {
		return new Token(lemma, lemma, "N");
	}

	public static Token createVerbT(String lemma) {
		return new Token(lemma, lemma, "V");
	}	

	public static List<Subgroup> createSubgorups(Token[][] tokenss) {
		List<Subgroup> subgroups = new LinkedList<Subgroup>();	
		for (Token[] tokens : tokenss) {
			subgroups.add(createSubgroup(tokens));
		}
		return subgroups;
	}

	public static Subgroup createSubgroup(Token[] tokens) {
		List<WToken> wTokens = new LinkedList<WToken>();
		for (Token token: tokens) {
			wTokens.add(new WToken(token));
		}
		return new Subgroup(wTokens);
	}

	public static List<WToken> createWTokens(Token[] tokens) {
		List<WToken> wTokens = new LinkedList<WToken>();
		for (Token token : tokens) {
			wTokens.add(new WToken(token));
		}
		return wTokens;
	}

	public static WToken createNounWT(String lemma, int importanceIndex, double importanceWeight, int subgroupIndex, double relatednessWeight) {
		return new WToken(createNounT(lemma), importanceIndex, importanceWeight, subgroupIndex, relatednessWeight);
	}

	public static List<Subgroup> createSubgorups(WToken[][] wTokenss) {
		List<Subgroup> subgroups = new LinkedList<Subgroup>();	
		for (WToken[] wTokens : wTokenss) {
			subgroups.add(new Subgroup(Arrays.asList(wTokens)));
		}
		return subgroups;
	}
}
