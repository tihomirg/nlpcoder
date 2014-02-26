package selection.parser.two;

import java.util.Arrays;
import java.util.List;

import selection.parser.one.Word;

public class Level {
	private double probability;
	private double depth;
	private List<Word> words;
	
	public Level(double depth, List<Word> words, double probability) {
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
	public double getDepth() {
		return depth;
	}
	public void setDepth(double depth) {
		this.depth = depth;
	}	
}
