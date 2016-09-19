package commons.values;

public interface Ordered<T> {
	public void inc();
	public void add(T that);
	public boolean isBetterThan(T that);
}
