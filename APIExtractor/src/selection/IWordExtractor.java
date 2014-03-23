package selection;

import selection.parser.one.Word;
import selection.scorers.Scorer;

import definitions.Declaration;

public interface IWordExtractor {

	Word[] getWords(Declaration decl);	

}
