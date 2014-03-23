package selection;

import selection.parser.one.Word;
import selection.scorers.Scorer;
import definitions.Declaration;

public class WordExtractorEmpty implements IWordExtractor {

	@Override
	public Word[] getWords(Declaration decl) {
		return new Word[0];
	}

}
