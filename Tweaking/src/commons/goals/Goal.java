package commons.goals;

import java.util.List;

import commons.scores.Score;
import commons.values.Ordered;

public interface Goal<T, V extends Ordered<V>> {
	public Score<V> getScore(List<T> actualOutput);
	public int size();
	public String getExpectedOutput();
}
