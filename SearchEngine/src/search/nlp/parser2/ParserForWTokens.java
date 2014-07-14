package search.nlp.parser2;

import java.util.LinkedList;
import java.util.List;
import nlp.parser.RelatedWordsMap;
import nlp.parser.TaggedWord;
import nlp.parser.TaggedWordMeaning;
import nlp.parser.Token;
import search.WToken;

public class ParserForWTokens implements IParser {


	private RelatedWordsMap relatedWords;
	private double leadingWTokenScore;
	private double secondaryWTokenScore;
	private double relatedWTokenFactor;

	public ParserForWTokens(RelatedWordsMap relatedWords, double leadingWTokenScore, double secondaryWTokenScore, double relatedWTokenFactor) {
		this.relatedWords = relatedWords;
		this.leadingWTokenScore = leadingWTokenScore;
		this.secondaryWTokenScore = secondaryWTokenScore;
		this.relatedWTokenFactor = relatedWTokenFactor;
	}

	@Override
	public Input parse(Input input) {
		for (Sentence sentence : input.getSentences()) {
			for (RichToken richToken : sentence.getRichTokens()) {
				List<Token> tokens = richToken.getLeadingTokens();
				List<List<WToken>> relatedWords = new LinkedList<List<WToken>>();
				for (Token token : tokens) {
					List<TaggedWordMeaning> meanings = this.relatedWords.get(toTaggedWord(token));
					if (meanings != null) relatedWords.add(relatedMeaningsToWTokens(meanings));
					else relatedWords.add(new LinkedList<WToken>());
				}

				richToken.setRelatedWTokens(relatedWords);
				richToken.setLeadingWTokens(tokensToWTokens(tokens, leadingWTokenScore));
				richToken.setSecondaryWTokens(tokensToWTokens(richToken.getSecondaryTokens(), secondaryWTokenScore));
			}
		}

		return input;
	}

	private List<WToken> tokensToWTokens(List<Token> tokens, double score) {
		List<WToken> wTokens = new LinkedList<WToken>();

		for (Token token : tokens) {
			wTokens.add(new WToken(token, 0, score));
		}

		return wTokens;
	}

	private List<WToken> copyTokensToWTokens(List<Token> tokens, double score) {
		List<WToken> wTokens = new LinkedList<WToken>();

		for (Token token : tokens) {
			wTokens.add(new WToken(new Token(token.getText(), token.getLemma(), token.getPos(), token.getIndex()), 0, score));
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

		wTokens.addAll(taggedWordsToWTokens(taggedWords, score * relatedWTokenFactor));

		return wTokens;
	}

	private List<WToken> taggedWordsToWTokens(List<TaggedWord> taggedWords, double score) {
		List<WToken> wTokens = new LinkedList<WToken>();
		for (TaggedWord taggedWord : taggedWords) {
			String lemma = taggedWord.getLemma();
			String pos = taggedWord.getPos();
			wTokens.add(new WToken(new Token(lemma, lemma, pos, 0), 0, score));
		}
		return wTokens;
	}

	private TaggedWord toTaggedWord(Token token) {
		return new TaggedWord(token.getLemma(), token.getPos());
	}
}
