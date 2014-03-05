package selection;

import selection.parser.one.SentenceZero;
import selection.parser.one.Word;
import definitions.Declaration;

public class WordExtractorFromSignature implements IWordExtractor {

	private ParserPipeline strategy;
	
	public WordExtractorFromSignature(ParserPipeline strategy) {
		this.strategy = strategy;
	}

	@Override
	public Indexes get(Declaration decl) {
		return ((SentenceTwoIndexes) this.strategy.parse(new SentenceZero(decl.getSignature()))).getIndexes();
	}

	@Override
	public Word[] getWords(Declaration decl) {
		return ((SentenceTwoIndexes) this.strategy.parse(new SentenceZero(decl.getSignature()))).getWords();
	}
}
