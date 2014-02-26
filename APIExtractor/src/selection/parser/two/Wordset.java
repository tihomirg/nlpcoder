package selection.parser.two;

import java.util.ArrayList;
import java.util.List;

import selection.parser.one.Word;

public class Wordset {
	private double probability;
	private Level[] levels;

	public double getProbability() {
		return probability;
	}
	public void setProbability(double probability) {
		this.probability = probability;
	}
	public Level[] getLevels() {
		return levels;
	}
	public void setLevels(Level[] levels) {
		this.levels = levels;
	}

	public List<Word> getWords(){
		List<Word> words = new ArrayList<Word>();

		for(Level level: levels){
			words.addAll(level.getWords());
		}
		return words;
	}
}
