package search.nlp.parser;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import nlp.parser.RelatedWordsMap;
import nlp.parser.TaggedWord;
import nlp.parser.TaggedWordMeaning;
import nlp.parser.Token;
import search.WToken;

public class ParserForWTokens implements IParser {

	private RelatedWordsMap wordMap;
	private double primaryWeight;
	private double secondaryWeight;
	private double relatedWeightFactor;
	private int primaryIndex;
	private int secondaryIndex;

	public ParserForWTokens(RelatedWordsMap wordMap, int primaryIndex, double primaryWeight, int secondaryIndex, double secondaryWeight, double relatedWeightFactor) {
		this.wordMap = wordMap;
		this.primaryIndex = primaryIndex;
		this.primaryWeight = primaryWeight;
		this.secondaryIndex = secondaryIndex;
		this.secondaryWeight = secondaryWeight;
		this.relatedWeightFactor = relatedWeightFactor;
	}

	@Override
	public Input parse(Input input) {
		for (Sentence sentence : input.getSentences()) {
			for (RichToken richToken : sentence.getRichTokens()) {
				List<Token> tokens = richToken.getLeadingTokens();
				List<List<WToken>> relatedWords = new LinkedList<List<WToken>>();
				for (Token token : tokens) {
					List<TaggedWordMeaning> meanings = this.wordMap.get(toTaggedWord(token));
					if (meanings != null) relatedWords.add(relatedMeaningsToWTokens(meanings));
					else relatedWords.add(new LinkedList<WToken>());
				}

				richToken.setRelatedWTokens(relatedWords);
				richToken.setLeadingWTokens(tokensToWTokens(tokens, primaryIndex, primaryWeight));
				richToken.setSecondaryWTokens(tokensToWTokens(richToken.getSecondaryTokens(), secondaryIndex, secondaryWeight));
			}
		}

		return input;
	}

	private List<WToken> tokensToWTokens(List<Token> tokens, int importanceIndex, double importanceWeight) {
		List<WToken> wTokens = new LinkedList<WToken>();

		for (Token token : tokens) {
			wTokens.add(new WToken(token, importanceIndex, importanceWeight));
		}

		return wTokens;
	}
	
	private List<WToken> relatedMeaningsToWTokens(List<TaggedWordMeaning> meanings) {
		List<WToken> wTokens = new LinkedList<WToken>();

		for (TaggedWordMeaning meaning: meanings) {
			wTokens.addAll(relatedMeaningToWTokens(meaning));
		}

		return wTokens;
	}

	private List<WToken> relatedMeaningToWTokens(TaggedWordMeaning meaning) {
		List<WToken> wTokens = new LinkedList<WToken>();

		List<TaggedWord> taggedWords = meaning.getWords();
		double score = meaning.getScore();

		wTokens.addAll(taggedWordsToWTokens(taggedWords, primaryIndex, score * relatedWeightFactor));

		return wTokens;
	}

	private List<WToken> taggedWordsToWTokens(List<TaggedWord> taggedWords, int importanceIndex, double score) {
		List<WToken> wTokens = new LinkedList<WToken>();
		for (TaggedWord taggedWord : taggedWords) {
			String lemma = taggedWord.getLemma();
			String pos = taggedWord.getPos();
			wTokens.add(new WToken(new Token(lemma, lemma, pos), importanceIndex, score));
		}
		return wTokens;
	}

	private TaggedWord toTaggedWord(Token token) {
		return new TaggedWord(token.getLemma(), token.getPos());
	}
}
