package selection.parser.one.trees;

import java.util.Arrays;

import selection.IParser;
import selection.ISentence;
import selection.parser.one.ConstituentOne;
import selection.parser.one.Word;

public class SentenceOne implements ISentence {

	private ConstituentOne[] constituents;
	private Word[] words;

	public SentenceOne(ConstituentOne[] constituents, Word[] words) {
		this.constituents = constituents;
		this.words = words;
	}
	
	public Word[] getWords() {
		return words;
	}
	public void setWords(Word[] words) {
		this.words = words;
	}
	public ConstituentOne[] getConstituents() {
		return constituents;
	}
	public void setConstituents(ConstituentOne[] constituents) {
		this.constituents = constituents;
	}
	
	public int length(){
		return words.length;
	}
	
	public int numOfWord(){
		return words.length;
	}
	
	public int numOfConstituents(){
		return constituents.length;
	}

	@Override
	public ISentence apply(IParser iParser) {
		// TODO Auto-generated method stub
		return iParser.parse(this);
	}
	
	@Override
	public String toString() {
		return "SentenceOne [constituents=" + Arrays.toString(constituents)
				+ ", words=" + Arrays.toString(words) + "]";
	}

}
