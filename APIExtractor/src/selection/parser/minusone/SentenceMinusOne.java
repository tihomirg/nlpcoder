package selection.parser.minusone;

import selection.IParser;
import selection.ISentence;

public class SentenceMinusOne implements ISentence {

	private String rep;

	public SentenceMinusOne(String rep) {
		this.rep = rep;
	}

	@Override
	public ISentence apply(IParser iParser) {
		return iParser.parse(this);
	}
	
	public String getRep() {
		return rep;
	}

	public void setRep(String rep) {
		this.rep = rep;
	}	

}
