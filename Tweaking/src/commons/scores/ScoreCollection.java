package commons.scores;

import java.util.LinkedList;
import java.util.List;

import commons.values.Ordered;
import commons.values.ValueFactory;

public class ScoreCollection<T extends Ordered<T>> implements Score<T> {
	private List<Score<T>> scores;
	private Class<?> clazz;
	
	public ScoreCollection(Class<?> clazz) {
		this.clazz = clazz;
		this.scores = new LinkedList<Score<T>>();
	}
	
	@Override
	public T getValue() {
		T sum = (T) ValueFactory.getInstance().create(clazz);
		for (Score<T> score: scores) {
			sum.add(score.getValue());
		}
		return sum;
	}
	
	@Override
	public boolean isBetterThan(Score<T> that) {
		return this.getValue().isBetterThan(that.getValue());
	}
	
	@Override
	public String toString() {
		return "ScoreCollection [value="+getValue()+", scores="+scores+"]";
	}

	@Override
	public void inc() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(Score<T> that) {
		this.scores.add(that);
	}
}
