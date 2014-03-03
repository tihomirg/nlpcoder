package selection;

import selection.parser.one.trees.SentenceOne;

public class ParserTwoIndexes extends IParser {

	public ISentence parse(SentenceOne curr) { 
		return new SentenceTwoIndexes(curr.getWords());
	}
	
}
