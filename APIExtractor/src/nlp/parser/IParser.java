package nlp.parser;

import nlp.parser.minusone.SentenceMinusOne;
import nlp.parser.one.SentenceZero;
import nlp.parser.one.trees.SentenceOne;
import nlp.parser.two.SentenceTwo;

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
