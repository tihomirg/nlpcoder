package commons;

import java.util.LinkedList;
import java.util.List;

import commons.examples.Example;
import commons.scores.Score;
import commons.scores.ScoreCollection;
import commons.values.Ordered;
import search.ISText;

public class Evaluator<T, V extends Ordered<V>> {

	private ISText iSText;
	private List<Example<T, V>> examples;
	private Class<?> clazz;
	
	public Evaluator(ISText iSText, Class<?> clazz, List<Example<T, V>> examples) {
		this.iSText = iSText;
		this.examples = examples;
		this.clazz = clazz;
	}
	
	public List<List<T>> run() {
		List<List<T>> results = new LinkedList<List<T>>();
		for (Example<T, V> example : this.examples) {
			results.add(example.run(iSText));
		}
		return results;
	}

	public Score<V> getTotalScore() {
		Score<V> score = new ScoreCollection<V>(clazz);
		for (Example<T, V> example : this.examples) {
			score.add(example.getScore());
		}
		return score;
	}
}
