package selection;

public abstract class WordsInflator {

	protected WordNet wordNet;
	
	public WordsInflator(WordNet wordNet) {
		this.wordNet = wordNet;
	}

	public abstract Words inflate(Words words);
}
