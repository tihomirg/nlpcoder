package commons.scores;

import commons.values.Ordered;

public interface Score<T extends Ordered<T>> extends Ordered<Score<T>>{
	public T getValue();
}
