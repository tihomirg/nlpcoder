package selection;

import selection.parser.one.Word;

import definitions.Declaration;

public interface IWordExtractor {

	Indexes get(Declaration decl);
	
	Word[] getWords(Declaration decl);	

}
