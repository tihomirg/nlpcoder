package selection.parser.two;

import java.util.Arrays;

import selection.IParser;
import selection.ISentence;
import selection.parser.one.Word;

public class SentenceTwo implements ISentence {

	private ConstituentTwo[] constituents;
	private Word[] words;

	public SentenceTwo(ConstituentTwo[] constituents, Word[] words) {
		this.constituents = constituents;
		this.words = words;
	}	
	
	public ConstituentTwo[] getConstituents() {
		return constituents;
	}
	public void setConstituents(ConstituentTwo[] constituents) {
		this.constituents = constituents;
	}
	public Word[] getWords() {
		return words;
	}
	public void setWords(Word[] words) {
		this.words = words;
	}

	@Override
	public ISentence apply(IParser iParser) {
		return iParser.parse(this);
	}

	@Override
	public String toString() {
		return "SentenceTwo [constituents=" + Arrays.toString(constituents)
				+ ",\n words=" + Arrays.toString(words) + "]\n";
	}
}
