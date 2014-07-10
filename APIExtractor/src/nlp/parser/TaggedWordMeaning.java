package nlp.parser;

import java.util.List;

public class TaggedWordMeaning {

	private String gloss;
	private double score;
	private List<TaggedWord> words;
	
	public TaggedWordMeaning() {}
	
	public TaggedWordMeaning(String gloss, double score, List<TaggedWord> words) {
		this.gloss = gloss;
		this.score = score;
		this.words = words;
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

	public List<TaggedWord> getWords() {
		return this.words;
	}
	
	@Override
	public String toString() {
		return "WordMeaning [score=" + score + ", gloss=" + gloss + ", \nwords="+ words + "]";
	}

}
