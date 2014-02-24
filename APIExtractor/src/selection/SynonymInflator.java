package selection;

public class SynonymInflator extends WordsInflator {

	public SynonymInflator(WordNet wordNet) {
		super(wordNet);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Words inflate(Words words) {
		Words syns = new Words();
		
		
		
		for(Word word: words.getWords()){
			wordNet.getSynonyms(word.getLemma(), word.getPos())
		    	
		}
		
		return null;
	}

}
