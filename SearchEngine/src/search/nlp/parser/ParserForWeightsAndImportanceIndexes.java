package search.nlp.parser;

import java.util.LinkedList;
import java.util.List;
import nlp.parser.RelatedWordsMap;
import nlp.parser.TaggedWord;
import nlp.parser.TaggedWordMeaning;
import nlp.parser.Token;
import search.WToken;
import search.config.SearchConfig;

public class ParserForWeightsAndImportanceIndexes implements IParser {

	private RelatedWordsMap wordMap;

	public ParserForWeightsAndImportanceIndexes(RelatedWordsMap wordMap) {
		this.wordMap = wordMap;
	}

	@Override
	public Input parse(Input input) {
		for (Sentence sentence : input.getSentences()) {
			for (RichToken richToken : sentence.getRichTokens()) {
				List<Token> primaryTokens = richToken.getLeadingTokens();
				List<Token> secondaryTokens = richToken.getSecondaryTokens();

				double primaryWeight = SearchConfig.getPrimaryWeight();
				double secondaryWeight = SearchConfig.getSecondaryWeight();
				
				richToken.setLeadingWTokens(tokensToWTokens(primaryTokens, SearchConfig.getPrimaryIndex(), primaryWeight));
				richToken.setSecondaryWTokens(tokensToWTokens(secondaryTokens, SearchConfig.getSecondaryIndex(), secondaryWeight));
				
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

		wTokens.addAll(taggedWordsToWTokens(taggedWords, SearchConfig.getPrimaryIndex(), importanceWeigh, score * SearchConfig.getRelatedWeightFactor()));

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
