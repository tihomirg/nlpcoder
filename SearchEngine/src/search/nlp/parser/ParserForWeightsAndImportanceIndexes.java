package search.nlp.parser;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import nlp.parser.RelatedWordsMap;
import nlp.parser.TaggedWord;
import nlp.parser.TaggedWordMeaning;
import nlp.parser.Token;
import search.WToken;

public class ParserForWeightsAndImportanceIndexes implements IParser {

	private RelatedWordsMap wordMap;
	private double initialPrimaryWeight;
	private double initialSecondaryWeight;
	private double relatedWeightFactor;
	private int primaryIndex;
	private int secondaryIndex;

	public ParserForWeightsAndImportanceIndexes(RelatedWordsMap wordMap, int primaryIndex, double primaryWeight, int secondaryIndex, double secondaryWeight, double relatedWeightFactor) {
		this.wordMap = wordMap;
		this.primaryIndex = primaryIndex;
		this.initialPrimaryWeight = primaryWeight;
		this.secondaryIndex = secondaryIndex;
		this.initialSecondaryWeight = secondaryWeight;
		this.relatedWeightFactor = relatedWeightFactor;
	}

	@Override
	public Input parse(Input input) {
		for (Sentence sentence : input.getSentences()) {
			for (RichToken richToken : sentence.getRichTokens()) {
				List<Token> primaryTokens = richToken.getLeadingTokens();
				List<Token> secondaryTokens = richToken.getSecondaryTokens();

				double initialWeight = initialPrimaryWeight + initialSecondaryWeight;
				int primarySize = primaryTokens.size();
				int secondarySize = secondaryTokens.size();

				double primaryWeight = initialPrimaryWeight; /// (initialWeight * primarySize);
				double secondaryWeight = initialSecondaryWeight;
//				if (secondarySize != 0) {
//				    secondaryWeight = initialSecondaryWeight / (initialWeight * secondarySize);
//				}
				
				richToken.setLeadingWTokens(tokensToWTokens(primaryTokens, primaryIndex, primaryWeight));
				richToken.setSecondaryWTokens(tokensToWTokens(secondaryTokens, secondaryIndex, secondaryWeight));
				
				List<List<WToken>> relatedWTokens = new LinkedList<List<WToken>>();
				for (Token token : primaryTokens) {
					List<TaggedWordMeaning> meanings = this.wordMap.get(toTaggedWord(token));
					if (meanings != null) relatedWTokens.add(relatedMeaningsToWTokens(meanings, primaryWeight));
					else relatedWTokens.add(new LinkedList<WToken>());
				}
				richToken.setRelatedWTokens(relatedWTokens);
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
	
	private List<WToken> relatedMeaningsToWTokens(List<TaggedWordMeaning> meanings, double importanceWeigh) {
		List<WToken> wTokens = new LinkedList<WToken>();

		for (TaggedWordMeaning meaning: meanings) {
			wTokens.addAll(relatedMeaningToWTokens(meaning, importanceWeigh));
		}

		return wTokens;
	}

	private List<WToken> relatedMeaningToWTokens(TaggedWordMeaning meaning, double importanceWeigh) {
		List<WToken> wTokens = new LinkedList<WToken>();

		List<TaggedWord> taggedWords = meaning.getWords();
		double score = meaning.getScore();

		wTokens.addAll(taggedWordsToWTokens(taggedWords, primaryIndex, importanceWeigh, score * relatedWeightFactor));

		return wTokens;
	}

	private List<WToken> taggedWordsToWTokens(List<TaggedWord> taggedWords, int importanceIndex, double importanceWeigh, double relatednessWeight) {
		List<WToken> wTokens = new LinkedList<WToken>();
		for (TaggedWord taggedWord : taggedWords) {
			String lemma = taggedWord.getLemma();
			String pos = taggedWord.getPos();
			WToken wToken = new WToken(new Token(lemma, lemma, pos), importanceIndex, importanceWeigh);
			wToken.setRelatednessWeight(relatednessWeight);
			wTokens.add(wToken);
		}
		return wTokens;
	}

	private TaggedWord toTaggedWord(Token token) {
		return new TaggedWord(token.getLemma(), token.getPos());
	}
}
