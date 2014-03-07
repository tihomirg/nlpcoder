package selection;

import selection.parser.one.Word;

public class SentenceTwoIndexes implements ISentence {
	
	private Word[] words;
	private Indexes indexes;
	
	public SentenceTwoIndexes(Word[] words, double nullProbs) {
		this.words = words;
		this.indexes = new Indexes(words, nullProbs);
	}
	
	public Word[] getWords() {
		return words;
	}

	public Indexes getIndexes() {
		return indexes;
	}	

	@Override
	public ISentence apply(IParser iParser) {
		// TODO Auto-generated method stub
		return null;
	}

}
