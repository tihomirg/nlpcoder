package selection;

import selection.parser.one.SentenceThree;
import selection.parser.one.SentenceTwo;
import selection.parser.one.SentenceZero;
import selection.parser.one.trees.SentenceOne;

public abstract class IParser {
	
	public ISentence parse(ISentence sentence) { 
		return sentence.apply(this);
	}
	
	public ISentence parse(SentenceZero curr) { 
		return null;
	}
	
	public ISentence parse(SentenceOne curr) { 
		return null;
	}
	
	public ISentence parse(SentenceTwo curr) { 
		return null;
	}
	
	public ISentence parse(SentenceThree curr){
		return null;
	}

}
