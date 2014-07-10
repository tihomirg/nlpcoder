package search.nlp.parser2;

import java.util.LinkedList;
import java.util.List;
import nlp.parser.RelatedWordsMap;
import nlp.parser.TaggedWord;
import nlp.parser.TaggedWordMeaning;
import nlp.parser.Token;
import search.WToken;

public class ParserForRelatedWords implements IParser{

	private RelatedWordsMap relatedWords;

	public ParserForRelatedWords(RelatedWordsMap relatedWords) {
		this.relatedWords = relatedWords;
	}

	@Override
	public Input parse(Input input) {
		for (Sentence sentence : input.getSentences()) {
			for (RichToken richToken : sentence.getRichTokens()) {
				List<Token> tokens = richToken.getTokens();
				List<WToken> relatedWords = new LinkedList<WToken>();
				for (Token token : tokens) {
					List<TaggedWordMeaning> meanings = this.relatedWords.get(toTaggedWord(token));
					relatedWords.addAll(toWTokens(meanings));
				}
			}
		}

		return input;
	}

	private List<WToken> toWTokens(List<TaggedWordMeaning> meanings) {
		List<WToken> wTokens = new LinkedList<WToken>();
		
		for (TaggedWordMeaning meaning: meanings) {
			wTokens.addAll(toWTokens(meaning));
		}
		
		return wTokens;
	}

	private List<WToken> toWTokens(TaggedWordMeaning meaning) {
		List<WToken> wTokens = new LinkedList<WToken>();
		
		List<TaggedWord> taggedWords = meaning.getWords();
		double score = meaning.getScore();
		
		for (TaggedWord taggedWord : taggedWords) {
			wTokens.addAll(taggedWordsToWTokens(taggedWords, score));
		}
		
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
