package selection.parser.two;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import selection.parser.one.Word;

public class Wordset {
	private double probability;
	private Level[] levels;

	public Wordset(Level[] levels){
		this(levels, 0.0);
	}
	
	public Wordset(Level[] levels, double probability) {
		this.probability = probability;
		this.levels = levels;
	}
	
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
	
	@Override
	public String toString() {
		return "Wordset [probability=" + probability + ",\n levels="
				+ Arrays.toString(levels) + "]\n";
	}	
}
