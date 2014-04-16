package selection;

import selection.parser.one.Word;
import definitions.Declaration;

public class WordExtractorEmpty extends WordExtractor {

	private static final Word[] EMPTY_WORDS = new Word[0];

	@Override
	public Word[] getWords(Declaration decl) {
		return EMPTY_WORDS;
	}

}
