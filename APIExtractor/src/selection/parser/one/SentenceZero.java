package selection.parser.one;

import selection.IParser;
import selection.ISentence;

public class SentenceZero implements ISentence {

	private String rep;
	
	public SentenceZero(String rep) {
		this.rep = rep;
	}
	
	public String getRep() {
		return rep;
	}

	public void setRep(String rep) {
		this.rep = rep;
	}

	@Override
	public ISentence apply(IParser iParser) {
		return iParser.parse(this);
	}

}
