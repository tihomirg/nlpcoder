package tweak.intervals;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Interval<T extends IntervalValue> {

	private T center;
	private List<T> values;
	private T step;
	private int intervalSize;
	
	public Interval(T center, T step, int intervalSize) {
		this.center = center;
		this.intervalSize = intervalSize;
		this.step = step;
		this.values = createValues(center, step, intervalSize);
	}

	private List<T> createValues(T center, T step, int intervalSize) {
		List<T> left = createLeft(center, step, intervalSize);
		List<T> right = createRight(center, step, intervalSize);
		Collections.reverse(left);
		return merge(left, this.center, right);
	}

	private List<T> merge(List<T> left, T centre, List<T> right) {
		List<T> list = new LinkedList<T>();
		list.addAll(left);
		list.add(centre);
		list.addAll(right);
		return list;
	}

	private List<T> createRight(T center, T step, int intervalSize) {
		List<T> list = new LinkedList<T>();
		T curr = center;
		for (int i = 0; i < intervalSize; i++) {
			T next = (T) curr.add(step);
			list.add(next);
			curr = next;
		}
		
		return list;
	}

	private List<T> createLeft(T center, T step, int intervalSize) {
		List<T> list = new LinkedList<T>();
		T curr = center;
		for (int i = 0; i < intervalSize; i++) {
			T next = (T) curr.sub(step);
			list.add(next);
			curr = next;
		}
		return list;
	}

	public void refine(int refinmentStep) {
		this.step = (T) this.step.dev(refinmentStep);
		this.intervalSize = this.intervalSize * refinmentStep;
		this.values = createValues(this.center, this.step, this.intervalSize);
	}

	public T chooseRandom() {
		return this.values.get(random(this.values.size()));
	}

	private int random(int size) {
		return (int) Math.floor(Math.random() * size);
	}
	
	public T getCenter() {
		return center;
	}
	
	public int getIntervalSize() {
		return intervalSize;
	}
	
	public List<T> getValues() {
		return values;
	}
}
