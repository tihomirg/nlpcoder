package commons.examples;

import java.util.List;

import commons.goals.Goal;
import commons.scores.Score;
import commons.values.Ordered;
import search.ISText;

public interface Example<T, V extends Ordered<V>> {
	public List<T> run(ISText iSText);
	public Score<V> getScore();
	public int size();
	public String getExprectedOuptup();
	public String getInput();
}
