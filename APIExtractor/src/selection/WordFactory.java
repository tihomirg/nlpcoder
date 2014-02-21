package selection;

import java.util.List;

import edu.mit.jwi.item.POS;

public class WordFactory {

	private ValueStrategy strategy;
	
	public Words getWords(List<String> rWords, POS pos, int wordIndex, int groupIndex) {
		
		Words words = new Words();
		
		for(String rWord:rWords){
			Word word = new Word(rWord, wordIndex, groupIndex, strategy.getVal(groupIndex));
			words.add(word);
		}
		
		return words;
	}

}
