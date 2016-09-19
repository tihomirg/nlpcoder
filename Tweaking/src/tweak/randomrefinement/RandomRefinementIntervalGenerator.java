package tweak.randomrefinement;

import tweak.intervals.Interval;
import tweak.intervals.IntervalValue;

public class RandomRefinementIntervalGenerator<T extends IntervalValue> {

	private String name;
	private Interval<T> interval;
	private int refinmentStep;
	
	private Interval<T> seeds;
	private int intervalSize;
	private T step;
	
	public RandomRefinementIntervalGenerator(String name, Interval<T> seeds, T center, T step, int intervalSize, int refinmentStep) {
		this.name = name;
		this.seeds = seeds;
		this.step = step;
		this.intervalSize = intervalSize;
		this.refinmentStep = refinmentStep;
		this.interval = createInterval(center, step, intervalSize);
	}
	
	public String getName() {
		return this.name;
	}

	public Interval<T> getInterval() {
		return interval;
	}

	public void randomize() {
		this.interval = createInterval(seeds.chooseRandom(), step, intervalSize);
	}
	
	private Interval<T> createInterval(T center, T step, int intervalSize) {
		return new Interval<T>(center, step, intervalSize);
	}

	public void refine() {
		this.interval.refine(refinmentStep);
	}

}
