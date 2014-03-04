package selection;

import selection.parser.one.SentenceZero;
import selection.parser.one.Word;

import definitions.Declaration;

public class WordExtractorFromName implements IWordExtractor {

	private ParserPipeline strategy;
	
	public WordExtractorFromName(ParserPipeline strategy) {
		this.strategy = strategy;
	}

	@Override
	public Indexes get(Declaration decl) {
		return ((SentenceTwoIndexes) this.strategy.parse(new SentenceZero(decl.getName()))).getIndexes();
	}

	@Override
	public Word[] getWords(Declaration decl) {
		return ((SentenceTwoIndexes) this.strategy.parse(new SentenceZero(decl.getName()))).getWords();
	}
}
