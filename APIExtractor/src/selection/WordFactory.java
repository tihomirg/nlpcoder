package selection;

import selection.parser.one.ConstituentOne;
import selection.parser.one.Word;

import edu.mit.jwi.item.POS;

public class WordFactory {
	
	public ConstituentOne getWords(Word[] rWords, int consIndex) {
		return new ConstituentOne(rWords, consIndex);
	}	
	
	public Word createWordOne(String lemma, POS pos, int consIndex){
		return new Word(lemma, pos, consIndex);
	}

}
