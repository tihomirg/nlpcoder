package selection;

import selection.parser.two.SentenceTwo;
import selection.parser.minusone.SentenceMinusOne;
import selection.parser.one.SentenceZero;
import selection.parser.one.trees.SentenceOne;

public abstract class IParser {
	
	public ISentence parse(ISentence sentence) { 
		return sentence.apply(this);
	}
	
	public ISentence parse(SentenceMinusOne curr) { 
		return null;
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
}
