package commons.scores;

import commons.values.Ordered;


public class SingleScore<T extends Ordered<T>> implements Score<T> {
	protected T value;
	protected String name;
	
	public SingleScore(String name, T value) {
		this.value = value;
		this.name = name;
	}

	public SingleScore(T value) {
		this("", value);
	}

	public T getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return "Score [name=" + name + ", value=" + value + "]";
	}

	@Override
	public boolean isBetterThan(Score<T> that) {
		return this.value.isBetterThan(that.getValue());
	}

	@Override
	public void inc() {
		value.inc();
	}

	@Override
	public void add(Score<T> that) {
		this.value.add(that.getValue());
	}
}
