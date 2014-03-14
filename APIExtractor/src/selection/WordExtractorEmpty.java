package selection;

import selection.parser.one.Word;
import definitions.Declaration;

public class WordExtractorEmpty implements IWordExtractor {

	@Override
	public Indexes get(Declaration decl) {
		return null;
	}

	@Override
	public Word[] getWords(Declaration decl) {
		return new Word[0];
	}

}
