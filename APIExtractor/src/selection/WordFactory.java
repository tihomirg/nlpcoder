package selection;

import java.util.List;

import edu.mit.jwi.item.POS;

public class WordFactory {

	private ValueStrategy strategy;
	
	public WordFactory() {
		this.strategy = new ValueStrategy();
	}

//	public Words getWords(List<String> rWords, POS pos, int wordIndex, int groupIndex) {
//		
//		Words words = new Words();
//		words.setIndex(wordIndex);
//		words.setPos(pos);
//		
//		
//		for(String rWord:rWords){
//			Word word = new Word(rWord, wordIndex, groupIndex, strategy.getVal(groupIndex));
//			words.add(word);
//		}
//		
//		return words;
//	}
	
	public Words getWords(List<Word> rWords, POS pos, int wordIndex) {
		Words words = new Words();
		words.setIndex(wordIndex);
		words.setPos(pos);
		words.addAll(rWords);
		return words;
	}	
	
	public Word getWord(String lemma, POS pos, int wordIndex, int groupIndex){
		return new Word(lemma, pos, wordIndex, groupIndex, strategy.getVal(groupIndex));
	}

}
