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

	public ISynset getSynset() {
		return synset;
	}

	public void setSynset(ISynset synset) {
		this.synset = synset;
	}

	public String getGloss() {
		return gloss;
	}

	public void setGloss(String gloss) {
		this.gloss = gloss;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "WordMeaning [score=" + score + ", gloss=" + gloss + ", synset="+ synset + "]\n";
	}

}
