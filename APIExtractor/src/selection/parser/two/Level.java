package selection.parser.two;

import java.util.List;

import selection.parser.one.Word;

public class Level {
	private double probability;
	private int depth;
	private List<Word> words;

	public Level(List<Word> words, int depth) {
		this(words, depth, 0.0);
	}
	
	public Level(List<Word> words, int depth, double probability) {
		this.probability = probability;
		this.depth = depth;
		this.words = words;
	}
	
	public double getProbability() {
		return probability;
	}
	public void setProbability(double probability) {
		this.probability = probability;
	}
	public List<Word> getWords() {
		return words;
	}
	public void setWords(List<Word> words) {
		this.words = words;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}	

	@Override
	public String toString() {
		return "Level [probability=" + probability + ", depth=" + depth
				+ ", words=" + words + "]";
	}
}
