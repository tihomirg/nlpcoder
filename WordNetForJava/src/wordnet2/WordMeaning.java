package wordnet2;

import edu.mit.jwi.item.ISynset;

public class WordMeaning {

	private ISynset synset;
	private String gloss;
	private double score;

	public WordMeaning(ISynset synset, String gloss, double score) {
		this.synset = synset;
		this.gloss = gloss;
		this.score = score;
	}

}
