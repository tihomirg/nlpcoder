package nlp.parser.minusone;

import nlp.parser.IParser;
import nlp.parser.ISentence;

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
